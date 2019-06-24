jQuery(document).ready(($) => {
    let alertTypeTable = $('#alertTypeTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    $.get("/alert-types", (alertTypes) => {
        $.each(JSON.parse(alertTypes), (index, alertType) => {
            let newRow = "<tr id='tableRow" + alertType.id + "'>\n" +
                "    <td class='fit'>" +
                "        <div class='editDeleteContainer' >" +
                "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + alertType.id + "'>Edit</button>" +
                "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + alertType.id + "'>Delete</button>" +
                "        </div>" +
                "    </td>\n" +
                "    <td class='fit' id='alertTypeID" + alertType.id + "'>" + alertType.id + "</td>\n" +
                "    <td id='name" + alertType.id + "'>" + alertType.name + "</td>\n" +
                "    <td id='description" + alertType.id + "'>" + alertType.description + "</td>\n" +
                "    <td id='source" + alertType.id + "'>" + alertType.source + "</td>\n" +
                "</tr>"
            alertTypeTable.row.add($(newRow)).draw();

            alertTypeTable.on("click", "#editButton" + alertType.id, function () {
                $.post("/edit-alert-type", {id: alertType.id}, function () {
                    $('html, body').animate({scrollTop: 0}, 'fast', function () {
                    });
                    $("#alertTypeContent #submitFormButton").html("Update");
                    $("#alertTypeContent #clearFormButton").html("Cancel Edit");
                    $("#alertTypeContent .form-group #name").val($("#alertTypeTableBody #name" + alertType.id).html());
                    $("#alertTypeContent .form-group #description").val($("#alertTypeTableBody #description" + alertType.id).html());
                    $("#alertTypeContent .form-control#source").val($("#alertTypeTableBody #source" + alertType.id).html()).change();
                });
            });

            alertTypeTable.on("click", "#deleteButton" + alertType.id, function () {
                $.post("/delete-alert-type", {id: alertType.id}, function (isSuccess) {
                    if (isSuccess == "true") {
                        alertTypeTable.row("#tableRow" + alertType.id).remove().draw();
                    } else {
                        alert("delete was unsuccessful");
                    }

                });
            });
        });
    });

    $("#alertTypeContent #clearFormButton").click(function () {
        $.post("/clear-alert-type-form", {}, function () {
            $("#alertTypeContent #submitFormButton").html("Add");
            $("#alertTypeContent #clearFormButton").html("Clear");
            $("#alertTypeContent .form-group #name").val("");
            $("#alertTypeContent .form-group #description").val("");
            $("#alertTypeContent .form-control#source").val("IoT Interface");
        });
    });
});