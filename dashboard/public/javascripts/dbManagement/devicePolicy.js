jQuery(document).ready(($) => {
    let devicePolicyTable = $('#devicePolicyTable').DataTable(
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
        $("#devicePolicyContent #devicePolicyDeviceTypeSelect").empty();

        return $.get("/device-types", (types) => {
            $.each(JSON.parse(types), (id, type) => {
                deviceTypeIdToNameMap[type.id] = type.name;
                deviceTypeNameToIdMap[type.name] = type.id;

                $("#devicePolicyContent #devicePolicyDeviceTypeSelect").append("<option id='typeOption" + type.id + "' value='" + type.id + "'>" + type.name + "</option>");
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

        $("#devicePolicyContent #devicePolicyStateTransitionSelect").empty();

        return $.get("/state-transitions", (transitions) => {
            $.each(JSON.parse(transitions), (id, transition) => {
                // Create the mapping for all of the state transitions
                stateTransitionIdToStartMap[transition.id] = transition.startStateId;
                stateTransitionIdToFinishMap[transition.id] = transition.finishStateId;
                stateTransitionStartToIdMap[transition.startStateId] = transition.id;
                stateTransitionFinishToIdMap[transition.finishStateId] = transition.id;

                $("#devicePolicyContent #devicePolicyStateTransitionSelect").append("<option id='typeOption" + transition.id + "' value='" + transition.id + "'>" + stateIdToNameMap[transition.startStateId] + " -> " + stateIdToNameMap[transition.finishStateId] + "</option>");
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
        $("#devicePolicyContent #devicePolicyAlertTypeSelect").empty();

        $.get("/alert-types", (alertTypes) => {
            $.each(JSON.parse(alertTypes), (id, alertType) => {
                alertTypeIdToNameMap[alertType.id] = alertType.name;
                alertTypeNameToIdMap[alertType.name] = alertType.id;

                $("#devicePolicyContent #devicePolicyAlertTypeSelect").append("<option id='alertTypeOption" + alertType.id + "' value='" + alertType.id + "'>" + alertType.name + "</option>")
            });
        });
    }

    async function getCommands() {
        $("#devicePolicyContent #devicePolicyCommands").empty();

        $.get("/commands", (commands) => {
            $.each(JSON.parse(commands), (id, command) => {
                commandIdToNameMap[command.id] = command.name;
                commandNameToIdMap[command.name] = command.id;

                $("#devicePolicyContent #devicePolicyCommands").append("<option id='commandOption" + command.id + "' value='" + command.id + "'>" + command.name + " " + deviceTypeIdToNameMap[command.deviceTypeId] + "</option>");
            });
        });
    }

    async function getDevicePolicies() {
        // Need to get the various components to fill in the fields of the form
        await getDeviceTypes();
        await getAlertTypes();
        await getStateTransitions();
        await getPolicyConditions();
        await getCommands();

        devicePolicyTable.clear();

        // Populate the table at the bottom of the page
        // TODO Update the following for proper names
        $.get("/policy-rules", (devicePolicys) => {
            $.each(JSON.parse(devicePolicys), (index, devicePolicy) => {
                let newRow = "<tr id='tableRow" + devicePolicy.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + devicePolicy.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + devicePolicy.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td id='deviceType" + devicePolicy.id + "'>" + deviceTypeIdToNameMap[devicePolicy.devTypeId] + "</td>\n" +
                    "    <td id='policyCondition" + devicePolicy.id + "'>" + devicePolicy.policyCondId + "</td>\n" +
                    "    <td id='startSecurityState" + devicePolicy.id + "'>" + stateIdToNameMap[stateTransitionIdToStartMap[devicePolicy.stateTransId]] + "</td>\n" +
                    "    <td id='finishSecurityState" + devicePolicy.id + "'>" + stateIdToNameMap[stateTransitionIdToFinishMap[devicePolicy.stateTransId]] + "</td>\n" +
                    "    <td id='samplingRate" + devicePolicy.id + "'>" + devicePolicy.samplingRateFactor + "</td>\n" +
                    "</tr>"
                devicePolicyTable.row.add($(newRow)).draw();

                // TODO update edit and delete button
                // devicePolicyTable.on("click", "#editButton" + devicePolicy.id, function () {
                //     editing = true;
                //
                //     $.post("/edit-umbox-lookup", {id: devicePolicy.id}, function () {
                //         let dagOrder = parseInt($("#devicePolicyTable #order" + devicePolicy.id).html());
                //         let umboxImageId = umboxImageNameToIdMap[$("#devicePolicyTable #umboxImage" + devicePolicy.id).html()];
                //
                //         $('html, body').animate({scrollTop: 0}, 'fast', function () {
                //         });
                //         $("#devicePolicyContent #submitFormButton").html("Update");
                //         $("#devicePolicyContent #clearFormButton").html("Cancel Edit");
                //         $("#devicePolicyContent .form-control#type").val(deviceTypeNameToIdMap[$("#devicePolicyTable #deviceType" + devicePolicy.id).html()]);
                //         $("#devicePolicyContent .form-control#securityState").val(stateNameToIdMap[$("#devicePolicyTable #securityState" + devicePolicy.id).html()]);
                //         $("#devicePolicyContent .form-control#umboxImage").val(umboxImageId);
                //         $("#devicePolicyContent .form-control#order").val(dagOrder);
                //         $("#alertConditionContent #umboxImageOrderTable").find("tr:gt(0)").remove();   //remove all rows except header
                //
                //         editingDagOrder = dagOrder;
                //         editingUmboxImageId = umboxImageId;
                //
                //         switchToEditForm();
                //     });
                // });
                //
                // devicePolicyTable.on("click", "#deleteButton" + devicePolicy.id, function () {
                //     $.post("/delete-umbox-lookup", {id: devicePolicy.id}, function (isSuccess) {
                //         if (isSuccess == "true") {
                //             devicePolicyTable.row("#tableRow" + devicePolicy.id).remove().draw();
                //         } else {
                //             alert("Delete was unsuccessful.  Please check that another table entry " +
                //                 "does not rely on this Umbox Lookup");
                //         }
                //     });
                // });
            });
        });
    }


    $("#devicePolicyContent #clearFormButton").click(function () {
        // TODO update to reset relevant fields
        editing = false;

        let typeSelect = $("#devicePolicyContent .form-control#type");
        let securityStateSelect = $("#devicePolicyContent .form-control#securityState");
        let umboxImageSelect = $("#devicePolicyContent .form-control#umboxImage");

        $.post("/clear-umbox-lookup-form", {}, function () {
            $("#devicePolicyContent #submitFormButton").html("Add");
            $("#devicePolicyContent #clearFormButton").html("Clear");
            typeSelect.val(typeSelect.find("option:first").val());
            securityStateSelect.val(securityStateSelect.find("option:first").val());
            umboxImageSelect.val(umboxImageSelect.find("option:first").val());
            $("#devicePolicyContent .form-control#order").val(1);
            $("#devicePolicyContent #umboxImageOrderTable").find("tr:gt(0)").remove();   //remove all rows except header

            currentUmboxImageIdDagOrderMap = {};
            editingDagOrder = null;
            editingUmboxImageId = null;

            switchToInsertForm();
        });
    });


    // Add the alert to the table when the add button is clicked.
    $("#devicePolicyContent #addAlertButton").click(function () {
        let devicePolicyAlertSelect = $(".form-control#devicePolicyAlertTypeSelect");

        if (addAlertTypeRow(devicePolicyAlertSelect.val())) { //if the add was successful
            devicePolicyAlertSelect.val(devicePolicyAlertSelect.find("option:first").val());
        }
    });

    // Add the command to the table when the add button is clicked.
    $("#devicePolicyContent #addCommandButton").click(function () {
        let devicePolicyAlertSelect = $(".form-control#devicePolicyCommands");
        let orderNumber = $(".form-control#order");

        if (addCommandsRow(devicePolicyAlertSelect.val(), orderNumber.val())) { //if the add was successful
            devicePolicyAlertSelect.val(devicePolicyAlertSelect.find("option:first").val());
            orderNumber.val(1);
        }
    });

    //before submitting, ensure that an image or a dag order is not being repeated for the
    //device type and state compared to what is already in the database
    $('#devicePolicyContent form').on("submit", function () {
        // TODO pre-submit validation
    });

    // Only load data when tab is active
    $('a[href="#DevicePolicyContent"]').on('shown.bs.tab', function (e) {
        getDevicePolicies();
    });
}); 