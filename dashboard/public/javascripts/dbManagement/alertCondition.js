jQuery(document).ready(($) => {
    let alertConditionTable = $('#alertConditionTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    let conditionIdToDeviceIdMap = {};
    let conditionIdToLookupIdMap = {};

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

    function getAllAlertConditions() {
        alertConditionTable.clear();

        $.get("/alert-conditions", (alertConditions) => {
            $.each(JSON.parse(alertConditions), (index, alertCondition) => {
                conditionIdToDeviceIdMap[alertCondition.id] = alertCondition.deviceId;
                conditionIdToLookupIdMap[alertCondition.id] = alertCondition.alertTypeLookupId;

                let newRow = "<tr id='tableRow" + alertCondition.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + alertCondition.id + "'"+
                    "                   role='button' data-toggle='modal' data-target='#selectMonitoringValuesModal'>Edit</button> "+
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + alertCondition.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td id='device" + alertCondition.id + "'>" + alertCondition.deviceName + "</td>\n" +
                    "    <td id='alertType" + alertCondition.id + "'>" + alertCondition.alertTypeName + "</td>\n" +
                    "    <td class='fit' id='variables" + alertCondition.id + "'>" + makeVariablesString(alertCondition.variables) + "</td>\n" +
                    "</tr>"
                alertConditionTable.row.add($(newRow)).draw();

                alertConditionTable.on("click", "#editButton" + alertCondition.id, function () {
                    $("alertConditionContent #variableFormInput").append("<input type='text' id='deviceIdInput' name='deviceId' " +
                        "value='" + conditionIdToDeviceIdMap[alertCondition.id] + "' hidden>");
                    $("alertConditionContent #variableFormInput").append("<input type='text' id='alertTypeLookupIdInput' name='alertTypeLookupId' " +
                        "value='" + conditionIdToLookupIdMap[alertCondition.id] + "' hidden>");
                    $("#alertConditionContent #variableTableBody").empty();
                    populateVariablesTableFromString($("#alertConditionTableBody #variables" + alertCondition.id).html());
                });

                alertConditionTable.on("click", "#deleteButton" + alertCondition.id, function () {
                    $.post("/delete-alert-condition", {id: alertCondition.id}, function (isSuccess) {
                        if (isSuccess == "true") {
                            alertConditionTable.row("#tableRow" + alertCondition.id).remove().draw();
                        } else {
                            alert("Delete was unsuccessful.  Please check that another table entry " +
                                "does not rely on this Alert Condition");
                        }
                    });
                });
            });
        });
    }

    $("#alertConditionContent #addVariableButton").click(function () {
        let keyInput = $(".form-control#variableKey");
        let valueInput = $(".form-control#variableValue");

        addVariableRow(keyInput.val(), valueInput.val());

        keyInput.val("");
        valueInput.val("");
    });

    //only load content if the tab is active
    $('a[href="#AlertConditionContent"]').on('shown.bs.tab', function (e) {
        getAllAlertConditions();
    });
});