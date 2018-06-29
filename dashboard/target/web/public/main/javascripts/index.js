jQuery(document).ready(($) => {
    $.get("/devices", (devices) => {
        $.each(devices, (index, device) => {
            $("#dashboardTableBody").append("<tr class=\"clickable-row\" data-href=\"/info?id=" + device.id + "\">\n" +
                "    <td>" + device.name + "</td>\n" +
                "    <td>State</td>\n" +
                "    <td>Variable</td>\n" +
                "    <td>Alert</td>\n" +
                "</tr>");
        });

        $(".clickable-row").click(function() {
            window.location = $(this).data("href");
        });
    });

    $("#dropAllTables").click(() => {
        $.post("/clean");
    });

    $("#logDevices").click(() => {
        $.get("/log-devices");
    });
});