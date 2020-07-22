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

let given_id_transitions = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    //list of found items on page load
    let foundImageLookups = [];
    let foundCommandLookups = [];

    //relationship from security state to list of images and commands
    let stateToImagesAndCommands = {};


    async function getImageLookups() {
        return $.get("/get-umbox-lookups-by-device", {id: given_id_transitions}, function (umboxLookups) {
            let arr = JSON.parse(umboxLookups);
            if (arr !== null && arr.length !== 0) {
                foundImageLookups = arr;
                foundImageLookups.forEach((imageLookup) => {
                   $.get("/umbox-image", {id: imageLookup.umboxImageId}, (umb) => {
                      let umbox = JSON.parse(umb);
                      imageLookup.name = umbox.name;
                   });
                });
            }
        });
    }

    async function getCommandLookups() {
        return $.get("/get-command-lookups-by-device", {id: given_id_transitions}, function (commandLookups) {
            let arr = JSON.parse(commandLookups);
            if (arr !== null && arr.length !== 0) {
                foundCommandLookups = arr;
                foundCommandLookups.forEach((commandLookup) => {
                    $.get("/command", {id:commandLookup.commandId}, (comm) =>{
                        let command = JSON.parse(comm);
                        commandLookup.name = command.name;
                    });
                })
            }
        });
    }

    //given a umboxImageLookup, add a relationship from its security state to its image
    function addToImageRelationship() {
        foundImageLookups.forEach((image) => {
            Object.keys(stateToImagesAndCommands[image.stateId]).forEach((previousState) => {
                stateToImagesAndCommands[image.stateId][previousState].images.push(image.name)
            });
        });
    }

    //given a commandLookup, add a relationship from its security state to its command
    function addToCommandRelationship() {
        foundCommandLookups.forEach((commandLookup) => {
            stateToImagesAndCommands[commandLookup.currentStateId][commandLookup.previousStateId].commands.push(commandLookup.name);
        });
    }

    async function configureStates() {
        //wait to get the security state before proceeding
        await $.get("/security-states", (states) => {
            let securityStates = JSON.parse(states);
            securityStates.forEach((currentState) => {
                if(stateToImagesAndCommands[currentState.id] == null) {
                    stateToImagesAndCommands[currentState.id] = {};
                    securityStates.forEach((previousState) => {
                        stateToImagesAndCommands[currentState.id][previousState.id] = {
                            currentState: currentState.name,
                            previousState: previousState.name,
                            images: [],
                            commands: []
                        };
                    });
                }
            });
        });
    }

    async function fillTable() {
        //wait to gather all imageLookups and commandLookups before proceeding
        await getImageLookups();
        await getCommandLookups();
        await configureStates();

        addToImageRelationship();
        addToCommandRelationship();

        Object.keys(stateToImagesAndCommands).forEach((currState) => {
            Object.keys(stateToImagesAndCommands[currState]).forEach((prevState) => {
                let state = stateToImagesAndCommands[currState][prevState];
                let newRow = "<tr>" +
                    "   <td>" + state.previousState + "</td>" +
                    "   <td>" + state.currentState + "</td>" +
                    "   <td>" + state.images.join("<br>") + "</td>" +
                    "   <td>" + state.commands.join("<br>") + "</td>" +
                    "</tr>";

                $("#stateTransitionsTableBody").append($(newRow));
            });

        });
    }

    fillTable();
});
