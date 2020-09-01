/*
 * Kalki - A Software-Defined IoT Security Platform
 * Copyright 2020 Carnegie Mellon University.
 * NO WARRANTY. THIS CARNEGIE MELLON UNIVERSITY AND SOFTWARE ENGINEERING INSTITUTE MATERIAL IS FURNISHED ON AN "AS-IS" BASIS. CARNEGIE MELLON UNIVERSITY MAKES NO WARRANTIES OF ANY KIND, EITHER EXPRESSED OR IMPLIED, AS TO ANY MATTER INCLUDING, BUT NOT LIMITED TO, WARRANTY OF FITNESS FOR PURPOSE OR MERCHANTABILITY, EXCLUSIVITY, OR RESULTS OBTAINED FROM USE OF THE MATERIAL. CARNEGIE MELLON UNIVERSITY DOES NOT MAKE ANY WARRANTY OF ANY KIND WITH RESPECT TO FREEDOM FROM PATENT, TRADEMARK, OR COPYRIGHT INFRINGEMENT.
 * Released under a MIT (SEI)-style license, please see license.txt or contact permission@sei.cmu.edu for full terms.
 * [DISTRIBUTION STATEMENT A] This material has been approved for public release and unlimited distribution.  Please see Copyright notice for non-US Government use and distribution.
 * This Software includes and/or makes use of the following Third-Party Software subject to its own license:
 * 1. Google Guava (https://github.com/google/guava) Copyright 2007 The Guava Authors.
 * 2. JSON.simple (https://code.google.com/archive/p/json-simple/) Copyright 2006-2009 Yidong Fang, Chris Nokleberg.
 * 3. JUnit (https://junit.org/junit5/docs/5.0.1/api/overview-summary.html) Copyright 2020 The JUnit Team.
 * 4. Play Framework (https://www.playframework.com/) Copyright 2020 Lightbend Inc..
 * 5. PostgreSQL (https://opensource.org/licenses/postgresql) Copyright 1996-2020 The PostgreSQL Global Development Group.
 * 6. Jackson (https://github.com/FasterXML/jackson-core) Copyright 2013 FasterXML.
 * 7. JSON (https://www.json.org/license.html) Copyright 2002 JSON.org.
 * 8. Apache Commons (https://commons.apache.org/) Copyright 2004 The Apache Software Foundation.
 * 9. RuleBook (https://github.com/deliveredtechnologies/rulebook/blob/develop/LICENSE.txt) Copyright 2020 Delivered Technologies.
 * 10. SLF4J (http://www.slf4j.org/license.html) Copyright 2004-2017 QOS.ch.
 * 11. Eclipse Jetty (https://www.eclipse.org/jetty/licenses.html) Copyright 1995-2020 Mort Bay Consulting Pty Ltd and others..
 * 12. Mockito (https://github.com/mockito/mockito/wiki/License) Copyright 2007 Mockito contributors.
 * 13. SubEtha SMTP (https://github.com/voodoodyne/subethasmtp) Copyright 2006-2007 SubEthaMail.org.
 * 14. JSch - Java Secure Channel (http://www.jcraft.com/jsch/) Copyright 2002-2015 Atsuhiko Yamanaka, JCraft,Inc. .
 * 15. ouimeaux (https://github.com/iancmcc/ouimeaux) Copyright 2014 Ian McCracken.
 * 16. Flask (https://github.com/pallets/flask) Copyright 2010 Pallets.
 * 17. Flask-RESTful (https://github.com/flask-restful/flask-restful) Copyright 2013 Twilio, Inc..
 * 18. libvirt-python (https://github.com/libvirt/libvirt-python) Copyright 2016 RedHat, Fedora project.
 * 19. Requests: HTTP for Humans (https://github.com/psf/requests) Copyright 2019 Kenneth Reitz.
 * 20. netifaces (https://github.com/al45tair/netifaces) Copyright 2007-2018 Alastair Houghton.
 * 21. ipaddress (https://github.com/phihag/ipaddress) Copyright 2001-2014 Python Software Foundation.
 * DM20-0543
 *
 */
jQuery(document).ready(($) => {
    let links = [];

    let deviceTypeIdToNameMap = {};
    let deviceTypeNameToIdMap = {};
    let stateIdToNameMap = {};
    let stateNameToIdMap = {};
    let stateTransitionIdToStartMap = {};
    let stateTransitionIdToFinishMap = {};
    let stateTransitionStartToIdMap = {};
    let stateTransitionFinishToIdMap = {};

    let policyConditionIdToAlertTypeIdsMap = {};
    let policyConditionIdToThresholdMap = {};

    let alertTypeIdToNameMap = {};
    let alertTypeNameToIdMap = {};
    let alertTypeIdToDescription = {};

    let stateNameToUmboxes = {};

    let umboxImageIDtoNameMap = {};
    let umboxImageNameToIdMap = {};

    let commandLookupIdToPolicyRuleId = {};
    let commandLookupIdToCommandId = {};

    let commandIdToNameMap = {};
    let commandIdToDeviceTypeIdMap = {};
    let commandNameToIdMap = {};

    /**
     * Generates a legend to help with determining what colors belong to what device types when using the directionalGraph
     * tool.
     *
     * @param links input data used to generate the graph. It needs to be in this format:
     * {
     *  "source":"<device type>|<state transition 1>",
     *  "target":"<device type>|<state transition 2>",
     *  "deviceType":"<device type>"
     * }
     * @param deviceColors A mapping between unique device types and their colors
     * @param deviceTypes A map of the unique device types being used
     * @param nodeColors A mapping between unique nodes and their colors
     */
    function legend(links, deviceColors, deviceTypes, nodeColors) {
        let arrowColorsDiv = $("#arrowColors");
        let html = '';

        // Add arrow colors
        deviceTypes.forEach(function (deviceType) {
            html += '\n<div class="col-12 m-2" style="color: ' + deviceColors(deviceType) + ';">' + deviceType + '</div>';
        })
        arrowColorsDiv.html(html)

        // Reset and get ready to do the nodes
        let nodeColorsDiv = $("#nodeColors");
        html = '<div class="col-12 m-2" style="color: ' + nodeColors("undefined") + ';">Default</div>'

        for (var key in stateNameToIdMap) {
            html += '\n<div class="col-12 m-2" style="color: ' + nodeColors(key) + ';">' + key + '</div>'
        }
        nodeColorsDiv.html(html)
    }

    function drawGraph(links, deviceColors, deviceTypes, nodeColors) {
      var stateNamesList = Object.keys(stateNameToIdMap).map(function (state) {
                                  return {"id": state}
                        });

    	let data = ({nodes: stateNamesList, links})

    	var cy = cytoscape({
  		  container: document.getElementById('cy')
  		});

  		for(var i = 0; i < data.nodes.length; i++) {
  			nodeId = data.nodes[i].id;
  			cy.add({
  		        data: { id: nodeId, 
                      name: nodeId},
  		        }
  		    );

  		    cy.nodes('[id = \"' + nodeId +'\"]').style('background-color', nodeColors(nodeId));
  		}

  		for(var i = 0; i < links.length; i++) {

  			cy.add({data: { id: 'trans'+i, 
  							source: links[i].source, 
  							target: links[i].target,
  							policyConditionsAndRatesAndCommands: links[i].policyConditionsAndRatesAndCommands}});
  		}

  		cy.layout({ name: 'circle'}).run();
  		cy.zoomingEnabled( false );
  		cy.nodes().ungrabify();

  		cy.edges('edge').style({
		    "curve-style": "bezier",
		    "target-arrow-shape": "triangle"
		  })

  		cy.on('tap', 'node', function(evt){
  		  var node = evt.target;
        var htmlString = "";
        var umboxes = stateNameToUmboxes[node.data("name")];

        htmlString += "Name: " + node.data("name")  + "<br>";
        htmlString += "Umboxes: ";
        for(var i = 0; i < umboxes.length-1; i++) {
          htmlString += umboxes[i] + ", ";
        }
        htmlString += umboxes[umboxes.length-1];
  		  $("#selectedElement").html(htmlString);
  		});

  		cy.on('tap', 'edge', function(evt){
  		  var edge = evt.target;
  		  var htmlString = "";
        var listOfPolicyConditions = edge.data("policyConditionsAndRatesAndCommands");
        for(var i = 0; i < listOfPolicyConditions.length; i++) {
          var content = "<strong>Sampling Rate Factor</strong>: " + listOfPolicyConditions[i][1] + "<br>";
          content += "<strong>Commands</strong>: " + listOfPolicyConditions[i][2] + "<br>";
          var row = '<button class="accordion">Policy Rule: '+listOfPolicyConditions[i][0]+
                '</button><div class="panel"><p>'+ content +'</p></div>'
          htmlString += row;
        }
        $("#selectedElement").html(htmlString);
        var acc = document.getElementsByClassName("accordion");
        for (var i = 0; i < acc.length; i++) {
          acc[i].addEventListener("click", function() {
            this.classList.toggle("activeRow");
            var panel = this.nextElementSibling;
            if (panel.style.display === "block") {
              panel.style.display = "none";
            } else {
              panel.style.display = "block";
            }
          });
        }
  		});



      cy.on('tap', function(event){
        var evtTarget = event.target;
        if( evtTarget === cy ){
          $("#selectedElement").html("");
        }
      });
    }

    /**
     * Get the device types and save them with their ids in two maps
     *
     * @returns {Promise<*>} Return nothing when done
     */
    async function getDeviceTypes() {
        return $.get("/device-types", (types) => {
            $.each(JSON.parse(types), (id, type) => {
                deviceTypeIdToNameMap[type.id] = type.name;
                deviceTypeNameToIdMap[type.name] = type.id;
            });
        });
    }

    /**
     * Get the security states and save them with their ids in two maps
     *
     * @returns {Promise<*>} Return nothing when done
     */
    async function getSecurityStates() {
        return $.get("/security-states", (securityStates) => {
            $.each(JSON.parse(securityStates), (id, securityState) => {
                stateIdToNameMap[securityState.id] = securityState.name;
                stateNameToIdMap[securityState.name] = securityState.id;
            });
        });
    }

    /**
     * Get the security state transitions and save them with their ids in reference to the security states
     *
     * @returns {Promise<*>} Return nothing when done
     */
    async function getStateTransitions() {
        // Need security state information for the transitions
        await getSecurityStates();

        return $.get("/state-transitions", (transitions) => {
            $.each(JSON.parse(transitions), (id, transition) => {
                stateTransitionIdToStartMap[transition.id] = transition.startStateId;
                stateTransitionIdToFinishMap[transition.id] = transition.finishStateId;
                stateTransitionStartToIdMap[transition.startStateId] = transition.id;
                stateTransitionFinishToIdMap[transition.finishStateId] = transition.id;
            });
        });
    }

    /**
     * Get policy conditions and saves them into two dictionaries to use
     *
     * @returns {Promise<*>} Return nothing when done
     */
    async function getPolicyConditions() {
        return $.get("/policy-conditions", (policyConditions) => {
            $.each(JSON.parse(policyConditions), (id, policyCondition) => {
                policyConditionIdToAlertTypeIdsMap[policyCondition.id] = policyCondition.alertTypeIds;
                policyConditionIdToThresholdMap[policyCondition.id] = policyCondition.threshold;
            });
        });
    }

    /**
     * Get alert types and saves them into three dictionaries to use
     *
     * @returns {Promise<*>} Return nothing when done
     */
    async function getAlertTypes() {
        $("#policyRuleContent #policyRuleAlertTypeSelect").empty();

        return $.get("/alert-types", (alertTypes) => {
            $.each(JSON.parse(alertTypes), (id, alertType) => {
                alertTypeIdToNameMap[alertType.id] = alertType.name;
                alertTypeNameToIdMap[alertType.name] = alertType.id;
                alertTypeIdToDescription[alertType.id] = alertType.description;
            });
        });
    }

     /**
     * Get umbox images and saves them into two dictionaries to use
     *
     * @returns {Promise<*>} Return nothing when done
     */
    async function getUmboxImages() {
        return $.get("/umbox-images", (umboxImages) => {
            $.each(JSON.parse(umboxImages), (id, umboxImage) => {
                umboxImageIDtoNameMap[umboxImage.id] = umboxImage.name;
                umboxImageNameToIdMap[umboxImage.name] = umboxImage.id;
            });
        });
    }

    /**
     * Get umbox lookups and creates a dictionary that has security states as keys and a list of umboxes associated as values
     */
    async function getUmboxLookups() {
        await getSecurityStates();
        await getUmboxImages();

        $.get("/get-umbox-lookups-by-device-type?id="+$("#type").val(), (umboxLookups) => {
            $.each(JSON.parse(umboxLookups), (index, umboxLookup) => {
                if(stateIdToNameMap[umboxLookup.securityStateId] in stateNameToUmboxes) {
                  stateNameToUmboxes[stateIdToNameMap[umboxLookup.securityStateId]].push(umboxImageIDtoNameMap[umboxLookup.umboxImageId]) 
                }
                else {
                  stateNameToUmboxes[stateIdToNameMap[umboxLookup.securityStateId]] = [umboxImageIDtoNameMap[umboxLookup.umboxImageId]]
                }
            });
        });
    }

    /**
     * Get commands and saves them into three dictionaries to use
     *
     * @returns {Promise<*>} Return nothing when done
     */
    async function getCommands() {
        return $.get("/commands", (commands) => {
            $.each(JSON.parse(commands), (id, command) => {
                commandIdToNameMap[command.id] = command.name;
                commandIdToDeviceTypeIdMap[command.id] = command.deviceTypeId;
                commandNameToIdMap[command.name] = command.id;
            });
        });
    }

    /**
     * Get command lookups and saves them into three dictionaries to use
     *
     * @returns {Promise<*>} Return nothing when done
     */
    async function getCommandLookups() {
        return $.get("/command-lookups", (commands) => {
            $.each(JSON.parse(commands), (id, commandLookup) => {
                commandLookupIdToPolicyRuleId[commandLookup.id] = commandLookup.policyRuleId
                commandLookupIdToCommandId[commandLookup.id] = commandLookup.commandId
            });
        });
    }

    /**
     * Get the policy rules, then populate the links object with the data necessary to create a chart of device
     * connections over state transitions.
     *
     * @returns {Promise<*>} Return nothing when done
     */
    async function getPolicyRuleData() {
        
        await getCommandLookups();
        await getCommands();
        links = []

        var stateToLinksDict = {}
        return $.get("/policy-rules-by-id?id="+$("#type").val(), (policyRules) => {
            $.each(JSON.parse(policyRules), (index, policyRule) => {
                // Get the string names using the provided maps
                let deviceTypeName;
                let alertTypeArray = [];
                policyConditionIdToAlertTypeIdsMap[policyRule.policyConditionId].forEach(element => alertTypeArray.push(alertTypeIdToNameMap[element]));

                let deviceCommandArray = [];
                $.each(commandLookupIdToPolicyRuleId, function (index, policyRuleId) {
                    if (policyRuleId == policyRule.id) {
                        deviceCommandArray.push(
                            deviceTypeIdToNameMap[commandIdToDeviceTypeIdMap[commandLookupIdToCommandId[index]]]
                            + " - " +
                            commandIdToNameMap[commandLookupIdToCommandId[index]]
                        )
                    }
                });


                let stateSource = stateIdToNameMap[stateTransitionIdToStartMap[policyRule.stateTransitionId]];
                let stateTarget = stateIdToNameMap[stateTransitionIdToFinishMap[policyRule.stateTransitionId]];
                let policyConditionName = alertTypeArray.join(" && ");

                // Add the links to the object using the special format
                // Have to be a little tricky here, and put the device name in front of the state. This lets us
                //  separate the graphs from one another (x|Normal is different from y|Normal). To get the color to
                //  work properly, we just remove this leading device name and separator when applying colors in chart
                var key = stateSource+"|"+stateTarget;
                if(key in stateToLinksDict) {
                  stateToLinksDict[key].push([policyConditionName, policyRule.samplingRateFactor, deviceCommandArray.join(", ")])
                }
                else {
                  stateToLinksDict[key] = [[policyConditionName, policyRule.samplingRateFactor, deviceCommandArray.join(", ")]]
                }
            });
            // After collecting all the necessary data, format it for the viewer to present
            for(var key in stateToLinksDict) {
              var item = stateToLinksDict[key];
              var stateSource = key.split("|")[0];
              var stateTarget = key.split("|")[1];
              var deviceTypeName = deviceTypeIdToNameMap[$("#type").val()];

              links.push({"source": stateSource,
                    "target": stateTarget,
                    "deviceType": deviceTypeName,
                    "policyConditionsAndRatesAndCommands" : item,
                });
            }
        });
    }

    /**
     * The main runner for the policyRuleView js, coordinates getting the ncessesary data then creating the chart.
     *
     * @returns {Promise<*>} Return nothing when done
     */
    async function getPolicyRuleView() {
        // Get the data needed to generate this graph
        await getDeviceTypes();
        await getStateTransitions();
        await getAlertTypes();
        await getPolicyConditions();
        await getUmboxLookups();
        await getPolicyRuleData();

        // Get all individual deviceTypes, so we can group them together
        let deviceTypes = Array.from(new Set(links.map(d => d.deviceType)));

        // Get all the colors to be used with the various device types, and nodes
        let deviceColors = d3.scaleOrdinal(deviceTypes, d3.schemeCategory10);

        let nodeColors = d3.scaleOrdinal(Object.keys(stateNameToIdMap),
            ["#4daf4a","#dec100","#e41a1c","#999999","#377eb8","#984ea3","#ff7f00","#a65628","#f781bf"]);

        // Make the graph and the legend
        drawGraph(links, deviceColors, deviceTypes, nodeColors);
        legend(links, deviceColors, deviceTypes, nodeColors);
    }

    /**
     * Only load data when tab is active
     */
    $('a[href="#PolicyRuleViewContent"]').on('shown.bs.tab', function (e) {
        getPolicyRuleView();
    });
    $("#type").change(function() {
        getPolicyRuleView();
        $("#selectedElement").html("");
        stateNameToUmboxes = {};
    });

});