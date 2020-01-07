let given_id_conditions = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    let alertConditionTable = $('#alertConditionTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    //map of characters we wish to escape before placing directly into HTML
    let entityMap = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#39;',
        '/': '&#x2F;',
        '`': '&#x60;',
        '=': '&#x3D;'
    };

    let conditionIdToDeviceIdMap = {};
    let conditionIdToLookupIdMap = {};

    //a counter that will give each new variable a unique id
    let variableCounter = 0;

    function escapeHtml (string) {
        return String(string).replace(/[&<>"'`=\/]/g, function (s) {
            return entityMap[s];
        });
    }

    //construct a string from a variable map so that it can be displayed in the table
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

    //add a row to the top form for a new variable mapping
    function addVariableRow(key, value) {
        variableCounter++;
        let currentCount = variableCounter;

        let newRow = "<tr id='variableTableRow" + currentCount + "'>\n" +
            "    <td class='fit'><button type='button' class='btn btn-primary btn-sm' id='removeButton" + currentCount + "'>Remove</button></td>" +
            "    <td id='key" + currentCount + "'>" + key + "</td>\n" +
            "    <td id='value" + currentCount + "'>" + value + "</td>\n" +
            "</tr>"

        $("#editVariablesModal #variableTable").find("tbody").append($(newRow));

        //add pairing to form data
        $("#editVariablesModal #variableFormInput").append("<input type='text' id='variableInput" + currentCount + "' " +
            "name='variables[" + escapeHtml(key) + "]' value='" + escapeHtml(value) + "' hidden>");
        $("#editVariablesModal #variableTableBody #removeButton" + currentCount).click(function () {
            $("#editVariablesModal #variableTableBody #variableTableRow" + currentCount).remove();

            //remove pairing from form data
            $("#editVariablesModal #variableFormInput #variableInput" + currentCount).remove()
        });
    }

    //when editing an alert condition, populate the form with each of the variables from that table row
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

    //retrieve all alert conditions and populate the table
    function getAllAlertConditions() {
        alertConditionTable.clear();

        $.get("/get-alert-conditions-by-device",{id:given_id_conditions}, (alertConditions) => {
            let conditions = JSON.parse(alertConditions);

            $.each(conditions, (index, alertCondition) => {
                conditionIdToDeviceIdMap[alertCondition.id] = alertCondition.deviceId;
                conditionIdToLookupIdMap[alertCondition.id] = alertCondition.alertTypeLookupId;

                let newRow = "<tr id='tableRow" + alertCondition.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + alertCondition.id + "'"+
                    "                   role='button' data-toggle='modal' data-target='#editVariablesModal'>Edit</button> "+
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + alertCondition.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td id='device" + alertCondition.id + "'>" + alertCondition.deviceName + "</td>\n" +
                    "    <td id='alertType" + alertCondition.id + "'>" + alertCondition.alertTypeName + "</td>\n" +
                    "    <td class='fit' id='variables" + alertCondition.id + "'>" + makeVariablesString(alertCondition.variables) + "</td>\n" +
                    "</tr>"
                alertConditionTable.row.add($(newRow)).draw();

                alertConditionTable.on("click", "#editButton" + alertCondition.id, function () {
                    $("#alertConditionContent #variableFormInput").append("<input type='text' id='deviceIdInput' name='deviceId' " +
                        "value='" + escapeHtml(conditionIdToDeviceIdMap[alertCondition.id]) + "' hidden>");
                    $("#alertConditionContent #variableFormInput").append("<input type='text' id='alertTypeLookupIdInput' name='alertTypeLookupId' " +
                        "value='" + escapeHtml(conditionIdToLookupIdMap[alertCondition.id]) + "' hidden>");
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
        let keyInput = $("#editVariablesModal .form-control#variableKey");
        let valueInput = $("#editVariablesModal .form-control#variableValue");

        addVariableRow(keyInput.val(), valueInput.val());

        keyInput.val("");
        valueInput.val("");
    });

    //only load content if the tab is active
    $('a[href="#deviceAlertConditionsContent"]').on('shown.bs.tab', function (e) {
        getAllAlertConditions();
    });
});
