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

var id = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const infoPollInterval = 1 * 1000;
    let device = null;

    function createTagString(tags) {
        let tagNames = [];

        if(tags != null) {
            tags.forEach((tag) => {
                tagNames = tagNames.concat(tag.name);
            });
        }

        return tagNames.join(", ");
    }

    async function getDevice() {
        $.get('/device', {"id": id}, (deviceString) => {
            device = JSON.parse(deviceString);
            console.log(device);
            if(device === null) {
                console.log("Invalid ID");
            }
            else {
                $("#deviceInfoPage #name").text(device.name);
                $("#deviceInfoPage #description").text(device.description);
                $("#deviceInfoPage #type").text(device.type.name);
                if(device.group != null) {
                    $("#deviceInfoPage #group").text(device.group.name);
                } else {
                    $("#deviceInfoPage #group").text("None");
                }
                $("#deviceInfoPage #ipAddress").text(device.ip);
                if(device.credentials === "") {
                    $("#deviceInfoPage #credentials").text("None");
                }
                else {
                    $("#deviceInfoPage #credentials").text('****');
                }
            }
        });
    }

    async function getSecurityState() {
        $.get("/device-security-state", { id: id }, function(stateHistory) {
            let deviceState = JSON.parse(stateHistory);
            if(deviceState !== null) {
                $("#deviceInfoPage #securityState").text(deviceState.name);
            }
        });
    }

    async function getTags() {
        $.get('/get-tags-by-device', {"id": id}, (tags) => {
            let tagString = createTagString(JSON.parse(tags));
            $("#deviceInfoPage #tags").text(tagString);
        });
    }

    function getNewStates() {
        $.get("/get-new-states", (states) => {
            let newStates = JSON.parse(states);
            if (newStates != null) {
                newStates.forEach((state) => {
                    if(state.deviceId == id) {
                        $("#deviceInfoPage #securityState").text(state.name);

                        $("#deviceInfoPage #securityState").addClass("updated");
                        setTimeout(function() {
                            $("#deviceInfoPage #securityState").removeClass("updated");
                        }, 3000);
                    }
                });
            }
        });
    }

    async function main() {
        //must wait to fill table before polling for new security states
        await Promise.all([getDevice(), getSecurityState(), getTags()]);

        getNewStates();

        setInterval(function () {
            getNewStates();
        }, infoPollInterval);
    }

    $("#stateResetButton").click(() => {
        $.get('/state-reset', {"id": id}, function() {
            window.location.reload();
        });
    });

    $("#editDeviceBtn").click(() => {
        $("#deviceInfoTextContainer").attr("hidden", true);
        $("#deviceEditForm").attr("hidden", false);
    });

    $("#cancelFormButton").click(() => {
        $("#deviceInfoTextContainer").attr("hidden", false);
        $("#deviceEditForm").attr("hidden", true);
    });

    $("#deleteDeviceBtn").click(() => {
        $.post("/delete-device", {id: id}, function () {
            window.location.replace("/");
        })
    });

    main();
});
