jQuery(document).ready(($) => {
    $.get("/device-types", (deviceTypes) => {
        $.each(JSON.parse(deviceTypes), (index, deviceType) => {
            $("#deviceTypeTableBody").append("<tr id='tableRow" + deviceType.id + "'>\n" +
                "    <td class='fit'>" +
                "        <div class='editDeleteContainer'>" +
                "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + deviceType.id + "'>Edit</button>" +
                "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + deviceType.id + "'>Delete</button>" +
                "        </div>" +
                "    </td>\n" +
                "    <td id='id" + deviceType.id + "'>" + deviceType.id + "</td>\n" +
                "    <td id='name" + deviceType.id + "'>" + deviceType.name + "</td>\n" +
                "</tr>");

            $("#deviceTypeTableBody #editButton" + deviceType.id).click(function () {
                $.post("/edit-device-type", {id: deviceType.id}, function () {
                    $('html, body').animate({ scrollTop: 0 }, 'fast', function () {});
                    $("#deviceTypeContent #submitFormButton").html("Update");
                    $("#deviceTypeContent #clearFormButton").html("Cancel Edit");
                    $("#deviceTypeContent .form-group #name").val($("#deviceTypeTableBody #name" +deviceType.id).html());
                });
            });

            $("#deviceTypeTableBody #deleteButton" + deviceType.id).click(function () {
                $.post("/delete-device-type", {id: deviceType.id}, function (isSuccess) {
                    if(isSuccess == "true") {
                        $("#deviceTypeTableBody #tableRow" + deviceType.id).remove();
                    }
                    else {
                        alert("delete was unsuccessful");
                    }
                });
            });
        });
    });

    $("#deviceTypeContent #clearFormButton").click(function () {
        $.post("/clear-device-type-form", {}, function () {
            $("#deviceTypeContent #submitFormButton").html("Add");
            $("#deviceTypeContent #clearFormButton").html("Clear");
            $("#deviceTypeContent .form-group #name").val("");
        });
    });
});