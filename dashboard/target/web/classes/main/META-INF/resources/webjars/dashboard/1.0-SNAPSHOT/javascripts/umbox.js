jQuery(document).ready(($) => {
    $.get("/umbox-images", (images) => {
        $.each(JSON.parse(images), (index, image) => {
            $("#umboxImageTableBody").append("<tr id='tableRow" + image.id + "'>\n" +
                "    <td>" + image.name + "</td>\n" +
                "    <td>" + image.path + "</td>\n" +
                "    <td><button type='button' class='btn btn-secondary' id='editButton" + image.id + "' data-toggle='modal' data-target='#editUmboxImageModal'>Edit</button></td>\n" +
                "    <td><button type='button' class='btn btn-secondary' id='deleteButton" + image.id + "'>Delete</button></td>\n" +
                "</tr>");

            $("#editButton" + image.id).click(function() {
                document.getElementById("editUmboxImageId").value = image.id;
                document.getElementById("editUmboxImageName").value = image.name;
                document.getElementById("editUmboxImagePath").value = image.path;
            });

            $("#deleteButton" + image.id).click(function() {
                $.post("/delete-umbox-image", { id: image.id }, function(data, success) {
                    $("#tableRow" + image.id).remove();
                });
            });
        });
    });
});