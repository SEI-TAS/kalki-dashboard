jQuery(document).ready(($) => {
    $.get("/alert-types", (alertTypes) => {
        $.each(JSON.parse(alertTypes), (index, alertType) => {
            $("#alertTypeTableBody").append("<tr id='tableRow" + alertType.id + "'>\n" +
                "    <td>" +
                "        <button type='button' class='btn btn-secondary' id='editButton" + alertType.id + "'>Edit</button>" +
                "        <button type='button' class='btn btn-secondary' id='deleteButton" + alertType.id + "'>Delete</button>" +
                "    </td>\n" +
                "    <td id='alertTypeID" + alertType.id + "'>" +alertType.id+ "</td>\n" +
                "    <td id='name" + alertType.id + "'>" +alertType.name+ "</td>\n" +
                "    <td id='description" + alertType.id + "'>" +alertType.description+ "</td>\n" +
                "    <td id='source" + alertType.id + "'>" +alertType.source+ "</td>\n" +
                "</tr>");

            $("#editButton" + alertType.id).click(function () {
                $(location).prop("href", "/edit-device?id=" + alertType.id);
            });

            $("#deleteButton" + alertType.id).click(function () {
                $.post("/delete-alert-type", {id: alertType.id}, function () {
                    $("#tableRow" + alertType.id).remove();
                });
            });
        });
    });
});