jQuery(document).ready(($) => {
    $.get("/devices", (devices) => {
        $.each(JSON.parse(devices), (index, device) => {
            $("#dashboardTableBody").append("<tr id='tableRow" + device.id + "'>\n" +
                "    <td><a href='/info?id=" + device.id + "' class='btn'>" + device.name + "</a></td>\n" +
                "    <td>State</td>\n" +
                "    <td>Variable</td>\n" +
                "    <td>Alert</td>\n" +
                "    <td><button type='button' class='btn btn-secondary' id='editButton" + device.id + "'>Edit</button></td>\n" +
                "    <td><button type='button' class='btn btn-secondary' id='deleteButton" + device.id + "'>Delete</button></td>\n" +
                "</tr>");

            $("#editButton" + device.id).click(function() {
                $(location).prop("href","/edit-device?id=" + device.id);
            });

            $("#deleteButton" + device.id).click(function() {
                $.post("/delete-device", { id: device.id }, function() {
                    $("#tableRow" + device.id).remove();
                });
            });
        });
    });

    $("#setupDatabaseButton").click(function() {
        $.post("/setup-database");
    });

    $("#resetDatabaseButton").click(function() {
        $.post("/reset-database");
    });

    $("#listDatabasesButton").click(function() {
        $.post("/list-all-databases");
    });
});