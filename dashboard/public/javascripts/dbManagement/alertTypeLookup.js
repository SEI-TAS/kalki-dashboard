/*
 * Kalki - A Software-Defined IoT Security Platform
 * Copyright 2020 Carnegie Mellon University.
 * NO WARRANTY. THIS CARNEGIE MELLON UNIVERSITY AND SOFTWARE ENGINEERING INSTITUTE MATERIAL IS FURNISHED ON AN "AS-IS" BASIS. CARNEGIE MELLON UNIVERSITY MAKES NO WARRANTIES OF ANY KIND, EITHER EXPRESSED OR IMPLIED, AS TO ANY MATTER INCLUDING, BUT NOT LIMITED TO, WARRANTY OF FITNESS FOR PURPOSE OR MERCHANTABILITY, EXCLUSIVITY, OR RESULTS OBTAINED FROM USE OF THE MATERIAL. CARNEGIE MELLON UNIVERSITY DOES NOT MAKE ANY WARRANTY OF ANY KIND WITH RESPECT TO FREEDOM FROM PATENT, TRADEMARK, OR COPYRIGHT INFRINGEMENT.
 * Released under a MIT (SEI)-style license, please see license.txt or contact permission@sei.cmu.edu for full terms.
 * [DISTRIBUTION STATEMENT A] This material has been approved for public release and unlimited distribution.  Please see Copyright notice for non-US Government use and distribution.
 * This Software includes and/or makes use of the following Third-Party Software subject to its own license:
 * 1. Google Guava (https://github.com/google/guava) Copyright 2007 The Guava Authors.
 * 2. JSON.simple (https://code.google.com/archive/p/json-simple/) Copyright 2006-2009 Yidong Fang, Chris Nokleberg.
 * 3. JUnit (https://junit.org/junit5/docs/5.0.1/api/overview-summary.html) Copyright 2020 The JUnit Team.
 * 4. Play Framework (https://www.playframework.com/) Copyright 2020 Lightbend Inc..
 * 5. PostgreSQL (https://opensource.org/licenses/postgresql) Copyright 1996-2020 The PostgreSQL Global Development Group.
 * 6. Jackson (https://github.com/FasterXML/jackson-core) Copyright 2013 FasterXML.
 * 7. JSON (https://www.json.org/license.html) Copyright 2002 JSON.org.
 * 8. Apache Commons (https://commons.apache.org/) Copyright 2004 The Apache Software Foundation.
 * 9. RuleBook (https://github.com/deliveredtechnologies/rulebook/blob/develop/LICENSE.txt) Copyright 2020 Delivered Technologies.
 * 10. SLF4J (http://www.slf4j.org/license.html) Copyright 2004-2017 QOS.ch.
 * 11. Eclipse Jetty (https://www.eclipse.org/jetty/licenses.html) Copyright 1995-2020 Mort Bay Consulting Pty Ltd and others..
 * 12. Mockito (https://github.com/mockito/mockito/wiki/License) Copyright 2007 Mockito contributors.
 * 13. SubEtha SMTP (https://github.com/voodoodyne/subethasmtp) Copyright 2006-2007 SubEthaMail.org.
 * 14. JSch - Java Secure Channel (http://www.jcraft.com/jsch/) Copyright 2002-2015 Atsuhiko Yamanaka, JCraft,Inc. .
 * 15. ouimeaux (https://github.com/iancmcc/ouimeaux) Copyright 2014 Ian McCracken.
 * 16. Flask (https://github.com/pallets/flask) Copyright 2010 Pallets.
 * 17. Flask-RESTful (https://github.com/flask-restful/flask-restful) Copyright 2013 Twilio, Inc..
 * 18. libvirt-python (https://github.com/libvirt/libvirt-python) Copyright 2016 RedHat, Fedora project.
 * 19. Requests: HTTP for Humans (https://github.com/psf/requests) Copyright 2019 Kenneth Reitz.
 * 20. netifaces (https://github.com/al45tair/netifaces) Copyright 2007-2018 Alastair Houghton.
 * 21. ipaddress (https://github.com/phihag/ipaddress) Copyright 2001-2014 Python Software Foundation.
 * DM20-0543
 *
 */

jQuery(document).ready(($) => {
    let alertTypeLookupTable = $('#alertTypeLookupTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    //query the database for all alert types
    let alertTypeIDtoNameMap = {};
    let alertTypeIDtoSourceMap = {};
    async function getAllAlertTypes() {
        $("#alertTypeSelect").empty();

        return $.get("/alert-types", (alertTypes) => {
            $.each(JSON.parse(alertTypes), (id, alertType) => {
                $("#alertTypeSelect").append("<option id='alertTypeOption" + alertType.id + "' value='" + alertType.id + "'>"
                    + alertType.name +
                    "</option>")
                alertTypeIDtoNameMap[alertType.id] = alertType.name;
                alertTypeIDtoSourceMap[alertType.id] = alertType.source;
            });
            $("#alertTypeSource").html(alertTypeIDtoSourceMap[$("#alertTypeSelect").find("option:first").val()]);
        });
    }

    let sensorNames = {};
    async function getDeviceSensors() {
        $("#alertConditionSensorSelect").empty();

        return $.get("/device-sensors-device-type?id="+$("#alertTypeLookupDeviceTypeIdHidden").val(), (sensors) => {
            $.each(JSON.parse(sensors), (id, sensor) => {
                sensorNames[sensor.id] = sensor.name;
                $("#alertConditionSensorSelect").append("<option id='sensorOption" + sensor.id + "' value='" + sensor.id + "'>"
                    + sensor.name +
                    "</option>")
            });
        });
    }

    async function getAllAlertTypeLookups() {
        //must wait for these functions to complete to ensure the mappings are present
        await getAllAlertTypes();

        $("#alertConditionsTableBody").html("");
        alertTypeLookupTable.off("click");
        alertTypeLookupTable.clear();

        $.get("/alert-type-lookups-by-device-type?id="+$("#alertTypeLookupDeviceTypeIdHidden").val(), (alertTypeLookups) => {
            $.each(JSON.parse(alertTypeLookups), (index, alertTypeLookup) => {
                let newRow = "<tr id='tableRow" + alertTypeLookup.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + alertTypeLookup.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + alertTypeLookup.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td id='alertType" + alertTypeLookup.id + "'>" + alertTypeLookup.id + "</td>\n" +
                    "    <td id='alertType" + alertTypeLookup.id + "'>" + alertTypeIDtoNameMap[alertTypeLookup.alertTypeId] + "</td>\n" +
                    "</tr>"
                alertTypeLookupTable.row.add($(newRow)).draw();

                alertTypeLookupTable.on("click", "#editButton" + alertTypeLookup.id, function () {
                    $('html, body').animate({scrollTop: 0}, 'fast', function () {});
                    $("#alertTypeLookupContent #atlSubmitFormButton").html("Update");
                    $("#alertTypeLookupContent #atlClearFormButton").html("Cancel Edit");
                    $("#alertTypeLookupIdHidden").val(alertTypeLookup.id);
                    $("#alertTypeLookupContent #alertTypeSelect").val(alertTypeLookup.alertTypeId);
                    $("#alertTypeSource").html(alertTypeIDtoSourceMap[alertTypeLookup.alertTypeId]);
                    getAlertConditionsForLookup(alertTypeLookup.id);
                    showOrHideConditions();
                });

                alertTypeLookupTable.on("click", "#deleteButton" + alertTypeLookup.id, function () {
                    if(confirm("Are you sure you want to delete the Alert Type Association for " + alertTypeIDtoNameMap[alertTypeLookup.alertTypeId] + "?") === true) {
                        $.post("/delete-alert-type-lookup", {id: alertTypeLookup.id}, function (isSuccess) {
                            if (isSuccess == "true") {
                                alertTypeLookupTable.row("#tableRow" + alertTypeLookup.id).remove().draw();
                            } else {
                                alert("Delete was unsuccessful.  Please check that another table entry " +
                                    "does not rely on this Alert Type Association.");
                            }
                        });
                    }
                });
            });
        });
    }

    function getAlertConditionsForLookup(alertTypeLookupId) {
        let alertConditionsTableBody = $("#alertConditionsTableBody");
        alertConditionsTableBody.html("");
        clearHiddenConditionMaps();

        $.get("/alert-context-by-lookup?id="+alertTypeLookupId, (alertContexts) => {
            // NOTE: The underlying assumption is that this list will only contain 1 item.
            let alertContextArray = JSON.parse(alertContexts);
            if(alertContextArray.length == 1) {
                let alertContext = alertContextArray[0];
                $("#alertContextIdHidden").val(alertContext.id);
                $("#alertContextLogicalOperator").val(alertContext.logicalOperator);
            }
            else if(alertContextArray.length > 1) {
                console.log("NOTE: More than one context found for alertTypeLookup.");
            }

            let alertContextId = $("#alertContextIdHidden").val();
            if(alertContextId == 0) {
                return;
            }
            $.get("/alert-conditions-by-context?id="+alertContextId, (alertConditions) => {
                $.each(JSON.parse(alertConditions), (index, alertCondition) => {
                    addConditionRow(alertCondition);
                });
            });
        });
    }

    // Add a condition row to the conditions table.
    let newConditionCounter = 0;
    function addConditionRow(alertCondition) {
        let alertConditionsTableBody = $("#alertConditionsTableBody");

        // For existing conditions displayed, the id is enough, but we need new ids to identify new rows we are adding.
        let suffix = alertCondition.id;
        if(alertCondition.id == 0) {
            newConditionCounter++;
            suffix = "-n-" + newConditionCounter;
        }

        let conditionRowId = "conditionTableRow" + suffix;
        let deleteButtonId = "conditionDeleteButton" + suffix;

        let newRow = "<tr id='" + conditionRowId + "'>\n" +
            "    <td><button type='button' class='btn btn-sm btn-secondary' id='" + deleteButtonId + "'>Delete</button></td>\n" +
            "    <td>" + alertCondition.attributeName + "</td>\n" +
            "    <td>" + alertCondition.numStatues + "</td>\n" +
            "    <td>" + alertCondition.calculation + "</td>\n" +
            "    <td>" + alertCondition.compOperator + "</td>\n" +
            "    <td>" + alertCondition.thresholdValue + "</td>\n" +
            "</tr>"
        alertConditionsTableBody.append($(newRow));

        // Add row info to hidden map (needed to pass form data to controller)
        addHiddenSelectOption("alertConditionIdsHidden", "alertConditionIdsHidden" + suffix, alertCondition.id);
        addHiddenSelectOption("alertConditionSensorsHidden", "alertConditionSensorsHidden" + suffix, alertCondition.attributeId);
        addHiddenSelectOption("alertConditionStatusesHidden", "alertConditionStatusesHidden" + suffix, alertCondition.numStatues);
        addHiddenSelectOption("alertConditionCalculationsHidden", "alertConditionCalculationsHidden" + suffix, alertCondition.calculation);
        addHiddenSelectOption("alertConditionComparisonsHidden", "alertConditionComparisonsHidden" + suffix, alertCondition.compOperator);
        addHiddenSelectOption("alertConditionThresholdsHidden", "alertConditionThresholdsHidden" + suffix, alertCondition.thresholdValue);

        $("#" + deleteButtonId).click(function () {
            // Remove visible row.
            alertConditionsTableBody.find("#" + conditionRowId).remove();

            // Remove from hidden maps.
            $("#alertConditionIdsHidden" + suffix).remove();
            $("#alertConditionSensorsHidden" + suffix).remove();
            $("#alertConditionStatusesHidden" + suffix).remove();
            $("#alertConditionCalculationsHidden" + suffix).remove();
            $("#alertConditionComparisonsHidden" + suffix).remove();
            $("#alertConditionThresholdsHidden" + suffix).remove();
        });
    }

    // Adds a row to a hidden map to be able to pass multiple conditions back to controller.
    function addHiddenSelectOption(selectId, optionId, alertConditionValue) {
        $("#" + selectId).append("<option id='" + optionId + "' value='" + alertConditionValue + "' selected></option>");
    }

    // Clears the input fields to add a new condition.
    function clearNewConditionFields() {
        $("#alertConditionSensorSelect").val($("#alertConditionSensorSelect").find("option:first").val());
        $("#alertConditionNumStatusFormInput").val("1");
        $("#alertConditionCalculationSelect").val($("#alertConditionCalculationSelect").find("option:first").val());
        $("#alertConditionComparisonSelect").val($("#alertConditionComparisonSelect").find("option:first").val());
        $("#alertConditionThresholdFormInput").val("");
    }

    function clearHiddenConditionMaps() {
        // Remove from hidden maps.
        $("#alertConditionIdsHidden").empty();
        $("#alertConditionSensorsHidden").empty();
        $("#alertConditionStatusesHidden").empty();
        $("#alertConditionCalculationsHidden").empty();
        $("#alertConditionComparisonsHidden").empty();
        $("#alertConditionThresholdsHidden").empty();
    }

    // Add the condition to the table when the add button is clicked.
    $("#alertConditionAddButton").click(function () {
        let newAlertCondition = {};
        newAlertCondition.id = 0;
        newAlertCondition.attributeId = $("#alertConditionSensorSelect").val();
        newAlertCondition.attributeName = sensorNames[newAlertCondition.attributeId];
        newAlertCondition.numStatues = $("#alertConditionNumStatusFormInput").val();
        newAlertCondition.calculation = $("#alertConditionCalculationSelect option:selected").text();
        newAlertCondition.compOperator = $("#alertConditionComparisonSelect option:selected").text();
        newAlertCondition.thresholdValue = $("#alertConditionThresholdFormInput").val();
        addConditionRow(newAlertCondition);
        clearNewConditionFields();
    });

    $("#atlClearFormButton").click(function () {
        let alertTypeSelect = $("#alertTypeSelect");

        $("#atlSubmitFormButton").html("Add");
        $("#atlClearFormButton").html("Clear");
        $("#alertTypeLookupIdHidden").val(0);
        alertTypeSelect.val(alertTypeSelect.find("option:first").val());
        $("#alertTypeSource").html(alertTypeIDtoSourceMap[alertTypeSelect.val()]);
        $("#alertContextIdHidden").val(0);
        $("#alertContextLogicalOperator").val($("#alertContextLogicalOperator").find("option:first").val());
        $("#alertConditionsTableBody").html("");
        showOrHideConditions();
    });

    // Reload when selected device type changes.
    $("#alertTypeLookupDeviceTypeIdHidden").change(function() {
        getAllAlertTypeLookups();
        getDeviceSensors();
    });

    // Show or hide condition sections depending on type source.
    function showOrHideConditions() {
        let alertConditionContent = $("#alertConditionContent");
        let source = alertTypeIDtoSourceMap[$("#alertTypeSelect").val()];
        if(source == "Device") {
            alertConditionContent.show();
        } else {
            alertConditionContent.hide();
        }
    }

    // When the alert type changes, update source and condition visibility.
    $("#alertTypeSelect").change(function () {
        let source = alertTypeIDtoSourceMap[$("#alertTypeSelect").val()];
        $("#alertTypeSource").html(source);
        $("#alertConditionsTableBody").html("");
        showOrHideConditions();
    });
});
