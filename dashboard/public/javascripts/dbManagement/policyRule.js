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
    let policyRuleTable = $('#policyRuleTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    let alertTypeRowCounter = 0;
    let commandRowCounter = 0;
    let currentDeviceTypeId = 0;

    let editing = false;
    let editingDagOrder = null;
    let editingUmboxImageId = null;

    // Various maps for ids and names
    let currentAlertTypeIds = [];
    let currentCommandIdToOrderNumberMap = {};

    let deviceTypeNameToIdMap = {};
    let deviceTypeIdToNameMap = {};

    let stateIdToNameMap = {};
    let stateNameToIdMap = {};

    let stateTransitionStartToIdMap = {};
    let stateTransitionFinishToIdMap = {};
    let stateTransitionIdToStartMap = {};
    let stateTransitionIdToFinishMap = {};

    let alertTypeIdToNameMap = {};
    let alertTypeNameToIdMap = {};

    let commandIdToNameMap = {};
    let commandIdToDeviceTypeIdMap = {};
    let commandNameToIdMap = {};

    let commandLookupIdToPolicyRuleId = {};
    let commandLookupIdToCommandId = {};

    let policyConditionIdToAlertTypeIdsMap = {};
    let policyConditionIdToThresholdMap = {};

    let alertTypeLookupIdToAlertTypeIdMap = {};
    let alertTypeIdToAlertTypeLookupIdMap = {};
    let alertTypeLookupIdToDeviceTypeIdMap = {};

    // Everything below this should be renamed
    let globalDeviceTypeAndStateOrderMap = {};
    let globalDeviceTypeAndStateImageMap = {};

    let umboxImageIDtoNameMap = {};
    let umboxImageNameToIdMap = {};

    let currentUmboxImageIdDagOrderMap = {};

    function addAlertTypeRow(alertTypeLookupId) {
        let alertTypeId = alertTypeLookupIdToAlertTypeIdMap[alertTypeLookupId];

        if(alertTypeId == null) {
            alert("please select a valid alert type");
            return false;
        }

        // Check for duplicate alert types trying to be added
        let hasDuplicate = false;
        Object.keys(currentAlertTypeIds).forEach((uid) => {
            let alertId = parseInt(currentAlertTypeIds[uid]);

            if (alertTypeId == alertId) {
                hasDuplicate = true;
            }
        });


        if (hasDuplicate) {
            alert("cannot add duplicate alert");
            return false;
        } else {
            // No duplicates, so add this row to the form
            $("#defaultAlertRow").remove();
            let currentCount = ++alertTypeRowCounter;
            let newRow = "<tr id='alertTypeOrderTableRow" + currentCount + "'>\n" +
                "    <td class='fit'><button type='button' class='btn btn-primary btn-sm' id='removeButton" + currentCount + "'>Remove</button></td>" +
                "    <td id='alertType" + currentCount + "'>" + alertTypeIdToNameMap[alertTypeId] + "</td>\n" +
                "</tr>"
            $("#alertTypeOrderTable").find("tbody").append($(newRow));

            // Add it to internal counter
            currentAlertTypeIds.push(alertTypeId);

            // Set hidden form input to current map (needed to pass form data to controller)
            $("#alertTypeOrderFormInput").append("<option id='alertTypeOrderFormInput" + alertTypeId + "' value='" + alertTypeId + "' selected></option>");

            // Set remove function for this alert type
            $("#alertTypeOrderTableBody #removeButton" + currentCount).click(function () {
                $("#alertTypeOrderTableBody #alertTypeOrderTableRow" + currentCount).remove();

                $("#alertTypeOrderFormInput" + alertTypeId).remove();

                let indexToRemove = currentAlertTypeIds.indexOf(alertTypeId);
                currentAlertTypeIds.splice(indexToRemove, 1);
            });
        }
        return true;
    }

    function addCommandsRow(commandId) {
        if(commandId == null) {
            alert("please select a valid alert type");
            return false;
        }

        // Check for duplicate orders or alert types trying to be added
        let hasDuplicate = false;
        Object.keys(currentCommandIdToOrderNumberMap).forEach((uid) => {
            let orderNumber = parseInt(currentCommandIdToOrderNumberMap[uid]);
            let imageId = parseInt(uid);

            if (commandId == imageId) {
                hasDuplicate = true;
            }
        });

        if (hasDuplicate) {
            alert("cannot add duplicate command");
            return false;
        } else {
            // No duplicates, so add this row to the form
            $("#defaultCommandRow").remove();
            let currentCount = ++commandRowCounter;
            let newRow = "<tr id='policyRuleCommandsOrderTableRow" + currentCount + "'>\n" +
                "    <td class='fit'><button type='button' class='btn btn-primary btn-sm' id='removeButton" + currentCount + "'>Remove</button></td>" +
                "    <td id='command" + currentCount + "'>" + deviceTypeIdToNameMap[commandIdToDeviceTypeIdMap[commandId]] + " - " + commandIdToNameMap[commandId] + "</td>\n" +
                "</tr>"

            $("#policyRuleCommandsOrderTable").find("tbody").append($(newRow));

            // Add command and order number relationship to map
            currentCommandIdToOrderNumberMap[commandId] = order;

            // Set hidden form input to current map (needed to pass form data to controller)
            $("#deviceCommandFormInput").append("<option id='deviceCommandFormInput" + commandId + "' value='" + commandId + "' selected></option>");

            // Set hidden form input to current map (needed to pass form data to controller)
            $("#policyRuleCommandsOrderFormInput").val(JSON.stringify(currentCommandIdToOrderNumberMap));

            // Set remove function for this command
            $("#policyRuleCommandsOrderTableBody #removeButton" + currentCount).click(function () {
                $("#policyRuleCommandsOrderTableBody #policyRuleCommandsOrderTableRow" + currentCount).remove();

                $("#deviceCommandFormInput" + currentCount).remove();

                delete currentCommandIdToOrderNumberMap[commandId.toString()];
            });
        }
        return true;
    }

    function switchToEditForm() {
        $("#policyRuleContent #policyRuleSubmitFormButton").html("Update");
        $("#policyRuleContent #policyRuleClearFormButton").html("Cancel");
    }

    function switchToInsertForm() {
        $("#policyRuleContent #policyRuleSubmitFormButton").html("Add");
        $("#policyRuleContent #policyRuleClearFormButton").html("Clear");
    }

    async function getDeviceTypes() {
        $("#policyRuleContent #policyRuleDeviceTypeSelect").empty();

        return $.get("/device-types", (types) => {
            $.each(JSON.parse(types), (id, type) => {
                deviceTypeIdToNameMap[type.id] = type.name;
                deviceTypeNameToIdMap[type.name] = type.id;

                // Set the first one on the list as the current selection
                if (currentDeviceTypeId === 0) {
                    currentDeviceTypeId = type.id;
                }

                $("#policyRuleContent #policyRuleDeviceTypeSelect").append("<option id='typeOption" + type.id + "' value='" + type.id + "'>" + type.name + "</option>");
            });
            let type = $("#type").val(); 
            $("#policyRuleContent #policyRuleDeviceTypeSelect").val(type);
        });
    }

    async function getSecurityStates() {
        return $.get("/security-states", (securityStates) => {
            $.each(JSON.parse(securityStates), (id, securityState) => {
                stateIdToNameMap[securityState.id] = securityState.name;
                stateNameToIdMap[securityState.name] = securityState.id;
            });
        });
    }


    async function getStateTransitions() {
        // Need security state information for the transitions
        await getSecurityStates();

        $("#policyRuleContent #policyRuleStateTransitionSelect").empty();

        return $.get("/state-transitions", (transitions) => {
            $.each(JSON.parse(transitions), (id, transition) => {
                // Create the mapping for all of the state transitions
                stateTransitionIdToStartMap[transition.id] = transition.startStateId;
                stateTransitionIdToFinishMap[transition.id] = transition.finishStateId;
                stateTransitionStartToIdMap[transition.startStateId] = transition.id;
                stateTransitionFinishToIdMap[transition.finishStateId] = transition.id;

                var startState;
                if(transition.startStateId) {
                	startState = stateIdToNameMap[transition.startStateId];
                }
                else {
                	startState = "All";
                }

                $("#policyRuleContent #policyRuleStateTransitionSelect").append("<option id='typeOption" + transition.id + "' value='" + transition.id + "'>" + startState + " -> " + stateIdToNameMap[transition.finishStateId] + "</option>");
            });
        });
    }


    async function getPolicyConditions() {
        return $.get("/policy-conditions", (policyConditions) => {
            $.each(JSON.parse(policyConditions), (id, policyCondition) => {
                policyConditionIdToAlertTypeIdsMap[policyCondition.id] = policyCondition.alertTypeIds;
                policyConditionIdToThresholdMap[policyCondition.id] = policyCondition.threshold;
            });
        });
    }

    async function getAlertTypes() {
        $("#policyRuleContent #policyRuleAlertTypeSelect").empty();

        return $.get("/alert-types", (alertTypes) => {
            $.each(JSON.parse(alertTypes), (id, alertType) => {
                alertTypeIdToNameMap[alertType.id] = alertType.name;
                alertTypeNameToIdMap[alertType.name] = alertType.id;
            });
        });
    }

    async function getAlertTypeLookups() {
        $("#policyRuleContent #policyRuleAlertTypeSelect").empty();

        await getAlertTypes();

        return $.get("/alert-type-lookups", (alertTypeLookups) => {
            $.each(JSON.parse(alertTypeLookups), (id, alertTypeLookup) => {
                alertTypeIdToAlertTypeLookupIdMap[alertTypeLookup.alertTypeId] = alertTypeLookup.id;
                alertTypeLookupIdToAlertTypeIdMap[alertTypeLookup.id] = alertTypeLookup.alertTypeId;
                alertTypeLookupIdToDeviceTypeIdMap[alertTypeLookup.id] = alertTypeLookup.deviceTypeId;

                if (currentDeviceTypeId === alertTypeLookup.deviceTypeId) {
                    $("#policyRuleContent #policyRuleAlertTypeSelect").append("<option id='alertTypeLookupOption" + alertTypeLookup.id + "' value='" + alertTypeLookup.id + "'>" + alertTypeIdToNameMap[alertTypeLookup.alertTypeId] + "</option>")
                }
            });
        });
    }

    async function getCommands() {
        $("#policyRuleContent #policyRuleCommands").empty();

        return $.get("/commands", (commands) => {
            $.each(JSON.parse(commands), (id, command) => {
                commandIdToNameMap[command.id] = command.name;
                commandIdToDeviceTypeIdMap[command.id] = command.deviceTypeId;
                commandNameToIdMap[command.name] = command.id;

                $("#policyRuleContent #policyRuleCommands").append("<option id='commandOption" + command.id + "' value='" + command.id + "'>" + deviceTypeIdToNameMap[command.deviceTypeId] + " - " + command.name + "</option>");

            });
        });
    }

    async function getCommandLookups() {
        return $.get("/command-lookups", (commands) => {
            $.each(JSON.parse(commands), (id, commandLookup) => {
                commandLookupIdToPolicyRuleId[commandLookup.id] = commandLookup.policyRuleId
                commandLookupIdToCommandId[commandLookup.id] = commandLookup.commandId
            });
        });
    }

    /**
     * The main starting point for the page, which gets all the related data from the database, then generates the
     * Policy Rule table at the bottom of the page.
     * @returns {Promise<void>}
     */
    async function getDevicePolicies() {
        // Need to get the various components to fill in the fields of the form
        await getDeviceTypes();
        await getAlertTypeLookups();
        await getStateTransitions();
        await getPolicyConditions();
        await getCommands();
        await getCommandLookups();

        policyRuleTable.clear();
        policyRuleTable.draw()

        // Populate the table at the bottom of the page
        $.get("/policy-rules-by-id?id="+$("#type").val(), (policyRules) => {
            $.each(JSON.parse(policyRules), (index, policyRule) => {
                // Create a custom strong for the alertTypeIds, which shows text instead of actual ids
                let alertTypeArray = [];
                policyConditionIdToAlertTypeIdsMap[policyRule.policyConditionId].forEach(element => alertTypeArray.push(alertTypeIdToNameMap[element]));

                // Get the associated device commands, and turn them into names
                let deviceCommandArray = [];
                $.each(commandLookupIdToPolicyRuleId, function (index, policyRuleId) {
                    if (policyRuleId == policyRule.id) {
                        deviceCommandArray.push(
                            deviceTypeIdToNameMap[commandIdToDeviceTypeIdMap[commandLookupIdToCommandId[index]]]
                            + " - " +
                            commandIdToNameMap[commandLookupIdToCommandId[index]]
                        )
                    }
                });

                var deviceTypeName;
                if(policyRule.deviceTypeId) { 
                    deviceTypeName =  deviceTypeIdToNameMap[policyRule.deviceTypeId];
                }
                else {
                    deviceTypeName = "All";
                }

                // Generate the table row and add it to the table
                let newRow = "<tr id='tableRow" + policyRule.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + policyRule.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + policyRule.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td id='deviceType" + policyRule.id + "'>" + deviceTypeName + "</td>\n" +
                    "    <td id='policyCondition" + policyRule.id + "'>" + alertTypeArray.join(" && ") + "</td>\n" +
                    "    <td id='deviceCommand" + policyRule.id + "'>" + deviceCommandArray.join(", ") + "</td>\n" +
                    "    <td id='startSecurityState" + policyRule.id + "'>" + stateIdToNameMap[stateTransitionIdToStartMap[policyRule.stateTransitionId]] + "</td>\n" +
                    "    <td id='finishSecurityState" + policyRule.id + "'>" + stateIdToNameMap[stateTransitionIdToFinishMap[policyRule.stateTransitionId]] + "</td>\n" +
                    "    <td id='samplingRateFactor" + policyRule.id + "'>" + policyRule.samplingRateFactor + "</td>\n" +
                    "    <td id='threshold" + policyRule.id + "'>" + policyConditionIdToThresholdMap[policyRule.policyConditionId] + "</td>\n" +
                    "</tr>"
                policyRuleTable.row.add($(newRow)).draw();

                // Set edit button click function
                policyRuleTable.on("click", "#editButton" + policyRule.id, function () {
                    editing = true;

                    $.post("/edit-policy-rule", {policyRuleId: policyRule.id, policyConditionId: policyRule.policyConditionId}, function () {
                        let deviceType = $("#policyRuleContent .form-control#policyRuleDeviceTypeSelect");
                        let stateTransition = $("#policyRuleContent .form-control#policyRuleStateTransitionSelect");
                        let threshold = $("#policyRuleContent .form-control#policyRuleThresholdFormInput");
                        let samplingRateFactor = $("#policyRuleContent .form-control#policyRuleSamplingRateFactorFormInput");

                        $('html, body').animate({scrollTop: 0}, 'fast', function () {});

                        // Update to the right device type, and then update the alert types availiable
                        deviceType.val(policyRule.deviceTypeId);
                        currentDeviceTypeId = policyRule.deviceTypeId;
                        getAlertTypeLookups();

                        // Update the alert types
                        clearAlertTypes();
                        $.each(policyConditionIdToAlertTypeIdsMap[policyRule.policyConditionId], (index, alertTypeId) => {
                            addAlertTypeRow(alertTypeIdToAlertTypeLookupIdMap[alertTypeId]);
                        });

                        // Update other inputs
                        stateTransition.val(policyRule.stateTransitionId);
                        threshold.val(policyConditionIdToThresholdMap[policyRule.policyConditionId]);
                        samplingRateFactor.val(policyRule.samplingRateFactor);

                        clearCommands();
                        $.each(commandLookupIdToPolicyRuleId, function (index, policyRuleId) {
                            if (policyRuleId == policyRule.id) {
                                addCommandsRow(commandLookupIdToCommandId[index])
                            }
                        });


                        switchToEditForm();
                    });
                });

                // Set delete button click function
                policyRuleTable.on("click", "#deleteButton" + policyRule.id, function () {
                    $.post("/delete-policy-rule", {policyRuleId: policyRule.id, policyConditionId: policyRule.policyConditionId}, function (isSuccess) {
                        if (isSuccess == "true") {
                            policyRuleTable.row("#tableRow" + policyRule.id).remove().draw();
                        } else {
                            alert("Delete was unsuccessful.  Please check that another table entry " +
                                "does not rely on this Policy Rule");
                        }
                    });
                });
            });
        });
    }

    /**
     * Removes all items in the alert types table
     */
    function clearAlertTypes() {
        $("#policyRuleContent #alertTypeOrderTable").find("tr:gt(0)").remove();
        $("#policyRuleContent #alertTypeOrderFormInput").find("option").remove();
        currentAlertTypeIds = [];

    }

    /**
     * Removes all items in the commands table
     */
    function clearCommands() {
        $("#policyRuleContent #policyRuleCommandsOrderTable").find("tr:gt(0)").remove();
        $("#policyRuleContent #deviceCommandFormInput").find("option").remove();
    }

    /**
     * Button which clears the form on the page, so the user can start over.
     */
    $("#policyRuleContent #policyRuleClearFormButton").click(function () {
        editing = false;

        let deviceType = $("#policyRuleContent .form-control#policyRuleDeviceTypeSelect");
        let stateTransition = $("#policyRuleContent .form-control#policyRuleStateTransitionSelect");
        let threshold = $("#policyRuleContent .form-control#policyRuleThresholdFormInput");
        let samplingRateFactor = $("#policyRuleContent .form-control#policyRuleSamplingRateFactorFormInput");

        $.post("/clear-policy-rule-form", {}, function () {
            // Reset device types
            //deviceType[0].selectedIndex = 0;
            // For above, could also use deviceType.val(deviceType.find("option:first").val());
            //currentDeviceTypeId = parseInt(deviceType.val());
            getAlertTypeLookups();
            getCommands();

            // Reset alert types and commands
            clearAlertTypes();
            clearCommands()

            // Reset other fields
            stateTransition[0].selectedIndex = 0;
            threshold.val(1);
            samplingRateFactor.val(1);

            switchToInsertForm();
        });
    });


    /**
     * Add the alert to the table when the add button is clicked.
     */
    $("#policyRuleContent #policyRuleAddAlertButton").click(function () {
        let policyRuleAlertSelect = $(".form-control#policyRuleAlertTypeSelect");

        if (addAlertTypeRow(policyRuleAlertSelect.val())) { //if the add was successful
            policyRuleAlertSelect.val(policyRuleAlertSelect.find("option:first").val());
        }
    });

    /**
     * Add the command to the table when the add button is clicked.
     */
    $("#policyRuleContent #policyRuleAddCommandButton").click(function () {
        let policyRuleAlertSelect = $(".form-control#policyRuleCommands");
        let orderNumber = $(".form-control#order");

        if (addCommandsRow(policyRuleAlertSelect.val(), orderNumber.val())) { //if the add was successful
            policyRuleAlertSelect.val(policyRuleAlertSelect.find("option:first").val());
            orderNumber.val(1);
        }
    });

    /**
     * Change the table when the device type is changed.
     */
    $('#policyRuleDeviceTypeSelect').on('change', function() {
        currentDeviceTypeId = parseInt(this.value);
        // TODO alert types should be stored locally, so there isnt extra calls to the db.
        getAlertTypeLookups();

        clearAlertTypes()
    });

    /**
     * Only load data when tab is active
     */
    $('a[href="#PolicyRuleContent"]').on('shown.bs.tab', function (e) {
        getDevicePolicies();
    });

    $("#type").change(function() {
        getDevicePolicies();
    });

}); 