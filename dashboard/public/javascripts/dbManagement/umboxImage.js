jQuery(document).ready(($) => {
    let umboxImageTable = $('#umboxImageTable').DataTable({
        order: [[1, 'asc']],
        columnDefs: [
            {"orderable": false, "targets": 0}
        ]
    });


    $.get("/get-umbox-images", (umboxImages) => {
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
                "    <td id='path" + umboxImage.id + "'>" + umboxImage.path + "</td>\n" +
                "</tr>";
            umboxImageTable.row.add($(newRow)).draw();

            $("#umboxImageTableBody #editButton" + umboxImage.id).click(function () {
                $.post("/edit-umbox-image", {id: umboxImage.id}, function () {
                    $('html, body').animate({ scrollTop: 0 }, 'fast', function () {});
                    $("#umboxImageContent #submitFormButton").html("Update");
                    $("#umboxImageContent #clearFormButton").html("Cancel Edit");
                    $("#umboxImageContent .form-group #name").val($("#umboxImageTableBody #name" +umboxImage.id).html());
                    $("#umboxImageContent .form-group #path").val($("#umboxImageTableBody #path" +umboxImage.id).html());
                });
            });

            $("#umboxImageTableBody #deleteButton" + umboxImage.id).click(function () {
                $.post("/delete-umbox-image", {id: umboxImage.id}, function (isSuccess) {
                    if(isSuccess == "true") {
                        umboxImageTable.row("#tableRow" + umboxImage.id).remove().draw();
                    }
                    else {
                        alert("delete was unsuccessful");
                    }
                });
            });
        });
    });

    $("#umboxImageContent #clearFormButton").click(function () {
        $.post("/clear-umbox-image-form", {}, function () {
            $("#umboxImageContent #submitFormButton").html("Add");
            $("#umboxImageContent #clearFormButton").html("Clear");
            $("#umboxImageContent .form-group #name").val("");
            $("#umboxImageContent .form-group #path").val("");
        });
    });
});