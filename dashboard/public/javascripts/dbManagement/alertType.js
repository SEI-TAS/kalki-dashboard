jQuery(document).ready(($) => {
    $.get("/alert-types", (alertTypes) => {
        $.each(JSON.parse(alertTypes), (index, alertType) => {
            $("#alertTypeTableBody").append("<tr id='tableRow" + alertType.id + "'>\n" +
                "    <td>" +
                "        <div class='btn-group' role='group'>" +
                "           <button type='button' class='btn btn-secondary btn-sm' id='editButton" + alertType.id + "'>Edit</button>" +
                "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + alertType.id + "'>Delete</button>" +
                "        </div>" +
                "    </td>\n" +
                "    <td id='alertTypeID" + alertType.id + "'>" + alertType.id + "</td>\n" +
                "    <td id='name" + alertType.id + "'>" + alertType.name + "</td>\n" +
                "    <td id='description" + alertType.id + "'>" + alertType.description + "</td>\n" +
                "    <td id='source" + alertType.id + "'>" + alertType.source + "</td>\n" +
                "</tr>");

            $("#editButton" + alertType.id).click(function () {
                $.post("/edit-alert-type", {id: alertType.id}, function () {
                    $('html, body').animate({ scrollTop: 0 }, 'fast', function () {});
                    $("#clearFormButton").html("Cancel Edit");
                    $("#alertTypeContent .form-group #name").val($("#alertTypeTableBody #name" +alertType.id).html());
                    $("#alertTypeContent .form-group #description").val($("#alertTypeTableBody #description" +alertType.id).html());
                    $("#alertTypeContent .form-control#source").val($("#alertTypeTableBody #source" +alertType.id).html()).change();
                });
            });

            $("#deleteButton" + alertType.id).click(function () {
                $.post("/delete-alert-type", {id: alertType.id}, function () {
                    $("#tableRow" + alertType.id).remove();
                });
            });
        });
    });

    $("#clearFormButton").click(function () {
        $.post("/clear-alert-form", {}, function () {
            $("#clearFormButton").html("Clear");
            $("#alertTypeContent .form-group #name").val("");
            $("#alertTypeContent .form-group #description").val("");
            $("#alertTypeContent .form-control#source").val("IoT Monitor");
        });
    });
});