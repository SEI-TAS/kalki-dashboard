jQuery(document).ready(($) => {
    let deviceTypeTable = $('#deviceTypeTable').DataTable({
        order: [[1, 'asc']],
        columnDefs: [
            {"orderable": false, "targets": 0}
        ]
    });


    $.get("/device-types", (deviceTypes) => {
        $.each(JSON.parse(deviceTypes), (index, deviceType) => {
            let newRow = "<tr id='tableRow" + deviceType.id + "'>\n" +
                "    <td class='fit'>" +
                "        <div class='editDeleteContainer'>" +
                "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + deviceType.id + "'>Edit</button>" +
                "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + deviceType.id + "'>Delete</button>" +
                "        </div>" +
                "    </td>\n" +
                "    <td class='fit' id='id" + deviceType.id + "'>" + deviceType.id + "</td>\n" +
                "    <td id='name" + deviceType.id + "'>" + deviceType.name + "</td>\n" +
                "</tr>";
            deviceTypeTable.row.add($(newRow)).draw();

            deviceTypeTable.on("click", "#editButton" + deviceType.id, function () {
                $.post("/edit-device-type", {id: deviceType.id}, function () {
                    $('html, body').animate({scrollTop: 0}, 'fast', function () {
                    });
                    $("#deviceTypeContent #submitFormButton").html("Update");
                    $("#deviceTypeContent #clearFormButton").html("Cancel Edit");
                    $("#deviceTypeContent .form-group #name").val($("#deviceTypeTableBody #name" + deviceType.id).html());
                });
            });

            deviceTypeTable.on("click", "#deleteButton" + deviceType.id, function () {
                $.post("/delete-device-type", {id: deviceType.id}, function (isSuccess) {
                    if (isSuccess == "true") {
                        deviceTypeTable.row("#tableRow" + deviceType.id).remove().draw();
                    } else {
                        alert("Delete was unsuccessful.  Please check that another table entry " +
                            "does not rely on this Device Type");
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