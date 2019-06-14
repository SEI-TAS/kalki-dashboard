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

    $.get("/device-types", (deviceTypes) => {
        $.each(JSON.parse(deviceTypes), (id, deviceType) => {
            $(".form-control#deviceTypeSelect").append("<option id='typeOption" + deviceType.id + "' value='" + deviceType.id + "'>"
                + deviceType.name +
                "</option>")
            deviceTypeIDtoNameMap[deviceType.id] = deviceType.name;
            deviceTypeNametoIDMap[deviceType.name] = deviceType.id;
        });

        $.get("/security-states", (securityStates) => {
            $.each(JSON.parse(securityStates), (id, state) => {
                $(".form-control#securityStateSelect").append("<option id='securityStateOption" + state.id + "' value='" + state.id + "'>"
                    + state.name +
                    "</option>")
                stateIDtoNameMap[state.id] = state.name;
                stateNametoIDMap[state.name] = state.id;
            });

            $.get("/device-commands", (commands) => {
                $.each(JSON.parse(commands), (id, command) => {
                    $(".form-control#deviceCommandSelect").append("<option id='deviceCommandOption" + command.id + "' value='" + command.id + "'>"
                        + command.name +
                        "</option>")
                    commandNametoIDMap[state.name] = state.id;
                });

                $.get("/command-lookups", (commandLookups) => {
                    $.each(JSON.parse(commandLookups), (index, commandLookup) => {

                        let newRow = "<tr id='tableRow" + commandLookup.id + "'>\n" +
                            "    <td class='fit'>" +
                            "        <div class='editDeleteContainer' >" +
                            "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + commandLookup.id + "'>Edit</button>" +
                            "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + commandLookup.id + "'>Delete</button>" +
                            "        </div>" +
                            "    </td>\n" +
                            "    <td id='deviceType" + commandLookup.id + "'>" + deviceTypeIDtoNameMap[commandLookup.deviceTypeId] + "</td>\n" +
                            "    <td id='securityState" + commandLookup.id + "'>" + stateIDtoNameMap[commandLookup.stateId] + "</td>\n" +
                            "    <td id='deviceCommand" + commandLookup.id + "'>" + commandLookup.name + "</td>\n" +
                            "</tr>"
                        commandLookupTable.row.add($(newRow)).draw();

                        /*
                        $("#commandLookupTableBody #editButton" + commandLookup.id).click(function () {
                            $.post("/edit-alert-condition", {id: commandLookup.id}, function () {
                                let alertTypeName = $("#commandLookupTableBody #alertType" + commandLookup.id).html();
                                let deviceName = $("#commandLookupTableBody #device" + commandLookup.id).html();

                                $('html, body').animate({scrollTop: 0}, 'fast', function () {});
                                $("#commandLookupContent #submitFormButton").html("Update");
                                $("#commandLookupContent #clearFormButton").html("Cancel Edit");
                                $("#commandLookupContent .form-control#alertType").val(alertTypeNametoIDMap[alertTypeName]).change();
                                $("#commandLookupContent .form-control#deviceSelect").val(deviceNametoIDMap[deviceName]).change();
                            });
                        });

                        $("#commandLookupTableBody #deleteButton" + commandLookup.id).click(function () {
                            $.post("/delete-alert-condition", {id: commandLookup.id}, function (isSuccess) {
                                if (isSuccess == "true") {
                                    commandLookupTable.row("#tableRow" + commandLookup.id).remove().draw();
                                } else {
                                    alert("delete was unsuccessful");
                                }
                            });
                        });
                         */
                    });
                });
            });
        });
    });

    $("#commandLookupContent #clearFormButton").click(function () {
        $.post("/clear-command-lookup-form", {}, function () {
            $("#commandLookupContent #submitFormButton").html("Add");
            $("#commandLookupContent #clearFormButton").html("Clear");
            $("#commandLookupContent .form-control#deviceTypeSelect").val("");
            $("#commandLookupContent .form-control#securityStateSelect").val("");
            $("#commandLookupContent .form-control#deviceCommandSelect").val("");
        });
    });
});