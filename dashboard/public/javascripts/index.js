jQuery(document).ready(($) => {
    $.get("/devices", (devices) => {
        $.each(devices, (index, device) => {
            $("#dashboardTableBody").append("<tr class=\"clickable-row\" data-href=\"/info?id=" + device.id + "\">\n" +
                "    <td><a href='/info?id=" + device.id + "' class='btn'>" + device.name + "</a></td>\n" +
                "    <td>State</td>\n" +
                "    <td>Variable</td>\n" +
                "    <td>Alert</td>\n" +
                "    <td><button class='btn btn-secondary' id='editButton" + device.id + "'>Edit</button></td>\n" +
                "    <td><button class='btn btn-secondary' id='deleteButton" + device.id + "'>Delete</button></td>\n" +
                "</tr>");

            $("#editButton" + device.id).click(function() {
                $(location).prop("href","/add-device");
            });

            $("#deleteButton" + device.id).click(function() {
                $.post("/delete-device", { id: device.id });
            });
        });

        $(".edit-button").click(function() {
            $(location).prop("href","/add-device");
        });

    });

    $("#dropAllTables").click(() => {
        $.post("/clean");
    });

    $("#logDevices").click(() => {
        $.get("/log-devices");
    });
});