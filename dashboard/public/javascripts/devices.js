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
    const timeFormat = "MMM Do YY, h:mm:ss a";
    const infoPollInterval = 1 * 1000;

    $.fn.dataTable.moment(timeFormat);

    let homeTable = $('#deviceTable').DataTable(
        {
            order: [[3, 'desc']],
            columnDefs: [
                {orderable: false, targets: 4}
            ],
            language: {
                "emptyTable": "No devices to show"
            }
        }
    );

    let currentDevices = null;
    let currentState = null;
    let currentLatestAlert = null;
    let currentDeviceStatus = null;
    let typeNameToIDMap = {};   //map to go from deviceType name in the table to typeID in the form select
    let typeIdToNameMap = {};
    let groupNameToIDMap = {};   //map to go from group name in the table to groupID in the form select
    let tagIDtoNameMap = {};   //map to go from tag name in the table to tagID in the form select
    let deviceIdToTagIdsMap = {};    //map to retrieve list of tagIds based on deviceID
    let dataNodeNameToIdMap = {};    //map to go from dataNode name in the table to dataNodeID in the form select
    let dataNodeIdToNameMap = {};

    $("#add-device-btn").click(() => {
        let button = $("#add-device-btn");
        let form = $("#deviceForm");
        if(button.text() === "Add Device"){
            getFormData();
            button.text("Cancel");
            form.attr("hidden", false);
        } else {
            button.text("Add Device");
            form.attr("hidden", true);
            // clear form
        }
    });

    function getFormData() {
        getDeviceTypes();
        getDataNodes();
        getGroups();
        //getTags();
    }

    //fill device types in form
    async function getDeviceTypes() {
        $("#deviceContent #type").empty();

        return $.get("/device-types", (types) => {
            $.each(JSON.parse(types), (id, type) => {
                typeNameToIDMap[type.name] = type.id;
                typeIdToNameMap[type.id] = type.name;
                $("#deviceForm #type").append("<option id='typeOption" + type.id + "' value='" + type.id + "'>" + type.name + "</option>");
            });
        });
    }

    //fill groups in form
    async function getGroups() {
        $("#deviceForm #group").empty();

        return $.get("/groups", (groups) => {
            groupNameToIDMap[""] = -1;
            groupNameToIDMap["N/A"] = -1;
            $("#deviceForm #group").append("<option value='-1'>None</option>");   //assuming this is to allow an empty group

            $.each(JSON.parse(groups), (id, group) => {
                groupNameToIDMap[group.name] = group.id;
                $("#deviceForm #group").append("<option id='groupOption" + group.id + "' value='" + group.id + "'>" + group.name + "</option>");
            });
        });
    }

    //fill data nodes in form
    async function getDataNodes() {
        $("#dataNode").empty();

        return $.get("/data-nodes", (dataNodes) => {
            $.each(JSON.parse(dataNodes), (id, node) => {
                dataNodeNameToIdMap[node.name] = node.id;
                dataNodeIdToNameMap[node.id] = node.name;
                $("#dataNode").append("<option id='dataNodeOption" + node.id + "' value='" + node.id + "'>" + node.name + " - " + node.ipAddress + "</option>");
            });
        });
    }

    //fill tags in form
    async function getTags() {
        $("#deviceForm #formTags").empty();
        return $.get("/tags", (tags) => {
            $.each(JSON.parse(tags), (id, tag) => {
                tagIDtoNameMap[tag.id] = tag.name;
                $("#deviceForm #formTags").append("<div class='form-check col-2'>\n" +
                    "    <input class='form-check-input' type='checkbox' id='tagCheckbox" + tag.id + "' name='tagIds[]' value='" + tag.id + "'>\n" +
                    "    <label class='form-check-label' for='tagCheckBox" + tag.id + "'>" + tag.name + "</label>\n" +
                    "</div>");  
            });
            //$("#deviceForm #formTags").append("<input class='form-check-input' id='hiddenChk' type='checkbox' name='tagIds[]' value='-1' hidden checked>");
        });
    }

    function makeAttributesString(attributes) {
        let resultString = "";
        if (attributes != null) {
            Object.keys(attributes).forEach(key => {
                resultString = resultString + key + ": " + attributes[key] + "<br>";
            });

            resultString = resultString.substring(0, resultString.length - 4); //remove the last <br>
        }

        return resultString;
    }

    async function getSecurityState(id) {
        return $.get("/device-security-state", {id: id}, function (stateHistory) {
            let deviceState = JSON.parse(stateHistory);
            if (deviceState != null) {
                currentState = deviceState;
            } else {
                currentState = {name: "no current state"};
            }
        });
    }

    async function getLatestAlert(id) {
        return $.get("/alert-history", {id: id}, function (alertHistory) {
            let arr = JSON.parse(alertHistory);
            if (arr !== null && arr.length !== 0) {
                // This sorts the array in reverse order by timestamp
                arr.sort(function (a, b) {
                    return b.timestamp - a.timestamp
                });
                currentLatestAlert = arr[0];
            } else {
                currentLatestAlert = {name: "no alert history", deviceStatusId: -1};
            }
        });
    }

    async function getDeviceStatus(id) {
        return $.get("/device-status", {id: id}, function (deviceStatus) {
            if (deviceStatus != null) {
                currentDeviceStatus = JSON.parse(deviceStatus);
            } else {
                currentDeviceStatus = null;
            }
        });
    }

    async function getAllDevices() {
        return $.get("/devices", (devices) => {
            let deviceArray = [];
            $.each(JSON.parse(devices), (index, device) => {
                deviceArray.push(device);
            });
            currentDevices = deviceArray;
        });
    }

    async function fillTable() {
        await getAllDevices();

        for (const device of currentDevices) {
            await getSecurityState(device.id);
            await getLatestAlert(device.id);
            await getDeviceStatus(currentLatestAlert.deviceStatusId);

            let attributes = currentDeviceStatus ? makeAttributesString(currentDeviceStatus.attributes) : "";
            let formattedTime = currentLatestAlert.timestamp ? moment(currentLatestAlert.timestamp).format(timeFormat) : "";

            let newRow = "<tr id='tableRow" + device.id + "'>\n" +
                "    <td><a href='/info?id=" + device.id + "'>" + device.name + "</a></td>\n" +
                "    <td id='securityState" + device.id + "'>" + currentState.name + "</td>\n" +
                "    <td id='latestAlert" + device.id + "'>" + currentLatestAlert.name + "</td>\n" +
                "    <td id='time" + device.id + "'>" + formattedTime + "</td>\n" +
                "    <td id='deviceStatus" + device.id + "'>" + attributes + "</td>\n" +
                "</tr>";
            homeTable.row.add($(newRow)).draw();
        }

        getNewStates();
        getNewAlerts();
    }

    function getNewStates() {
        $.get("/get-new-states", (states) => {
            let newStates = JSON.parse(states);
            if (newStates != null) {
                newStates.forEach((state) => {
                    let deviceId = state.deviceId;
                    let stateName = state.name;

                    if(homeTable.row("#tableRow"+deviceId).cell("#securityState" +deviceId) != undefined) {
                        homeTable.row("#tableRow" +deviceId).cell("#securityState" +deviceId).data(stateName);
                        $("#homeTable #tableRow" + deviceId).addClass("updated");
                        setTimeout(function() {
                            $("#homeTable #tableRow" + deviceId).removeClass("updated");
                        }, 3000);
                    }

                });
            }
        });
    }

    function getNewAlerts() {
        $.get("/get-new-alerts", (alerts) => {
            let newAlerts = JSON.parse(alerts);
            if (newAlerts != null) {
                newAlerts.forEach((alert) => {
                    let deviceId = alert.deviceId;
                    let alertName = alert.name;
                    let deviceStatusId = alert.deviceStatusId;
                    let formattedTime = alert.timestamp ? moment(alert.timestamp).format(timeFormat) : "";

                    homeTable.row("#tableRow" + deviceId).cell("#latestAlert" + deviceId).data(alertName).draw();
                    homeTable.row("#tableRow" + deviceId).cell("#time" + deviceId).data(formattedTime).draw();

                    $("#homeTable #tableRow" + deviceId).addClass("updated");

                    setTimeout(function() {
                        $("#homeTable #tableRow" + deviceId).removeClass("updated");
                    }, 3000);

                    if (deviceStatusId != 0) {
                        $.get("/device-status", {id: id}, function (deviceStatus) {
                            if (deviceStatus != null) {
                                deviceStatus = JSON.parse(deviceStatus);
                            } else {
                                deviceStatus = null;
                            }

                            let attributes = deviceStatus ? makeAttributesString(deviceStatus.attributes) : "";

                            homeTable.row("#tableRow" + deviceId).cell("#deviceStatus" + deviceId).data(attributes);
                        });
                    }
                });
            }
        });
    }

    function startListener() {
        $.post("/start-listener", () => {});
    }

    fillTable();

    startListener();

    setInterval(function () {
        getNewStates();
        getNewAlerts();
    }, infoPollInterval);

    $(window).on("beforeunload", function() {
       $.post("/stop-listener", () => {});
    });
});
