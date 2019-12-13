jQuery(document).ready(($) => {

    let typeNameToIDMap = {};   //map to go from deviceType name in the table to typeID in the form select
    let typeIdToNameMap = {};
    let groupNameToIDMap = {};   //map to go from group name in the table to groupID in the form select
    let tagIDtoNameMap = {};   //map to go from tag name in the table to tagID in the form select
    let deviceIdToTagIdsMap = {}    //map to retrieve list of tagIds based on deviceID

    //helper function to populate the device form
    // function populateForm(device) {
    //     let deviceGroup = device["group"];
    //     let deviceGroupName = deviceGroup ? deviceGroup["name"] : "";
    //     $(".form-control#type").val(typeNameToIDMap[device["type"]["name"]]);
    //     $(".form-group #name").val(device["name"]).change();
    //     $(".form-group #description").val(device["description"]);
    //     $(".form-control#group").val(groupNameToIDMap[deviceGroupName]);
    //     $(".form-group #ipAddress").val(device["ip"]);
    //     $(".form-group #statusHistorySize").val(device["statusHistorySize"]).change();
    //     $(".form-group #samplingRate").val(device["samplingRate"]).change();
    //     checkTags(device["tagIds"]);
    // }

    $("#editButton").click(function () {
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
        });
    });

    $("#deleteButton").click(function () {
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

    $("#clearFormButton").click(function () {
        console.log("clicked clear");
        $.post("/clear-device-form", {}, function () {
            $("#submitFormButton").html("Add");
            $("#clearFormButton").html("Clear");

            //create device object
            let newDevice = {
                type: {
                    name: typeIdToNameMap[$(".form-control#type option:first").val()]
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

    //searches through the tags and checks each box given the list of tagIds
    function checkTags(tagIds) {
        //uncheck all tags
        $('input:checkbox').removeAttr('checked');

        tagIds.forEach(tagId => {
            $('#deviceContent #formTags #tagCheckbox' + tagId).prop("checked", true);
        })
        $('input:checkbox#hiddenChk').prop("checked", true);
    }

    //convert list of tagIds to comma separated names as a string to display in the table
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

    async function getDeviceTypes() {
        $("#type").empty();

        return $.get("/device-types", (types) => {
            $.each(JSON.parse(types), (id, type) => {
                typeNameToIDMap[type.name] = type.id;
                typeIdToNameMap[type.id] = type.name;
                $("#type").append("<option id='typeOption" + type.id + "' value='" + type.id + "'>" + type.name + "</option>");
            });
        });
    }

    async function getGroups() {
        $("#group").empty();

        return $.get("/groups", (groups) => {
            groupNameToIDMap[""] = -1;
            groupNameToIDMap["N/A"] = -1;
            $("#group").append("<option value='-1'></option>");   //assuming this is to allow an empty group

            $.each(JSON.parse(groups), (id, group) => {
                groupNameToIDMap[group.name] = group.id;
                $("#group").append("<option id='groupOption" + group.id + "' value='" + group.id + "'>" + group.name + "</option>");
            });
        });
    }

    async function getTags() {
        $("#formTags").empty();

        return $.get("/tags", (tags) => {
            $.each(JSON.parse(tags), (id, tag) => {
                tagIDtoNameMap[tag.id] = tag.name;
                $("#formTags").append("<div class='form-check col-2'>\n" +
                    "    <input class='form-check-input' type='checkbox' id='tagCheckbox" + tag.id + "' name='tagIds[]' value='" + tag.id + "'>\n" +
                    "    <label class='form-check-label' for='tagCheckBox" + tag.id + "'>" + tag.name + "</label>\n" +
                    "</div>");
            });
            $("#formTags").append("<input class='form-check-input' id='hiddenChk' type='checkbox' name='tagIds[]' value='-1' hidden checked>");
        });
    }

    getDeviceTypes();
    getGroups();
    getTags();
});
