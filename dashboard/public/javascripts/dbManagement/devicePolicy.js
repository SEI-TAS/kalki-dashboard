jQuery(document).ready(($) => {
    let devicePolicyLookupTable = $('#devicePolicyTable').DataTable(
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
    let devicePolicyIDtoNameMap = {};
    let devicePolicyNameToIDMap = {};
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

    //add a variable row to the form for editing or creating new devicePolicys
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

    //given variable mappings from an devicePolicy, populate the top form with rows for each
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
    async function getAllDevicePolicys() {
        $("#devicePolicyLookupContent .form-control#devicePolicy").empty();

        return $.get("/alert-types", (devicePolicys) => {
            $.each(JSON.parse(devicePolicys), (id, devicePolicy) => {
                $("#devicePolicyLookupContent .form-control#devicePolicySelect").append("<option id='devicePolicyOption" + devicePolicy.id + "' value='" + devicePolicy.id + "'>"
                    + devicePolicy.name +
                    "</option>")
                devicePolicyIDtoNameMap[devicePolicy.id] = devicePolicy.name;
                devicePolicyNameToIDMap[devicePolicy.name] = devicePolicy.id;
            });
        });
    }

    //query the database for all device types
    async function getAllDeviceTypes() {
        $("#devicePolicyLookupContent .form-control#deviceTypeSelect").empty();

        return $.get("/device-types", (deviceTypes) => {
            $.each(JSON.parse(deviceTypes), (id, deviceType) => {
                $("#devicePolicyLookupContent .form-control#deviceTypeSelect").append("<option id='typeOption" + deviceType.id + "' value='" + deviceType.id + "'>"
                    + deviceType.name +
                    "</option>")
                deviceTypeIDtoNameMap[deviceType.id] = deviceType.name;
                deviceTypeNameToIDMap[deviceType.name] = deviceType.id;
            });
        });
    }

    async function getAllDevicePolicyLookups() {
        //must wait for these functions to complete to ensure the mappings are present
        await getAllDevicePolicys();
        await getAllDeviceTypes();

        devicePolicyLookupTable.clear();

        $.get("/alert-type-lookups", (devicePolicyLookups) => {
            $.each(JSON.parse(devicePolicyLookups), (index, devicePolicyLookup) => {
                let newRow = "<tr id='tableRow" + devicePolicyLookup.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + devicePolicyLookup.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + devicePolicyLookup.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td id='deviceType" + devicePolicyLookup.id + "'>" + deviceTypeIDtoNameMap[devicePolicyLookup.deviceTypeId] + "</td>\n" +
                    "    <td id='devicePolicy" + devicePolicyLookup.id + "'>" + devicePolicyIDtoNameMap[devicePolicyLookup.devicePolicyId] + "</td>\n" +
                    "    <td class='fit' id='variables" + devicePolicyLookup.id + "'>" + makeVariablesString(devicePolicyLookup.variables) + "</td>\n" +
                    "</tr>"
                devicePolicyLookupTable.row.add($(newRow)).draw();

                devicePolicyLookupTable.on("click", "#editButton" + devicePolicyLookup.id, function () {
                    $.post("/edit-alert-type-lookup", {id: devicePolicyLookup.id}, function() {
                        let devicePolicyName = $("#devicePolicyLookupTableBody #devicePolicy" + devicePolicyLookup.id).html();
                        let deviceTypeName = $("#devicePolicyLookupTableBody #deviceType" + devicePolicyLookup.id).html();

                        $('html, body').animate({scrollTop: 0}, 'fast', function () {});
                        $("#devicePolicyLookupContent #submitFormButton").html("Update");
                        $("#devicePolicyLookupContent #clearFormButton").html("Cancel Edit");
                        $("#devicePolicyLookupContent .form-control#devicePolicySelect").val(devicePolicyNameToIDMap[devicePolicyName]).change();
                        $("#devicePolicyLookupContent .form-control#deviceTypeSelect").val(deviceTypeNameToIDMap[deviceTypeName]).change();
                        $("#devicePolicyLookupContent #variableTableBody").empty();
                        populateVariablesTableFromString($("#devicePolicyLookupTableBody #variables" + devicePolicyLookup.id).html());
                    });
                });

                devicePolicyLookupTable.on("click", "#deleteButton" + devicePolicyLookup.id, function () {
                    $.post("/delete-alert-type-lookup", {id: devicePolicyLookup.id}, function (isSuccess) {
                        if (isSuccess == "true") {
                            devicePolicyLookupTable.row("#tableRow" + devicePolicyLookup.id).remove().draw();
                        } else {
                            alert("Delete was unsuccessful.  Please check that another table entry " +
                                "does not rely on this Alert Type Lookup");
                        }
                    });
                });
            });
        });
    }

    $("#devicePolicyLookupContent #addVariableButton").click(function () {
        let keyInput = $(".form-control#variableKey");
        let valueInput = $(".form-control#variableValue");

        console.log(keyInput.val());

        addVariableRow(keyInput.val(), valueInput.val());

        keyInput.val("");
        valueInput.val("");
    });

    $("#devicePolicyLookupContent #clearFormButton").click(function () {
        let devicePolicySelect = $("#devicePolicyLookupContent .form-control#devicePolicy");
        let typeSelect = $("#devicePolicyLookupContent .form-control#deviceTypeSelect");

        $("#devicePolicyLookupContent #submitFormButton").html("Add");
        $("#devicePolicyLookupContent #clearFormButton").html("Clear");
        devicePolicySelect.val(devicePolicySelect.find("option:first").val());
        typeSelect.val(typeSelect.find("option:first").val());
        $("#devicePolicyLookupContent .form-control#variableKey").val("");
        $("#devicePolicyLookupContent .form-control#variableValue").val("");
        $("#devicePolicyLookupContent #variableTable").find("tr:gt(0)").remove();   //remove all rows except header
    });

    //only load content if the tab is active
    $('a[href="#DevicePolicyLookupContent"]').on('shown.bs.tab', function (e) {
        getAllDevicePolicyLookups();
    });
});