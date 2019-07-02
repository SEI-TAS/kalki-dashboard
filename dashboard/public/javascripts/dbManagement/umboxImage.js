jQuery(document).ready(($) => {
    let umboxImageTable = $('#umboxImageTable').DataTable({
        order: [[1, 'asc']],
        columnDefs: [
            {"orderable": false, "targets": 0}
        ]
    });

    function getUmboxImages() {
        umboxImageTable.clear();

        $.get("/umbox-images", (umboxImages) => {
            $.each(JSON.parse(umboxImages), (index, umboxImage) => {
                let newRow = "<tr id='tableRow" + umboxImage.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer'>" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + umboxImage.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + umboxImage.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td class='fit' id='id" + umboxImage.id + "'>" + umboxImage.id + "</td>\n" +
                    "    <td id='name" + umboxImage.id + "'>" + umboxImage.name + "</td>\n" +
                    "    <td id='fileName" + umboxImage.id + "'>" + umboxImage.fileName + "</td>\n" +
                    "</tr>";
                umboxImageTable.row.add($(newRow)).draw();

                umboxImageTable.on("click", "#editButton" + umboxImage.id, function () {
                    $.post("/edit-umbox-image", {id: umboxImage.id}, function () {
                        $('html, body').animate({scrollTop: 0}, 'fast', function () {
                        });
                        $("#umboxImageContent #submitFormButton").html("Update");
                        $("#umboxImageContent #clearFormButton").html("Cancel Edit");
                        $("#umboxImageContent .form-group #name").val($("#umboxImageTableBody #name" + umboxImage.id).html());
                        $("#umboxImageContent .form-group #fileName").val($("#umboxImageTableBody #fileName" + umboxImage.id).html());
                    });
                });

                umboxImageTable.on("click", "#deleteButton" + umboxImage.id, function () {
                    $.post("/delete-umbox-image", {id: umboxImage.id}, function (isSuccess) {
                        if (isSuccess == "true") {
                            umboxImageTable.row("#tableRow" + umboxImage.id).remove().draw();
                        } else {
                            alert("Delete was unsuccessful.  Please check that another table entry " +
                                "does not rely on this Umbox Image");
                        }
                    });
                });
            });
        });
    }

    $("#umboxImageContent #clearFormButton").click(function () {
        $.post("/clear-umbox-image-form", {}, function () {
            $("#umboxImageContent #submitFormButton").html("Add");
            $("#umboxImageContent #clearFormButton").html("Clear");
            $("#umboxImageContent .form-group #name").val("");
            $("#umboxImageContent .form-group #fileName").val("");
        });
    });

    //only load data when tab is active
    $('a[href="#UmboxImageContent"]').on('shown.bs.tab', function (e) {
        console.log("running umboxImage script");
        getUmboxImages();
    });
});