jQuery(document).ready(($) => {
    let commandLookupTable = $('#commandLookupTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    let stateIDtoNameMap = {};
    let stateNameToIDMap = {};
    let commandNameToIDMap = {};
    let commandIdToNameMap = {};

    getCommandLookups();

    async function getSecurityStates() {
        return $.get("/security-states", (securityStates) => {
            $.each(JSON.parse(securityStates), (id, state) => {
                $(".form-control#securityStateSelect").append("<option id='securityStateOption" + state.id + "' value='" + state.id + "'>"
                    + state.name +
                    "</option>")
                stateIDtoNameMap[state.id] = state.name;
                stateNameToIDMap[state.name] = state.id;
            });
        });
    }

    async function getDeviceCommands() {
        return $.get("/commands", (commands) => {
            $.each(JSON.parse(commands), (id, command) => {
                commandNameToIDMap[command.name] = command.id;
                commandIdToNameMap[command.id] = command.name;

                $(".form-control#deviceCommandSelect").append("<option id='deviceCommandOption" + command.id + "' value='" + command.id + "'>"
                    + command.name + "</option>")
            });

            //set hidden deviceCommandName to the value of the deviceCommandSelect
            $("input#deviceCommandName").val($(".form-control#deviceCommandSelect").text());
        });
    }

    async function getCommandLookups() {
        await getSecurityStates();
        await getDeviceCommands();

        $.get("/command-lookups", (commandLookups) => {
            $.each(JSON.parse(commandLookups), (index, commandLookup) => {

                let newRow = "<tr id='tableRow" + commandLookup.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + commandLookup.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + commandLookup.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td id='securityState" + commandLookup.id + "'>" + stateIDtoNameMap[commandLookup.stateId] + "</td>\n" +
                    "    <td id='deviceCommand" + commandLookup.id + "'>" + commandIdToNameMap[commandLookup.commandId] + "</td>\n" +
                    "</tr>"
                commandLookupTable.row.add($(newRow)).draw();

                commandLookupTable.on("click", "#editButton" + commandLookup.id, function () {
                    $.post("/edit-command-lookup", {id: commandLookup.id}, function () {
                        let securityStateName = $("#commandLookupTableBody #securityState" + commandLookup.id).html();
                        let deviceCommandName = $("#commandLookupTableBody #deviceCommand" + commandLookup.id).html();

                        $('html, body').animate({scrollTop: 0}, 'fast', function () {
                        });
                        $("#commandLookupContent #submitFormButton").html("Update");
                        $("#commandLookupContent #clearFormButton").html("Cancel Edit");
                        $("#commandLookupContent .form-control#securityStateSelect").val(stateNameToIDMap[securityStateName]).change();
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
        let securityStateSelect = $("#commandLookupContent .form-control#securityStateSelect");
        let deviceCommandSelect = $("#commandLookupContent .form-control#deviceCommandSelect");

        $.post("/clear-command-lookup-form", {}, function () {
            $("#commandLookupContent #submitFormButton").html("Add");
            $("#commandLookuContent #clearFormButton").html("Clear");
            securityStateSelect.val(securityStateSelect.find("option:first").val()).change();
            deviceCommandSelect.val(securityStateSelect.find("option:first").val()).change();
            $("#commandLookupContent #deviceCommandName").val($(".form-control#deviceCommandSelect").text());
        });
    });
});