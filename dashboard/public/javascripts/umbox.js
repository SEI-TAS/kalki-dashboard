jQuery(document).ready(($) => {
    $.get("/umbox-images", (images) => {
        $.each(images, (index, image) => {
            $("#umboxImageTableBody").append("<tr>\n" +
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
                $.post("/delete-umbox-image", { id: image.id });
                $(location).prop("href","/umbox-setup");
            });
        });
    });
});