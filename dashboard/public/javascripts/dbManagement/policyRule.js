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

    let editing = false;
    let editingDagOrder = null;
    let editingUmboxImageId = null;

    // Various maps for ids and names
    let currentAlertTypeIdToOrderNumberMap = {};
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
    let commandNameToIdMap = {};

    // Everything below this should be renamed
    let globalDeviceTypeAndStateOrderMap = {};
    let globalDeviceTypeAndStateImageMap = {};

    let umboxImageIDtoNameMap = {};
    let umboxImageNameToIdMap = {};

    let currentUmboxImageIdDagOrderMap = {};

    function addAlertTypeRow(alertTypeId) {
        if(alertTypeId == null) {
            alert("please select a valid alert type");
            return false;
        }

        // Check for duplicate alert types trying to be added
        let hasDuplicate = false;
        Object.keys(currentAlertTypeIdToOrderNumberMap).forEach((uid) => {
            let imageId = parseInt(uid);

            if (alertTypeId == imageId) {
                alert("cannot add duplicate image");
                hasDuplicate = true;
            }
        });

        if (hasDuplicate) {
            return false;
        } else {
            // No duplicates, so add this row to the form
            let currentCount = ++alertTypeRowCounter;
            let newRow = "<tr id='alertTypeOrderTableRow" + currentCount + "'>\n" +
                "    <td class='fit'><button type='button' class='btn btn-primary btn-sm' id='removeButton" + currentCount + "'>Remove</button></td>" +
                "    <td id='alertType" + currentCount + "'>" + alertTypeIdToNameMap[alertTypeId] + "</td>\n" +
                "</tr>"
            $("#alertTypeOrderTable").find("tbody").append($(newRow));

            // Set hidden form input to current map (needed to pass form data to controller)
            $("#alertTypeOrderFormInput").val(JSON.stringify(currentAlertTypeIdToOrderNumberMap));

            // Set remove function for this alert type
            $("#alertTypeOrderTableBody #removeButton" + currentCount).click(function () {
                $("#alertTypeOrderTableBody #alertTypeOrderTableRow" + currentCount).remove();

                delete currentAlertTypeIdToOrderNumberMap[alertTypeId.toString()];
            });
        }
        return true;
    }

    function addCommandsRow(commandId, order) {
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
                alert("cannot add duplicate image");
                hasDuplicate = true;
            }
            if (orderNumber == order) {
                alert("cannot add duplicate order number");
                hasDuplicate = true;
            }
        });

        if (hasDuplicate) {
            return false;
        } else {
            // No duplicates, so add this row to the form
            let currentCount = ++commandRowCounter;
            let newRow = "<tr id='commandsOrderTableRow" + currentCount + "'>\n" +
                "    <td class='fit'><button type='button' class='btn btn-primary btn-sm' id='removeButton" + currentCount + "'>Remove</button></td>" +
                "    <td id='umboxImage" + currentCount + "'>" + commandIdToNameMap[commandId] + "</td>\n" +
                "    <td id='order" + currentCount + "'>" + order + "</td>\n" +
                "</tr>"

            $("#commandsOrderTable").find("tbody").append($(newRow));

            // Add command and order number relationship to map
            currentCommandIdToOrderNumberMap[commandId] = order;

            // Set hidden form input to current map (needed to pass form data to controller)
            $("#commandsOrderFormInput").val(JSON.stringify(currentCommandIdToOrderNumberMap));

            // Set remove function for this command
            $("#commandsOrderTableBody #removeButton" + currentCount).click(function () {
                $("#commandsOrderTableBody #commandsOrderTableRow" + currentCount).remove();

                delete currentCommandIdToOrderNumberMap[commandId.toString()];
            });
        }
        return true;
    }

    function switchToEditForm() {
        // TODO update to fill in relevant fields

        $("#umboxImageOrderTable").hide();
        $("#addOrderButton").hide();

        $(".form-control#command").attr("name", "commandId");
        $(".form-control#order").attr("name", "dagOrder");
    }

    function switchToInsertForm() {
        // TODO update to fill in relevant fields

        $("#umboxImageOrderTable").show();
        $("#addOrderButton").show();

        $(".form-control#umboxImage").removeAttr("name");
        $(".form-control#order").removeAttr("name");
    }

    async function getDeviceTypes() {
        $("#policyRuleContent #policyRuleDeviceTypeSelect").empty();

        return $.get("/device-types", (types) => {
            $.each(JSON.parse(types), (id, type) => {
                deviceTypeIdToNameMap[type.id] = type.name;
                deviceTypeNameToIdMap[type.name] = type.id;

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
                console.log(policyCondition)
            });
        });
    }

    async function getAlertTypes() {
        $("#policyRuleContent #policyRuleAlertTypeSelect").empty();

        $.get("/alert-types", (alertTypes) => {
            $.each(JSON.parse(alertTypes), (id, alertType) => {
                alertTypeIdToNameMap[alertType.id] = alertType.name;
                alertTypeNameToIdMap[alertType.name] = alertType.id;

                $("#policyRuleContent #policyRuleAlertTypeSelect").append("<option id='alertTypeOption" + alertType.id + "' value='" + alertType.id + "'>" + alertType.name + "</option>")
            });
        });
    }

    async function getCommands() {
        $("#policyRuleContent #policyRuleCommands").empty();

        $.get("/commands", (commands) => {
            $.each(JSON.parse(commands), (id, command) => {
                commandIdToNameMap[command.id] = command.name;
                commandNameToIdMap[command.name] = command.id;

                $("#policyRuleContent #policyRuleCommands").append("<option id='commandOption" + command.id + "' value='" + command.id + "'>" + command.name + " " + deviceTypeIdToNameMap[command.deviceTypeId] + "</option>");
            });
        });
    }

    async function getpolicyRules() {
        // Need to get the various components to fill in the fields of the form
        await getDeviceTypes();
        await getAlertTypes();
        await getStateTransitions();
        await getPolicyConditions();
        await getCommands();

        policyRuleTable.clear();

        // Populate the table at the bottom of the page
        // TODO Update the following for proper names
        $.get("/policy-rules", (policyRules) => {
            $.each(JSON.parse(policyRules), (index, policyRule) => {
                let newRow = "<tr id='tableRow" + policyRule.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + policyRule.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + policyRule.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td id='deviceType" + policyRule.id + "'>" + deviceTypeIdToNameMap[policyRule.devTypeId] + "</td>\n" +
                    "    <td id='policyCondition" + policyRule.id + "'>" + policyRule.policyCondId + "</td>\n" +
                    "    <td id='startSecurityState" + policyRule.id + "'>" + stateIdToNameMap[stateTransitionIdToStartMap[policyRule.stateTransId]] + "</td>\n" +
                    "    <td id='finishSecurityState" + policyRule.id + "'>" + stateIdToNameMap[stateTransitionIdToFinishMap[policyRule.stateTransId]] + "</td>\n" +
                    "    <td id='samplingRate" + policyRule.id + "'>" + policyRule.samplingRateFactor + "</td>\n" +
                    "</tr>"
                policyRuleTable.row.add($(newRow)).draw();

                // TODO update edit and delete button
                // policyRuleTable.on("click", "#editButton" + policyRule.id, function () {
                //     editing = true;
                //
                //     $.post("/edit-umbox-lookup", {id: policyRule.id}, function () {
                //         let dagOrder = parseInt($("#policyRuleTable #order" + policyRule.id).html());
                //         let umboxImageId = umboxImageNameToIdMap[$("#policyRuleTable #umboxImage" + policyRule.id).html()];
                //
                //         $('html, body').animate({scrollTop: 0}, 'fast', function () {
                //         });
                //         $("#policyRuleContent #submitFormButton").html("Update");
                //         $("#policyRuleContent #clearFormButton").html("Cancel Edit");
                //         $("#policyRuleContent .form-control#type").val(deviceTypeNameToIdMap[$("#policyRuleTable #deviceType" + policyRule.id).html()]);
                //         $("#policyRuleContent .form-control#securityState").val(stateNameToIdMap[$("#policyRuleTable #securityState" + policyRule.id).html()]);
                //         $("#policyRuleContent .form-control#umboxImage").val(umboxImageId);
                //         $("#policyRuleContent .form-control#order").val(dagOrder);
                //         $("#alertConditionContent #umboxImageOrderTable").find("tr:gt(0)").remove();   //remove all rows except header
                //
                //         editingDagOrder = dagOrder;
                //         editingUmboxImageId = umboxImageId;
                //
                //         switchToEditForm();
                //     });
                // });
                //
                // policyRuleTable.on("click", "#deleteButton" + policyRule.id, function () {
                //     $.post("/delete-umbox-lookup", {id: policyRule.id}, function (isSuccess) {
                //         if (isSuccess == "true") {
                //             policyRuleTable.row("#tableRow" + policyRule.id).remove().draw();
                //         } else {
                //             alert("Delete was unsuccessful.  Please check that another table entry " +
                //                 "does not rely on this Umbox Lookup");
                //         }
                //     });
                // });
            });
        });
    }


    $("#policyRuleContent #clearFormButton").click(function () {
        // TODO update to reset relevant fields
        editing = false;

        let typeSelect = $("#policyRuleContent .form-control#type");
        let securityStateSelect = $("#policyRuleContent .form-control#securityState");
        let umboxImageSelect = $("#policyRuleContent .form-control#umboxImage");

        $.post("/clear-umbox-lookup-form", {}, function () {
            $("#policyRuleContent #submitFormButton").html("Add");
            $("#policyRuleContent #clearFormButton").html("Clear");
            typeSelect.val(typeSelect.find("option:first").val());
            securityStateSelect.val(securityStateSelect.find("option:first").val());
            umboxImageSelect.val(umboxImageSelect.find("option:first").val());
            $("#policyRuleContent .form-control#order").val(1);
            $("#policyRuleContent #umboxImageOrderTable").find("tr:gt(0)").remove();   //remove all rows except header

            currentUmboxImageIdDagOrderMap = {};
            editingDagOrder = null;
            editingUmboxImageId = null;

            switchToInsertForm();
        });
    });


    // Add the alert to the table when the add button is clicked.
    $("#policyRuleContent #addAlertButton").click(function () {
        let policyRuleAlertSelect = $(".form-control#policyRuleAlertTypeSelect");

        if (addAlertTypeRow(policyRuleAlertSelect.val())) { //if the add was successful
            policyRuleAlertSelect.val(policyRuleAlertSelect.find("option:first").val());
        }
    });

    // Add the command to the table when the add button is clicked.
    $("#policyRuleContent #addCommandButton").click(function () {
        let policyRuleAlertSelect = $(".form-control#policyRuleCommands");
        let orderNumber = $(".form-control#order");

        if (addCommandsRow(policyRuleAlertSelect.val(), orderNumber.val())) { //if the add was successful
            policyRuleAlertSelect.val(policyRuleAlertSelect.find("option:first").val());
            orderNumber.val(1);
        }
    });

    //before submitting, ensure that an image or a dag order is not being repeated for the
    //device type and state compared to what is already in the database
    $('#policyRuleContent form').on("submit", function () {
        // TODO pre-submit validation
    });

    // Only load data when tab is active
    $('a[href="#PolicyRuleContent"]').on('shown.bs.tab', function (e) {
        getpolicyRules();
    });
});