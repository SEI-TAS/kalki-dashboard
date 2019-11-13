jQuery(document).ready(($) => {
    let commandLookupTable = $('#commandLookupTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    //mappings from id to name to avoid uneccesary database calls
    let stateIDtoNameMap = {};
    let stateNameToIDMap = {};
    let commandNameToIDMap = {};
    let commandIdToNameMap = {};
    let deviceTypeIDtoNameMap = {};
    let deviceTypeNametoIDMap = {};

    async function getSecurityStates() {
        $(".form-control#securityStateSelect").empty();

        return $.get("/security-states", (securityStates) => {
            $.each(JSON.parse(securityStates), (id, state) => {
                $(".form-control#currentSecurityStateSelect").append("<option id='currentSecurityStateOption" + state.id + "' value='" + state.id + "'>"
                    + state.name +
                    "</option>");
                $(".form-control#previousSecurityStateSelect").append("<option id='previousSecurityStateOption" + state.id + "' value='" + state.id + "'>"
                    + state.name +
                    "</option>");
                stateIDtoNameMap[state.id] = state.name;
                stateNameToIDMap[state.name] = state.id;
            });
        });
    }

    async function getDeviceTypes() {
        $(".form-control#deviceTypeSelect").empty();

        return $.get("/device-types", (deviceTypes) => {
            $.each(JSON.parse(deviceTypes), (id, type) => {
                console.log("Type: ",type);
                $(".form-control#deviceTypeSelect").append("<option id='deviceTypeOption" + type.id + "' value='" + type.id + "'>"
                    + type.name +
                    "</option>");
                deviceTypeIDtoNameMap[type.id] = type.name;
                deviceTypeNametoIDMap[type.name] = type.id;
            });

        });
    }

    async function getDeviceCommands() {
        $(".form-control#deviceCommandSelect").empty();

        return $.get("/commands", (commands) => {
            $.each(JSON.parse(commands), (id, command) => {
                let name = deviceTypeIDtoNameMap[command.deviceTypeId]+": "+command.name;
                commandNameToIDMap[name] = command.id;
                commandIdToNameMap[command.id] = name;

                $(".form-control#deviceCommandSelect").append("<option id='deviceCommandOption" + command.id + "' value='" + command.id + "'>"
                    + name + "</option>");
            });

            //set hidden deviceCommandName to the value of the deviceCommandSelect
            $("input#deviceCommandName").val($(".form-control#deviceCommandSelect").text());
        });
    }

    async function getCommandLookups() {
        //must wait on these functions to complete to ensure the mappings are present
        await getDeviceTypes();
        await getSecurityStates();
        await getDeviceCommands();

        commandLookupTable.clear();

        $.get("/command-lookups", (commandLookups) => {
            $.each(JSON.parse(commandLookups), (index, commandLookup) => {

                let newRow = "<tr id='tableRow" + commandLookup.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + commandLookup.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + commandLookup.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td id='id" + commandLookup.id + "'>" + commandLookup.id + "</td>\n" +
                    "    <td id='currentSecurityState" + commandLookup.id + "'>" + stateIDtoNameMap[commandLookup.currentStateId] + "</td>\n" +
                    "    <td id='previousSecurityState" + commandLookup.id + "'>" + stateIDtoNameMap[commandLookup.previousStateId] + "</td>\n" +
                    "    <td id='deviceType" + commandLookup.id + "'>" + deviceTypeIDtoNameMap[commandLookup.deviceTypeId] + "</td>\n" +
                    "    <td id='deviceCommand" + commandLookup.id + "'>" + commandIdToNameMap[commandLookup.commandId] + "</td>\n" +
                    "</tr>"
                commandLookupTable.row.add($(newRow)).draw();

                commandLookupTable.on("click", "#editButton" + commandLookup.id, function () {
                    $.post("/edit-command-lookup", {id: commandLookup.id}, function () {
                        let currentSecurityState = $("#commandLookupTableBody #currentSecurityState" + commandLookup.id).html();
                        let previousSecurityState = $("#commandLookupTableBody #previousSecurityState" + commandLookup.id).html();
                        let deviceType = $("#commandLookupTableBody #deviceType"+commandLookup.id).html();
                        let deviceCommandName = $("#commandLookupTableBody #deviceCommand" + commandLookup.id).html();

                        $('html, body').animate({scrollTop: 0}, 'fast', function () {
                        });
                        $("#commandLookupContent #submitFormButton").html("Update");
                        $("#commandLookupContent #clearFormButton").html("Cancel Edit");
                        $("#commandLookupContent .form-control#currentSecurityStateSelect").val(stateNameToIDMap[currentSecurityState]).change();
                        $("#commandLookupContent .form-control#previousSecurityStateSelect").val(stateNameToIDMap[previousSecurityState]).change();
                        $("#commandLookupContent .form-control#deviceTypeSelect").val(deviceTypeNametoIDMap[deviceType]).change();
                        $("#commandLookupContent .form-control#deviceCommandSelect").val(commandNameToIDMap[deviceCommandName]).change();
                    });
                });

                commandLookupTable.on("click", "#deleteButton" + commandLookup.id, function () {
                    $.post("/delete-command-lookup", {id: commandLookup.id}, function (isSuccess) {
                        if (isSuccess == "true") {
                            commandLookupTable.row("#tableRow" + commandLookup.id).remove().draw();
                        } else {
                            alert("Delete was unsuccessful.  Please check that another table entry " +
                                "does not rely on this Command Lookup");
                        }
                    });
                });
            });
        });
    }

    $("#commandLookupContent #clearFormButton").click(function () {
        let currentSecurityStateSelect = $("#commandLookupContent .form-control#currentSecurityStateSelect");
        let previousSecurityStateSelect = $("#commandLookupContent .form-control#previousSecurityStateSelect");
        let deviceTypeSelect = $("#commandLookupContent .form-control#deviceTypeSelect");
        let deviceCommandSelect = $("#commandLookupContent .form-control#deviceCommandSelect");

        $.post("/clear-command-lookup-form", {}, function () {
            $("#commandLookupContent #submitFormButton").html("Add");
            $("#commandLookuContent #clearFormButton").html("Clear");
            currentSecurityStateSelect.val(currentSecurityStateSelect.find("option:first").val()).change();
            previousSecurityStateSelect.val(previousSecurityStateSelect.find("option:first").val()).change();
            deviceTypeSelect.val(deviceTypeSelect.find("option:first").val()).change();
            deviceCommandSelect.val(deviceCommandSelect.find("option:first").val()).change();
            $("#commandLookupContent #deviceCommandName").val($(".form-control#deviceCommandSelect").text());
        });
    });

    //only load data when tab is active
    $('a[href="#CommandLookupContent"]').on('shown.bs.tab', function (e) {
        getCommandLookups();
    });
});
