jQuery(document).ready(($) => {
    let alertTypeLookupTable = $('#alertTypeLookupTable').DataTable(
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

    //id to name mappings to easily fill form and table names and ids without querying the DB again
    let alertTypeIDtoNameMap = {};
    let alertTypeNameToIDMap = {};
    let deviceTypeIDtoNameMap = {};
    let deviceTypeNameToIDMap = {};

    //a counter that will give each new variable a unique id
    let variableCounter = 0;

    function escapeHtml (string) {
        return String(string).replace(/[&<>"'`=\/]/g, function (s) {
            return entityMap[s];
        });
    }

    //create a string from a variable mapping to be placed into the table
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

    //add a variable row to the form for editing or creating new alertTypes
    function addVariableRow(key, value) {
        variableCounter++;
        let currentCount = variableCounter;

        console.log(typeof key);

        let newRow = "<tr id='variableTableRow" + currentCount + "'>\n" +
            "    <td class='fit'><button type='button' class='btn btn-primary btn-sm' id='removeButton" + currentCount + "'>Remove</button></td>" +
            "    <td id='key" + currentCount + "'>" + key + "</td>\n" +
            "    <td id='value" + currentCount + "'>" + value + "</td>\n" +
            "</tr>"

        $("#variableTable").find("tbody").append($(newRow));

        //add pairing to form data
        $("#variableFormInput").append("<input type='text' id='variableInput" + currentCount + "' " +
            "name='variables[" + escapeHtml(key) + "]' value='" + escapeHtml(value) + "' hidden>");

        $("#variableTableBody #removeButton" + currentCount).click(function () {
            $("#variableTableBody #variableTableRow" + currentCount).remove();

            //remove pairing from form data
            $("#variableFormInput #variableInput" + currentCount).remove()
        });
    }

    //given variable mappings from an alertType, populate the top form with rows for each
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

    //query the database for all alert types
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

    //query the database for all device types
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
        //must wait for these functions to complete to ensure the mappings are present
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
                        $("#alertTypeLookupContent #clearAtlFormButton").html("Cancel Edit");
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

        console.log(keyInput.val());

        addVariableRow(keyInput.val(), valueInput.val());

        keyInput.val("");
        valueInput.val("");
    });

    $("#alertTypeLookupContent #clearAtlFormButton").click(function () {
        let alertTypeSelect = $("#alertTypeLookupContent .form-control#alertType");
        let typeSelect = $("#alertTypeLookupContent .form-control#deviceTypeSelect");

        $("#alertTypeLookupContent #submitFormButton").html("Add");
        $("#alertTypeLookupContent #clearAtlFormButton").html("Clear");
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
