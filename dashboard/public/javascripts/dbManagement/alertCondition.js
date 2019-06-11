jQuery(document).ready(($) => {
    let alertConditionTable = $('#alertConditionTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    let alertTypeIDtoNameMap = {};
    let alertTypeNametoIDMap = {};
    let deviceIDtoNameMap = {};

    $.get("/alert-types", (alertTypes) => {
        $.each(JSON.parse(alertTypes), (id, alertType) => {
            $(".form-control#alertType").append("<option id='alertTypeOption" + alertType.id + "' value='" + alertType.id + "'>"
                + alertType.name +
                "</option>")
            alertTypeIDtoNameMap[alertType.id] = alertType.name;
            alertTypeNametoIDMap[alertType.name] = alertType.id;
        });
    });

    $.get("/devices", (devices) => {
        $.each(JSON.parse(devices), (id, device) => {
            deviceIDtoNameMap[device.id] = device.name;
        });
    });

    $.get("/alert-conditions", (alertConditions) => {
        $.each(JSON.parse(alertConditions), (index, alertCondition) => {
            let newRow = "<tr id='tableRow" + alertCondition.id + "'>\n" +
                "    <td class='fit'>" +
                "        <div class='editDeleteContainer' >" +
                "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + alertCondition.id + "'>Edit</button>" +
                "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + alertCondition.id + "'>Delete</button>" +
                "        </div>" +
                "    </td>\n" +
                "    <td id='device" + alertCondition.id + "'>" + deviceIDtoNameMap[alertCondition.deviceId]+ "</td>\n" +
                "    <td id='alertType" + alertCondition.id + "'>" + alertTypeIDtoNameMap[alertcondition.alertTypeId] + "</td>\n" +
                "</tr>"
            alertConditionTable.row.add($(newRow)).draw();

            $("#alertConditionTableBody #editButton" + alertCondition.id).click(function () {
                $.post("/edit-alert-condition", {id: alertCondition.id}, function () {
                    let alertTypeName = $("#alertConditionTableBody #alertType" + alertCondition.id).html();
                    $('html, body').animate({scrollTop: 0}, 'fast', function () {});
                    $("#alertConditionContent #submitFormButton").html("Update");
                    $("#alertConditionContent #clearFormButton").html("Cancel Edit");
                    $("#alertConditionContent .form-control#alertType").val(alertTypeNametoIDMap[alertTypeName]).change();
                });
            });

            $("#alertConditionTableBody #deleteButton" + alertCondition.id).click(function () {
                $.post("/delete-alert-condition", {id: alertCondition.id}, function (isSuccess) {
                    if (isSuccess == "true") {
                        alertConditionTable.row("#tableRow" + alertCondition.id).remove().draw();
                    } else {
                        alert("delete was unsuccessful");
                    }

                });
            });
        });
    });

    $("#alertConditionContent #clearFormButton").click(function () {
        $.post("/clear-alert-condition-form", {}, function () {
            $("#alertConditionContent #submitFormButton").html("Add");
            $("#alertConditionContent #clearFormButton").html("Clear");
            $("#alertConditionContent .form-control#alertType").val("");
        });
    });
});