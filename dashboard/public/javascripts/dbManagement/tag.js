jQuery(document).ready(($) => {
    let tagTable = $('#tagTable').DataTable({
        order: [[1, 'asc']],
        columnDefs: [
            {"orderable": false, "targets": 0}
        ]
    });


    $.get("/tags", (tags) => {
        $.each(JSON.parse(tags), (index, tag) => {
            let newRow = "<tr id='tableRow" + tag.id + "'>\n" +
                "    <td class='fit'>" +
                "        <div class='editDeleteContainer'>" +
                "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + tag.id + "'>Edit</button>" +
                "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + tag.id + "'>Delete</button>" +
                "        </div>" +
                "    </td>\n" +
                "    <td class='fit' id='id" + tag.id + "'>" + tag.id + "</td>\n" +
                "    <td id='name" + tag.id + "'>" + tag.name + "</td>\n" +
                "</tr>";
            tagTable.row.add($(newRow)).draw();

            $("#tagTableBody #editButton" + tag.id).click(function () {
                $.post("/edit-tag", {id: tag.id}, function () {
                    $('html, body').animate({ scrollTop: 0 }, 'fast', function () {});
                    $("#tagContent #submitFormButton").html("Update");
                    $("#tagContent #clearFormButton").html("Cancel Edit");
                    $("#tagContent .form-group #name").val($("#tagTableBody #name" +tag.id).html());
                });
            });

            $("#tagTableBody #deleteButton" + tag.id).click(function () {
                $.post("/delete-tag", {id: tag.id}, function (isSuccess) {
                    if(isSuccess == "true") {
                        tagTable.row("#tableRow" + tag.id).remove().draw();
                    }
                    else {
                        alert("delete was unsuccessful");
                    }
                });
            });
        });
    });

    $("#tagContent #clearFormButton").click(function () {
        $.post("/clear-tag-form", {}, function () {
            $("#tagContent #submitFormButton").html("Add");
            $("#tagContent #clearFormButton").html("Clear");
            $("#tagContent .form-group #name").val("");
        });
    });
});