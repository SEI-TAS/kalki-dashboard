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
        getTags();
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
            $("#deviceForm #group").append("<option value='-1'></option>");   //assuming this is to allow an empty group

            $.each(JSON.parse(groups), (id, group) => {
                groupNameToIDMap[group.name] = group.id;
                $("#deviceForm #group").append("<option id='groupOption" + group.id + "' value='" + group.id + "'>" + group.name + "</option>");
            });
        });
    }

    //fill data nodes in form
    async function getDataNodes() {
        $("#dataNode").empty();
        console.log("Getting data node.")

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
            $("#deviceForm #formTags").append("<input class='form-check-input' id='hiddenChk' type='checkbox' name='tagIds[]' value='-1' hidden checked>");
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
