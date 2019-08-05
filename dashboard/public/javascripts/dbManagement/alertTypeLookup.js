jQuery(document).ready(($) => {
    let alertTypeLookupTable = $('#alertTypeLookupTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    let alertTypeIDtoNameMap = {};
    let alertTypeNameToIDMap = {};
    let deviceTypeIDtoNameMap = {};
    let deviceTypeNameToIDMap = {};

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

    async function getAllAlertTypes() {
        $("#alertTypeLookupContent .form-control#alertType").empty();

        return $.get("/alert-types", (alertTypes) => {
            $.each(JSON.parse(alertTypes), (id, alertType) => {
                $("#alertTypeLookupContent .form-control#alertTypeSelect").append("<option id='alertTypeOption" + alertType.id + "' value='" + alertType.id + "'>"
                    + alertType.name +
                    "</option>")
                alertTypeIDtoNameMap[alertType.id] = alertType.name;
                alertTypeNameToIDMap[alertType.name] = alertType.id;
            });
        });
    }

    async function getAllDeviceTypes() {
        $("#alertTypeLookupContent .form-control#deviceTypeSelect").empty();

        return $.get("/device-types", (deviceTypes) => {
            $.each(JSON.parse(deviceTypes), (id, deviceType) => {
                $("#alertTypeLookupContent .form-control#deviceTypeSelect").append("<option id='typeOption" + deviceType.id + "' value='" + deviceType.id + "'>"
                    + deviceType.name +
                    "</option>")
                deviceTypeIDtoNameMap[deviceType.id] = deviceType.name;
                deviceTypeNameToIDMap[deviceType.name] = deviceType.id;
            });
        });
    }

    async function getAllAlertTypeLookups() {
        await getAllAlertTypes();
        await getAllDeviceTypes();

        alertTypeLookupTable.clear();

        $.get("/alert-type-lookups", (alertTypeLookups) => {
            $.each(JSON.parse(alertTypeLookups), (index, alertTypeLookup) => {
                let newRow = "<tr id='tableRow" + alertTypeLookup.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + alertTypeLookup.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + alertTypeLookup.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td id='deviceType" + alertTypeLookup.id + "'>" + deviceTypeIDtoNameMap[alertTypeLookup.deviceTypeId] + "</td>\n" +
                    "    <td id='alertType" + alertTypeLookup.id + "'>" + alertTypeIDtoNameMap[alertTypeLookup.alertTypeId] + "</td>\n" +
                    "    <td class='fit' id='variables" + alertTypeLookup.id + "'>" + makeVariablesString(alertTypeLookup.variables) + "</td>\n" +
                    "</tr>"
                alertTypeLookupTable.row.add($(newRow)).draw();

                alertTypeLookupTable.on("click", "#editButton" + alertTypeLookup.id, function () {
                    $.post("/edit-alert-type-lookup", {id: alertTypeLookup.id}, function() {
                        let alertTypeName = $("#alertTypeLookupTableBody #alertType" + alertTypeLookup.id).html();
                        let deviceTypeName = $("#alertTypeLookupTableBody #deviceType" + alertTypeLookup.id).html();

                        $('html, body').animate({scrollTop: 0}, 'fast', function () {});
                        $("#alertTypeLookupContent #submitFormButton").html("Update");
                        $("#alertTypeLookupContent #clearFormButton").html("Cancel Edit");
                        $("#alertTypeLookupContent .form-control#alertTypeSelect").val(alertTypeNameToIDMap[alertTypeName]).change();
                        $("#alertTypeLookupContent .form-control#deviceTypeSelect").val(deviceTypeNameToIDMap[deviceTypeName]).change();
                        $("#alertTypeLookupContent #variableTableBody").empty();
                        populateVariablesTableFromString($("#alertTypeLookupTableBody #variables" + alertTypeLookup.id).html());
                    });
                });

                alertTypeLookupTable.on("click", "#deleteButton" + alertTypeLookup.id, function () {
                    $.post("/delete-alert-type-lookup", {id: alertTypeLookup.id}, function (isSuccess) {
                        if (isSuccess == "true") {
                            alertTypeLookupTable.row("#tableRow" + alertTypeLookup.id).remove().draw();
                        } else {
                            alert("Delete was unsuccessful.  Please check that another table entry " +
                                "does not rely on this Alert Type Lookup");
                        }
                    });
                });
            });
        });
    }

    $("#alertTypeLookupContent #addVariableButton").click(function () {
        let keyInput = $(".form-control#variableKey");
        let valueInput = $(".form-control#variableValue");

        addVariableRow(keyInput.val(), valueInput.val());

        keyInput.val("");
        valueInput.val("");
    });

    $("#alertTypeLookupContent #clearFormButton").click(function () {
        let alertTypeSelect = $("#alertTypeLookupContent .form-control#alertType");
        let typeSelect = $("#alertTypeLookupContent .form-control#deviceTypeSelect");

        $("#alertTypeLookupContent #submitFormButton").html("Add");
        $("#alertTypeLookupContent #clearFormButton").html("Clear");
        alertTypeSelect.val(alertTypeSelect.find("option:first").val());
        typeSelect.val(typeSelect.find("option:first").val());
        $("#alertTypeLookupContent .form-control#variableKey").val("");
        $("#alertTypeLookupContent .form-control#variableValue").val("");
        $("#alertTypeLookupContent #variableTable").find("tr:gt(0)").remove();   //remove all rows except header
    });

    //only load content if the tab is active
    $('a[href="#AlertTypeLookupContent"]').on('shown.bs.tab', function (e) {
        getAllAlertTypeLookups();
    });
});