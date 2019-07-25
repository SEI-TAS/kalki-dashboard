jQuery(document).ready(($) => {
    let deviceTable = $('#deviceTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    let typeNameToIDMap = {};   //map to go from deviceType name in the table to typeID in the form select
    let typeIdToNameMap = {};
    let groupNameToIDMap = {};   //map to go from group name in the table to groupID in the form select
    let tagIDtoNameMap = {};   //map to go from tag name in the table to tagID in the form select
    let deviceIdToTagIdsMap = {}    //map to retrieve list of tagIds based on deviceID

    function checkTags(tagIds) {
        //uncheck all tags
        $('input:checkbox').removeAttr('checked');

        tagIds.forEach(tagId => {
            $('#deviceContent #formTags #tagCheckbox' + tagId).prop("checked", true);
        })
        $('input:checkbox#hiddenChk').prop("checked", true);
    }

    //helper function to populate the device form
    function populateForm(device) {
        let deviceGroup = device["group"];
        let deviceGroupName = deviceGroup ? deviceGroup["name"] : "";
        $("#deviceContent .form-control#type").val(typeNameToIDMap[device["type"]["name"]]);
        $("#deviceContent .form-group #name").val(device["name"]).change();
        $("#deviceContent .form-group #description").val(device["description"]);
        $("#deviceContent .form-control#group").val(groupNameToIDMap[deviceGroupName]);
        $("#deviceContent .form-group #ipAddress").val(device["ip"]);
        $("#deviceContent .form-group #statusHistorySize").val(device["statusHistorySize"]).change();
        $("#deviceContent .form-group #samplingRate").val(device["samplingRate"]).change();
        checkTags(device["tagIds"]);
    }

    //convert list of tagIds to comma separated names as string
    function tagIdsToNames(tagIds) {
        let tagNames = [];
        tagIds.forEach((id) => {
            tagNames = tagNames.concat(tagIDtoNameMap[id]);
        });

        return tagNames.join(", ");
    }

    function findTagIdsByDevice(id) {
        return deviceIdToTagIdsMap[id]
    }

    //fill device types in form
    async function getDeviceTypes() {
        $("#deviceContent #type").empty();

        return $.get("/device-types", (types) => {
            $.each(JSON.parse(types), (id, type) => {
                typeNameToIDMap[type.name] = type.id;
                typeIdToNameMap[type.id] = type.name;
                $("#deviceContent #type").append("<option id='typeOption" + type.id + "' value='" + type.id + "'>" + type.name + "</option>");
            });
        });
    }

    //fill groups in form
    async function getGroups() {
        $("#deviceContent #group").empty();

        return $.get("/groups", (groups) => {
            groupNameToIDMap[""] = -1;
            groupNameToIDMap["N/A"] = -1;
            $("#deviceContent #group").append("<option value='-1'></option>");   //assuming this is to allow an empty group

            $.each(JSON.parse(groups), (id, group) => {
                groupNameToIDMap[group.name] = group.id;
                $("#deviceContent #group").append("<option id='groupOption" + group.id + "' value='" + group.id + "'>" + group.name + "</option>");
            });
        });
    }

    //fill tags in form
    async function getTags() {
        $("#deviceContent #formTags").empty();

       return $.get("/tags", (tags) => {
            $.each(JSON.parse(tags), (id, tag) => {
                tagIDtoNameMap[tag.id] = tag.name;
                $("#deviceContent #formTags").append("<div class='form-check col-2'>\n" +
                    "    <input class='form-check-input' type='checkbox' id='tagCheckbox" + tag.id + "' name='tagIds[]' value='" + tag.id + "'>\n" +
                    "    <label class='form-check-label' for='tagCheckBox" + tag.id + "'>" + tag.name + "</label>\n" +
                    "</div>");
            });
            $("#deviceContent #formTags").append("<input class='form-check-input' id='hiddenChk' type='checkbox' name='tagIds[]' value='-1' hidden checked>");
        });
    }


    async function getDevices() {
        await getDeviceTypes();
        await getGroups();
        await getTags();

        deviceTable.clear();

        $.get("/devices", (devices) => {
            $.each(JSON.parse(devices), (index, device) => {
                let deviceGroupName = device.group ? device.group.name : "N/A";

                let newRow = "<tr id='tableRow" + device.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + device.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + device.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td class='fit' id='deviceID" + device.id + "'>" + device.id + "</td>\n" +
                    "    <td id='name" + device.id + "'>" + device.name + "</td>\n" +
                    "    <td id='description" + device.id + "'>" + device.description + "</td>\n" +
                    "    <td id='deviceType" + device.id + "'>" + device.type.name + "</td>\n" +
                    "    <td id='group" + device.id + "'>" + deviceGroupName + "</td>\n" +
                    "    <td id='ipAddress" + device.id + "'>" + device.ip + "</td>\n" +
                    "    <td id='statusHistorySize" + device.id + "'>" + device.statusHistorySize + "</td>\n" +
                    "    <td id='samplingRate" + device.id + "'>" + device.samplingRate + "</td>\n" +
                    "    <td id='tags" + device.id + "'>" + tagIdsToNames(device.tagIds) + "</td>\n" +
                    "</tr>"

                deviceTable.row.add($(newRow)).draw();

                //add tagIds for this device to the map
                deviceIdToTagIdsMap[device.id] = device.tagIds;

                //Add options in copy from existing device modal
                $("#deviceContent .form-control#deviceSelect").append("<option id='deviceSelectOption" + device.id + "' value='" + device.id + "'>\n" +
                    device.name + "</option>");

                deviceTable.on("click", "#editButton" + device.id, function () {
                    $.post("/edit-device", {id: device.id}, function () {
                        $('html, body').animate({scrollTop: 0}, 'fast', function () {
                        });
                        $("#deviceContent #submitFormButton").html("Update");
                        $("#deviceContent #clearFormButton").html("Cancel Edit");

                        //create device object
                        let newDevice = {
                            type: {
                                name: $("#deviceTableBody #deviceType" + device.id).html()
                            },
                            name: $("#deviceTableBody #name" + device.id).html(),
                            description: $("#deviceTableBody #description" + device.id).html(),
                            group: {
                                name: $("#deviceTableBody #group" + device.id).html()
                            },
                            ip: $("#deviceTableBody #ipAddress" + device.id).html(),
                            statusHistorySize: $("#deviceTableBody #statusHistorySize" + device.id).html(),
                            samplingRate: $("#deviceTableBody #samplingRate" + device.id).html(),
                            tagIds: findTagIdsByDevice(device.id)
                        }

                        populateForm(newDevice);
                    });
                });

                deviceTable.on("click", "#deleteButton" + device.id, function () {
                    $.post("/delete-device", {id: device.id}, function (isSuccess) {
                        if (isSuccess == "true") {
                            deviceTable.row("#tableRow" + device.id).remove().draw();
                            $("#deviceSelectOption" + device.id).remove();   //remove option from copy device list
                        } else {
                            alert("Delete was unsuccessful.  Please check that another table entry " +
                                "does not rely on this Device");
                        }
                    });
                });
            });
        });
    }

    $("#deviceContent #clearFormButton").click(function () {
        $.post("/clear-device-form", {}, function () {
            $("#deviceContent #submitFormButton").html("Add");
            $("#deviceContent #clearFormButton").html("Clear");

            //create device object
            let newDevice = {
                type: {
                    name: typeIdToNameMap[$("#deviceContent .form-control#type option:first").val()]
                },
                name: "",
                description: "",
                group: {
                    name: ""
                },
                ip: "",
                statusHistorySize: "",
                samplingRate: "",
                tagIds: []
            }

            populateForm(newDevice);
        });
    });

    //populate device form with information from the device copying from
    $("#copyFromDeviceModalForm").submit(function (e) {
        e.preventDefault();
        let deviceId = document.getElementById("deviceSelect").value;
        $.get("/device", {id: deviceId}, function (device) {
            populateForm(JSON.parse(device))
        });
        $("#copyFromDeviceModal").modal("hide");
    });

    //only load data when tab is active
    $('a[href="#DeviceContent"]').on('shown.bs.tab', function (e) {
        getDevices();
    });
});