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
    let groupNameToIDMap = {};   //map to go from group name in the table to groupID in the form select
    let tagNameToIDMap = {};   //map to go from tag name in the table to tagID in the form select

    function checkAllTagsOnEdit(id) {
        let commaSeparatedString = $("#deviceContent #tags");
        let tagList = commaSeparatedString.split(", ");

        taglist.forEach(tag => {
            $('#deviceContent #formTags .form-check-input').prop( "checked", false );
        })
    }

    //fill device types in form
    $.get("/types", (types) => {
        $.each(JSON.parse(types), (id,type) => {
            typeNameToIDMap[type.name] = type.id;
            $("#deviceContent #type").append("<option id='typeOption" + type.id + "' value='" + type.id + "'>" + type.name + "</option>");
        });
    });

    //fill groups in form
    $.get("/groups", (groups) => {
        $.each(JSON.parse(groups), (id,group) => {
            groupNameToIDMap[group.name] = group.id;
            $("#deviceContent #group").append("<option id='groupOption" + group.id + "' value='" + group.id + "'>" + group.name + "</option>");
        });
        $("#deviceContent #group").append("<option value='-1' hidden></option>");   //assuming this is to allow an empty group
    });

    //fill security states in form
    $.get("/security-states", (securityStates) => {
        $.each(JSON.parse(securityStates), (id,securityState) => {
            $("#deviceContent #securityState").append("<option id='securityStateOption" + securityState.id + "' value='" + securityState.id + "'>\n" +
                securityState.name + "</option>");
        });
        $("#deviceContent #securityState").append("<option value='-1' hidden></option>");
    });

    //fill tags in form
    $.get("/tags", (tags) => {
        $.each(JSON.parse(tags), (id,tag) => {
            groupNameToIDMap[tag.name] = tag.id;
            $("#deviceContent #formtags").append("<div class='form-check col-2'>\n" +
                "    <input class='form-check-input' type='checkbox' id='tagCheckbox" + tag.id + "' name='tagIds[]' value='" + tag.id + "'>\n" +
                "    <label class='form-check-label' for='tagCheckBox" + tag.id + "'>" + tag.name + "</label>\n" +
                "</div>");
        });
    });

    $.get("/devices", (devices) => {
        $.each(JSON.parse(devices), (index, device) => {
            let deviceGroupName = device.group ? device.group.name : "N/A";
            let lastAlertName = device.lastAlert ? device.lastAlert.name : "N/A";

            let newRow = "<tr id='tableRow" + device.id + "'>\n" +
                "    <td class='fit'>" +
                "        <div class='editDeleteContainer' >" +
                "           <button type='button' class='btn btn-primary btn-xs' id='editButton" + device.id + "'>Edit</button>" +
                "           <button type='button' class='btn btn-secondary btn-xs' id='deleteButton" + device.id + "'>Delete</button>" +
                "        </div>" +
                "    </td>\n" +
                "    <td class='fit' id='deviceID" + device.id + "'>" + device.id + "</td>\n" +
                "    <td id='name" + device.id + "'>" + device.name + "</td>\n" +
                "    <td id='description" + device.id + "'>" + device.description + "</td>\n" +
                "    <td id='deviceType" + device.id + "'>" + device.type.name + "</td>\n" +
                "    <td id='group" + device.id + "'>" + deviceGroupName + "</td>\n" +
                "    <td id='ipAddress" + device.id + "'>" + device.ip + "</td>\n" +
                "    <td id='currentState" + device.id + "'>" + device.currentState.name + "</td>\n" +
                "    <td id='lastAlert" + device.id + "'>" + lastAlertName + "</td>\n" +
                "    <td id='statusHistorySize" + device.id + "'>" + device.statusHistorySize + "</td>\n" +
                "    <td id='samplingRate" + device.id + "'>" + device.samplingRate + "</td>\n" +
                "    <td id='tags" + device.id + "'>" + device.tagIds.join(", ") + "</td>\n" +
                "</tr>"

            deviceTable.row.add($(newRow)).draw();

            $("#deviceTableBody #editButton" + device.id).click(function () {
                $.post("/edit-device", {id: device.id}, function () {
                    $('html, body').animate({scrollTop: 0}, 'fast', function () {});
                    $("#deviceContent #submitFormButton").html("Update");
                    $("#deviceContent #clearFormButton").html("Cancel Edit");
                    $("#deviceContent .form-control#type").val(typeNameToIDMap[$("#deviceTableBody #deviceType" + device.id).html()]);
                    $("#deviceContent .form-group #name").val($("#deviceTableBody #name" + device.id).html());
                    $("#deviceContent .form-group #description").val($("#deviceTableBody #description" + device.id).html());
                    console.log(groupNameToIDMap);
                    console.log($("#deviceTableBody #group" + device.id).html());
                    $("#deviceContent .form-control#group").val(groupNameToIDMap[$("#deviceTableBody #group" + device.id).html()]);
                    $("#deviceContent .form-group #ipAddress").val($("#deviceTableBody #ipAddress" + device.id).html());
                    $("#deviceContent .form-group #statusHistorySize").val($("#deviceTableBody #statusHistorySize" + device.id).html());
                    $("#deviceContent .form-group #samplingRate").val($("#deviceTableBody #samplingRate" + device.id).html());
                    $("#deviceContent .form-control#securityState").val($("#deviceTableBody #securityState" + device.id).html());
                    checkAllTagsOnEdit(device.id);
                    //$("#deviceContent .form-group #tags").val($("#deviceTableBody #tags" + device.id).html());

                });
            });

            $("#deviceTableBody #deleteButton" + device.id).click(function () {
                $.post("/delete-device", {id: device.id}, function (isSuccess) {
                    if (isSuccess == "true") {
                        deviceTable.row("#tableRow" + device.id).remove().draw();
                    } else {
                        alert("delete was unsuccessful");
                    }

                });
            });
        });
    });

    $("#deviceContent #clearFormButton").click(function () {
        $.post("/clear-device-form", {}, function () {
            $("#deviceContent #submitFormButton").html("Add");
            $("#deviceContent #clearFormButton").html("Clear");
            $("#deviceContent .form-control#type").val(1);
            $("#deviceContent .form-group #name").val("");
            $("#deviceContent .form-group #description").val("");
            $("#deviceContent .form-control#group").val(1);
            $("#deviceContent .form-group #ipAddress").val("");
            $("#deviceContent .form-group #historySize").val("");
            $("#deviceContent .form-group #samplingRate").val("");
            $("#deviceContent .form-control#securityState").val(1);
            $('#deviceContent #tags .form-check-input').prop( "checked", false );
        });
    });
});