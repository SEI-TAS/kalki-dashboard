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
            let currentCount = ++commandRowCounter;
            let newRow = "<tr id='policyRuleCommandsOrderTableRow" + currentCount + "'>\n" +
                "    <td class='fit'><button type='button' class='btn btn-primary btn-sm' id='removeButton" + currentCount + "'>Remove</button></td>" +
                "    <td id='command" + currentCount + "'>" + commandIdToNameMap[commandId] + "</td>\n" +
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

                $("#policyRuleContent #policyRuleStateTransitionSelect").append("<option id='typeOption" + transition.id + "' value='" + transition.id + "'>" + stateIdToNameMap[transition.startStateId] + " -> " + stateIdToNameMap[transition.finishStateId] + "</option>");
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

        $.get("/alert-types", (alertTypes) => {
            $.each(JSON.parse(alertTypes), (id, alertType) => {
                alertTypeIdToNameMap[alertType.id] = alertType.name;
                alertTypeNameToIdMap[alertType.name] = alertType.id;
            });
        });
    }

    async function getAlertTypeLookups() {
        $("#policyRuleContent #policyRuleAlertTypeSelect").empty();

        await getAlertTypes();

        $.get("/alert-type-lookups", (alertTypeLookups) => {
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

        $.get("/commands", (commands) => {
            $.each(JSON.parse(commands), (id, command) => {
                commandIdToNameMap[command.id] = command.name;
                commandIdToDeviceTypeIdMap[command.id] = command.deviceTypeId;
                commandNameToIdMap[command.name] = command.id;

                if (currentDeviceTypeId === command.deviceTypeId) {
                    $("#policyRuleContent #policyRuleCommands").append("<option id='commandOption" + command.id + "' value='" + command.id + "'>" + command.name + "</option>");
                }
            });
        });
    }

    async function getCommandLookups() {
        $.get("/command-lookups", (commands) => {
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

        // Populate the table at the bottom of the page
        $.get("/policy-rules", (policyRules) => {
            $.each(JSON.parse(policyRules), (index, policyRule) => {
                // Create a custom strong for the alertTypeIds, which shows text instead of actual ids
                let alertTypeArray = [];
                policyConditionIdToAlertTypeIdsMap[policyRule.policyConditionId].forEach(element => alertTypeArray.push(alertTypeIdToNameMap[element]));

                // Get the associated device commands, and turn them into names
                let deviceCommandArray = [];
                $.each(commandLookupIdToPolicyRuleId, function (index, policyRuleId) {
                    if (policyRuleId == policyRule.id) {
                        deviceCommandArray.push(commandIdToNameMap[commandLookupIdToCommandId[index]])
                    }
                });

                // Generate the table row and add it to the table
                let newRow = "<tr id='tableRow" + policyRule.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + policyRule.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + policyRule.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td id='deviceType" + policyRule.id + "'>" + deviceTypeIdToNameMap[policyRule.deviceTypeId] + "</td>\n" +
                    "    <td id='policyCondition" + policyRule.id + "'>" + alertTypeArray.join(", ") + "</td>\n" +
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
            deviceType[0].selectedIndex = 0;
            // For above, could also use deviceType.val(deviceType.find("option:first").val());
            currentDeviceTypeId = parseInt(deviceType.val());
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
        getCommands();

        clearAlertTypes()
        clearCommands()
    });

    /**
     * Only load data when tab is active
     */
    $('a[href="#PolicyRuleContent"]').on('shown.bs.tab', function (e) {
        getDevicePolicies();
    });
}); 