jQuery(document).ready(($) => {
    $.get("/devices", (devices) => {
        $.each(devices, (index, device) => {
            $("#dashboardTableBody").append("<tr>\n" +
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
                $.post("/delete-device", { id: device.id });
                $(location).prop("href","/");
            });
        });
    });
});