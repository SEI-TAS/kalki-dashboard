jQuery.ajaxSetup({async: false});

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
    let deviceNametoIDMap = {};
    let deviceTypeIDtoNameMap = {};
    let deviceTypeNametoIDMap = {};

    let variableCounter = 0;

    function makeVariablesString(variables) {
        let resultString = "";
        if (variables != null) {
            Object.keys(variables).forEach(key => {
                resultString = resultString + key + ": " + variables[key] + "<br>";
            });

            resultString = resultString.substring(0, resultString.length - 4); //remove the last <br>
        }

        return resultString;
    }

    function addVariableRow(key, value) {
        variableCounter++;
        let currentCount = variableCounter;

        let newRow = "<tr id='variableTableRow" + currentCount + "'>\n" +
            "    <td class='fit'><button type='button' class='btn btn-primary btn-sm' id='removeButton" + currentCount + "'>Remove</button></td>" +
            "    <td id='key" + currentCount + "'>" + key + "</td>\n" +
            "    <td id='value" + currentCount + "'>" + value + "</td>\n" +
            "</tr>"

        $("#variableTable").find("tbody").append($(newRow));

        //add pairing to form data
        $("#variableFormInput").append("<input type='text' id='variableInput" + currentCount + "' name='variables[" + key + "]' value='" + value + "' hidden>");

        $("#variableTableBody #removeButton" + currentCount).click(function () {
            $("#variableTableBody #variableTableRow" + currentCount).remove();

            //remove pairing from form data
            $("#variableFormInput #variableInput" + currentCount).remove()
        });
    }

    function populateVariablesTableFromString(variablesString) {
        if (variablesString != "") {
            let variablesArray = variablesString.split("<br>");

            variablesArray.forEach(variableString => {
                let colonIndex = variableString.indexOf(":");
                let key = variableString.substring(0, colonIndex);
                let value = variableString.substring(colonIndex + 2, variableString.length);

                addVariableRow(key, value);
            });
        }
    }

    function disableSwitch() {
        let selectSwitch = $("#alertConditionContent #alertConditionSwitch");
        selectSwitch.prop("checked", false);
        selectSwitch.prop("disabled", true);
        $(".form-control#typeSelect").prop("disabled", true);
        $(".form-control#deviceSelect").prop("disabled", false);
        $("#typeLabel").removeClass("selected");
        $("#deviceLabel").addClass("selected");
    }

    function enableSwitch() {
        let selectSwitch = $("#alertConditionContent #alertConditionSwitch");
        selectSwitch.prop("disabled", false);
    }

    $.get("/alert-types", (alertTypes) => {
        $.each(JSON.parse(alertTypes), (id, alertType) => {
            $("#alertConditionContent .form-control#alertType").append("<option id='alertTypeOption" + alertType.id + "' value='" + alertType.id + "'>"
                + alertType.name +
                "</option>")
            alertTypeIDtoNameMap[alertType.id] = alertType.name;
            alertTypeNametoIDMap[alertType.name] = alertType.id;
        });
    });
    $.get("/devices", (devices) => {
        $.each(JSON.parse(devices), (id, device) => {
            $("#alertConditionContent .form-control#deviceSelect").append("<option id='deviceOption" + device.id + "' value='" + device.id + "'>"
                + device.name +
                "</option>")
            deviceIDtoNameMap[device.id] = device.name;
            deviceNametoIDMap[device.name] = device.id;
        });
    });
    $.get("/device-types", (deviceTypes) => {
        $.each(JSON.parse(deviceTypes), (id, deviceType) => {
            $("#alertConditionContent .form-control#typeSelect").append("<option id='typeOption" + deviceType.id + "' value='" + deviceType.id + "'>"
                + deviceType.name +
                "</option>")
            deviceTypeIDtoNameMap[deviceType.id] = deviceType.name;
            deviceTypeNametoIDMap[deviceType.name] = deviceType.id;
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
                "    <td id='device" + alertCondition.id + "'>" + deviceIDtoNameMap[alertCondition.deviceId] + "</td>\n" +
                "    <td id='alertType" + alertCondition.id + "'>" + alertTypeIDtoNameMap[alertCondition.alertTypeId] + "</td>\n" +
                "    <td class='fit' id='variables" + alertCondition.id + "'>" + makeVariablesString(alertCondition.variables) + "</td>\n" +
                "</tr>"
            alertConditionTable.row.add($(newRow)).draw();

            alertConditionTable.on("click", "#editButton" + alertCondition.id, function () {
                $.post("/edit-alert-condition", {id: alertCondition.id}, function () {
                    let alertTypeName = $("#alertConditionTableBody #alertType" + alertCondition.id).html();
                    let deviceName = $("#alertConditionTableBody #device" + alertCondition.id).html();

                    $('html, body').animate({scrollTop: 0}, 'fast', function () {
                    });
                    $("#alertConditionContent #submitFormButton").html("Update");
                    $("#alertConditionContent #clearFormButton").html("Cancel Edit");
                    $("#alertConditionContent .form-control#alertType").val(alertTypeNametoIDMap[alertTypeName]).change();
                    $("#alertConditionContent .form-control#deviceSelect").val(deviceNametoIDMap[deviceName]).change();
                    populateVariablesTableFromString($("#alertConditionTableBody #variables" + alertCondition.id).html());

                    //disbable inputting device type
                    disableSwitch();
                });
            });

            alertConditionTable.on("click", "#deleteButton" + alertCondition.id, function () {
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

    $("#alertConditionContent #addVariableButton").click(function () {
        let keyInput = $(".form-control#variableKey");
        let valueInput = $(".form-control#variableValue");

        addVariableRow(keyInput.val(), valueInput.val());

        keyInput.val("")
        valueInput.val("");
    });


    $("#alertConditionContent #alertConditionSwitch").click(function () {
        let typeSelect = $(".form-control#typeSelect");
        let typeLabel = $("#typeLabel");
        let deviceSelect = $(".form-control#deviceSelect");
        let deviceLabel = $("#deviceLabel");

        if ($(this).is(':checked')) {
            typeSelect.prop("disabled", false);
            deviceSelect.prop("disabled", true);

        } else {
            typeSelect.prop("disabled", true);
            deviceSelect.prop("disabled", false);
        }

        typeLabel.toggleClass("selected");
        deviceLabel.toggleClass("selected");
    });

    $("#alertConditionContent #clearFormButton").click(function () {
        $.post("/clear-alert-condition-form", {}, function () {
            $("#alertConditionContent #submitFormButton").html("Add");
            $("#alertConditionContent #clearFormButton").html("Clear");
            $("#alertConditionContent .form-control#alertType").val("");
            $("#alertConditionContent .form-control#deviceSelect").val("");
            $("#alertConditionContent .form-control#typeSelect").val("");
            $("#alertConditionContent .form-control#variableKey").val("");
            $("#alertConditionContent .form-control#variableValue").val("");
            $("#alertConditionContent #variableTable").find("tr:gt(0)").remove();   //remove all rows except header

            enableSwitch();
        });
    });
});

jQuery.ajaxSetup({async: true});