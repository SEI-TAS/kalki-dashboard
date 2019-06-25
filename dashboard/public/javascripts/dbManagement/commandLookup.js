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
    let stateNametoIDMap = {};
    let deviceTypeIDtoNameMap = {};
    let deviceTypeNametoIDMap = {};
    let commandNametoIDMap = {};

    getCommandLookups();

    async function getDeviceTypes() {
        return $.get("/device-types", (deviceTypes) => {
            $.each(JSON.parse(deviceTypes), (id, deviceType) => {
                $(".form-control#deviceTypeSelect").append("<option id='typeOption" + deviceType.id + "' value='" + deviceType.id + "'>"
                    + deviceType.name +
                    "</option>")
                deviceTypeIDtoNameMap[deviceType.id] = deviceType.name;
                deviceTypeNametoIDMap[deviceType.name] = deviceType.id;
            });
        });
    }

    async function getSecurityStates() {
        return $.get("/security-states", (securityStates) => {
            $.each(JSON.parse(securityStates), (id, state) => {
                $(".form-control#securityStateSelect").append("<option id='securityStateOption" + state.id + "' value='" + state.id + "'>"
                    + state.name +
                    "</option>")
                stateIDtoNameMap[state.id] = state.name;
                stateNametoIDMap[state.name] = state.id;
            });
        });
    }

    async function getDeviceCommands() {
        return $.get("/commands", (commands) => {
            $.each(JSON.parse(commands), (id, command) => {
                $(".form-control#deviceCommandSelect").append("<option id='deviceCommandOption" + command.id + "' value='" + command.id + "'>"
                    + command.name +
                    "</option>")
                commandNametoIDMap[command.name] = command.id;
            });

            //set hidden deviceCommandName to the value of the deviceCommandSelect
            $("input#deviceCommandName").val($(".form-control#deviceCommandSelect").text());
        });
    }

    async function getCommandLookups() {
        await getDeviceTypes();
        await getSecurityStates();
        await getDeviceCommands();

        $.get("/command-lookups", (commands) => {
            $.each(JSON.parse(commands), (index, command) => {

                let newRow = "<tr id='tableRow" + command.lookupId + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + command.lookupId + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + command.lookupId + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td id='deviceType" + command.lookupId + "'>" + deviceTypeIDtoNameMap[command.deviceTypeId] + "</td>\n" +
                    "    <td id='securityState" + command.lookupId + "'>" + stateIDtoNameMap[command.stateId] + "</td>\n" +
                    "    <td id='deviceCommand" + command.lookupId + "'>" + command.name + "</td>\n" +
                    "</tr>"
                commandLookupTable.row.add($(newRow)).draw();

                commandLookupTable.on("click", "#editButton" + command.lookupId, function () {
                    $.post("/edit-command-lookup", {id: command.lookupId}, function () {
                        let deviceTypeName = $("#commandLookupTableBody #deviceType" + command.lookupId).html();
                        let securityStateName = $("#commandLookupTableBody #securityState" + command.lookupId).html();
                        let deviceCommandName = $("#commandLookupTableBody #deviceCommand" + command.lookupId).html();

                        $('html, body').animate({scrollTop: 0}, 'fast', function () {
                        });
                        $("#commandLookupContent #submitFormButton").html("Update");
                        $("#commandLookupContent #clearFormButton").html("Cancel Edit");
                        $("#commandLookupContent .form-control#deviceTypeSelect").val(deviceTypeNametoIDMap[deviceTypeName]).change();
                        $("#commandLookupContent .form-control#securityStateSelect").val(stateNametoIDMap[securityStateName]).change();
                        $("#commandLookupContent .form-control#deviceCommandSelect").val(commandNametoIDMap[deviceCommandName]).change();
                        $("#commandLookupContent #deviceCommandName").val(deviceCommandName);
                    });
                });

                commandLookupTable.on("click", "#deleteButton" + command.lookupId, function () {
                    $.post("/delete-command-lookup", {id: command.lookupId}, function (isSuccess) {
                        if (isSuccess == "true") {
                            commandLookupTable.row("#tableRow" + command.lookupId).remove().draw();
                        } else {
                            alert("delete was unsuccessful");
                        }
                    });
                });
            });
        });
    }



    //change hidden deviceCommandName in form when deviceCommandSelect is changed
    $('.form-control#deviceCommandSelect').on('change', function () {
        $("input#deviceCommandName").val(this.value);
    });

    $("#commandLookupContent #clearFormButton").click(function () {
        let deviceTypeSelect = $("#commandLookupContent .form-control#deviceTypeSelect");
        let securityStateSelect = $("#commandLookupContent .form-control#securityStateSelect");
        let deviceCommandSelect = $("#commandLookupContent .form-control#deviceCommandSelect");

        $.post("/clear-command-lookup-form", {}, function () {
            $("#commandLookupContent #submitFormButton").html("Add");
            $("#commandLookupContent #clearFormButton").html("Clear");
            deviceTypeSelect.val(deviceTypeSelect.find("option:first").val()).change();
            securityStateSelect.val(securityStateSelect.find("option:first").val()).change();
            deviceCommandSelect.val(securityStateSelect.find("option:first").val()).change();
            $("#commandLookupContent #deviceCommandName").val($(".form-control#deviceCommandSelect").text());
        });
    });
});