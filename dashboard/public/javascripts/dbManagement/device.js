jQuery(document).ready(($) => {
    let deviceTable = $('#deviceTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

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
                "    <td id='deviceGroup" + device.id + "'>" + deviceGroupName + "</td>\n" +
                "    <td id='ipAddress" + device.id + "'>" + device.ip + "</td>\n" +
                "    <td id='currentState" + device.id + "'>" + device.currentState.name + "</td>\n" +
                "    <td id='lastAlert" + device.id + "'>" + lastAlertName + "</td>\n" +
                "    <td id='statusHistorySize" + device.id + "'>" + device.type.name + "</td>\n" +
                "    <td id='samplingRate" + device.id + "'>" + device.samplingRate + "</td>\n" +
                "    <td id='tags" + device.id + "'>" + device.tagIds + "</td>\n" +      //TODO: figure out how to get comma separated list from ids
                "</tr>"

            deviceTable.row.add($(newRow)).draw();

            $("#deviceTableBody #editButton" + device.id).click(function () {
                $.post("/edit-device", {id: device.id}, function () {
                    $('html, body').animate({scrollTop: 0}, 'fast', function () {});
                    $("#deviceContent #submitFormButton").html("Update");
                    $("#deviceContent #clearFormButton").html("Cancel Edit");
                    $("#deviceContent .form-group #name").val($("#deviceTableBody #name" + device.id).html());
                    $("#deviceContent .form-group #description").val($("#deviceTableBody #description" + device.id).html());
                    $("#deviceContent .form-control#source").val($("#deviceTableBody #source" + device.id).html()).change();
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

    //fill device types in form
    $.get("/types", (types) => {
        $.each(JSON.parse(types), (id,type) => {
            $("#deviceContent #type").append("<option id='typeOption" + type.id + "' value='" + type.id + "'>" + type.name + "</option>");
        });
    });

    //fill groups in form
    $.get("/groups", (groups) => {
        $.each(JSON.parse(groups), (id,group) => {
            $("#deviceContent #group").append("<option id='groupOption" + group.id + "' value='" + group.id + "'>" + group.name + "</option>");
        });
        $("#deviceContent #group").append("<option value='-1' hidden></option>");
    });

    //fill security states in form
    $.get("/security-states", (securityStates) => {
        $.each(JSON.parse(securityStates), (id,securityState) => {
            $("#deviceContent #securityState").append("<option id='securityStateOption" + securityState.id + "' value='" + securityState.id + "'>\n" +
                securityState.name + "</option>");
        });
        $("#deviceContent #securityState").append("<option value='-1' hidden></option>");
    });

    $.get("/tags", (tags) => {
        $.each(JSON.parse(tags), (id,tag) => {
            $("#deviceContent #tags").append("<div class='form-check col-2'>\n" +
                "    <input class='form-check-input' type='checkbox' id='tagCheckbox" + tag.id + "' name='tagIds[]' value='" + tag.id + "'>\n" +
                "    <label class='form-check-label' for='tagCheckBox" + tag.id + "'>" + tag.name + "</label>\n" +
                "</div>");
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
            $('#deviceContent #tags .form-check-input').prop( "checked", false );;
        });
    });
});