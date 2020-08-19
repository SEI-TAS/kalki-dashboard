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

    /**
     * Create a directional graph based on input data. This graph is color coded for up to 10 unique device types, and
     * allows the user to move the graph elements around.
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
    function directionalGraph(links, deviceColors, deviceTypes, nodeColors) {
        // Get all unique nodes
        let data = ({nodes: Array.from(new Set(links.flatMap(l => [l.source, l.target])), id => ({id})), links})

        // Set the internal scale of the display area. Change these to mess with the scale of the svg
        let height = 400
        let width = 700

        // Arc function for the link between two nodes
        function linkArc(d) {
            const r = Math.hypot(d.target.x - d.source.x, d.target.y - d.source.y);
            return `
            M${d.source.x},${d.source.y}
            A${r},${r} 0 0,1 ${d.target.x},${d.target.y}
          `;
        }

        // Simulation for moving the nodes around
        drag = simulation => {
            function dragstarted(d) {
                if (!d3.event.active) simulation.alphaTarget(0.3).restart();
                d.fx = d.x;
                d.fy = d.y;
            }

            function dragged(d) {
                d.fx = d3.event.x;
                d.fy = d3.event.y;
            }

            function dragended(d) {
                if (!d3.event.active) simulation.alphaTarget(0);
                d.fx = null;
                d.fy = null;
            }

            return d3.drag()
                .on("start", dragstarted)
                .on("drag", dragged)
                .on("end", dragended);
        }

        // A function to generate the actual chart (stored in a chart variable)
        chart = function () {
            const links = data.links.map(d => Object.create(d));
            const nodes = data.nodes.map(d => Object.create(d));

            const simulation = d3.forceSimulation(nodes)
                .force("link", d3.forceLink(links).id(d => d.id))
                .force("charge", d3.forceManyBody().strength(-400))
                .force("x", d3.forceX())
                .force("y", d3.forceY());

            // Attach it to the html item with the id="svg"
            const svg = d3.selectAll("#svg").append("svg")
                .attr("viewBox", [-width / 2, -height / 2, width, height])
                .style("font", "12px sans-serif");

            var div = d3.select("#svg").append("div")	
			    .attr("class", "tooltip")				
			    

            // Per-type markers, as they don't inherit styles.
            svg.append("defs").selectAll("marker")
                .data(deviceTypes)
                .join("marker")
                .attr("id", d => `arrow-${d}`)
                .attr("viewBox", "0 -5 10 10")
                .attr("refX", 15)
                .attr("refY", -0.5)
                .attr("markerWidth", 6)
                .attr("markerHeight", 6)
                .attr("orient", "auto")
                .append("path")
                .attr("fill", deviceColors)
                .attr("d", "M0,-5L10,0L0,5");

            // Create the links between all of the nodes, and color it based on the device type
            const link = svg.append("g")
                .attr("fill", "none")
                .attr("stroke-width", 1.5)
                .selectAll("path")
                .data(links)
                .join("path")
                .attr("stroke", d => deviceColors(d.deviceType))
                .attr("marker-end", d => `url(${new URL(`#arrow-${d.deviceType}`, location)})`)

            // For getting text on links, see the second part of this answer:
            //  https://stackoverflow.com/questions/33165265/show-tool-tip-on-links-of-force-directed-graph-in-d3js

            // Create the nodes
            const node = svg.append("g")
                .attr("stroke-linecap", "round")
                .attr("stroke-linejoin", "round")
                .selectAll("g")
                .data(nodes)
                .join("g")
                // Change this to change the color of the nodes, based on the name associated with the node
                .attr("fill", d => {
                    // Split on the node name, to exclude device type header
                    var nodeSplit = d.id.split('|')
                    // Safely get the second element (avoid index out of bounds errors)
                    nodeSplit.length === 2 ? nodeSplit = nodeSplit[1] : nodeSplit = nodeSplit;
                    return nodeColors(nodeSplit)
                })
                .call(drag(simulation));

            node.append("circle")
                .attr("stroke", "white")
                .attr("stroke-width", 1.5)
                .attr("r", 4)

            node.append("text")
                .style("fill", "black")  // Set the text to be black
                .attr("x", 8)
                .attr("y", "0.31em")
                // .text(d => {
                //     var nodeSplit = d.id.split('|')
                //     nodeSplit.length === 2 ? nodeSplit = nodeSplit[1] : nodeSplit = nodeSplit;
                //     return nodeSplit
                // })
                .clone(true).lower()
                .attr("fill", "none")
                .attr("stroke", "white")
                .attr("stroke-width", 3);

            simulation.on("tick", () => {
                link.attr("d", linkArc);
                node.attr("transform", d => `translate(${d.x},${d.y})`);
            });

            return svg.node();
        }

        $("#svg").empty()
        // Make the call to actually draw the chart
        chart()
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
     * Get the policy rules, then populate the links object with the data necessary to create a chart of device
     * connections over state transitions.
     *
     * @returns {Promise<*>} Return nothing when done
     */
    async function getPolicyRuleData() {
        links = []
        return $.get("/policy-rules-by-id?id="+$("#type").val(), (policyRules) => {
            $.each(JSON.parse(policyRules), (index, policyRule) => {
                // Get the string names using the provided maps
                let deviceTypeName;
                if(policyRule.deviceTypeId) {
                    deviceTypeName = deviceTypeIdToNameMap[policyRule.deviceTypeId];
                }
                else {
                    deviceTypeName = "All";
                }
                let stateSource = stateIdToNameMap[stateTransitionIdToStartMap[policyRule.stateTransitionId]];
                let stateTarget = stateIdToNameMap[stateTransitionIdToFinishMap[policyRule.stateTransitionId]];

                // Add the links to the object using the special format
                // Have to be a little tricky here, and put the device name in front of the state. This lets us
                //  separate the graphs from one another (x|Normal is different from y|Normal). To get the color to
                //  work properly, we just remove this leading device name and separator when applying colors in chart
                links.push({"source": deviceTypeName + "|" + stateSource,
                    "target": deviceTypeName + "|" + stateTarget,
                    "deviceType": deviceTypeName
                });
            });
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
        await getPolicyRuleData();

        // Get all individual deviceTypes, so we can group them together
        let deviceTypes = Array.from(new Set(links.map(d => d.deviceType)));

        // Get all the colors to be used with the various device types, and nodes
        let deviceColors = d3.scaleOrdinal(deviceTypes, d3.schemeCategory10);
        let nodeColors = d3.scaleOrdinal(stateNameToIdMap,
            ["#4daf4a","#dec100","#e41a1c","#999999","#377eb8","#984ea3","#ff7f00","#a65628","#f781bf"]);

        // Make the graph and the legend
        directionalGraph(links, deviceColors, deviceTypes, nodeColors);
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
    });
});