jQuery(document).ready(($) => {
    let groupTable = $('#groupTable').DataTable({
        order: [[1, 'asc']],
        columnDefs: [
            {"orderable": false, "targets": 0}
        ]
    });

    function getGroups() {
        groupTable.clear();

        $.get("/groups", (groups) => {
            $.each(JSON.parse(groups), (index, group) => {
                let newRow = "<tr id='tableRow" + group.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer'>" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + group.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + group.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td class='fit' id='id" + group.id + "'>" + group.id + "</td>\n" +
                    "    <td id='name" + group.id + "'>" + group.name + "</td>\n" +
                    "</tr>";
                groupTable.row.add($(newRow)).draw();

                groupTable.on("click", "#editButton" + group.id, function () {
                    $.post("/edit-group", {id: group.id}, function () {
                        $('html, body').animate({scrollTop: 0}, 'fast', function () {
                        });
                        $("#groupContent #submitFormButton").html("Update");
                        $("#groupContent #clearFormButton").html("Cancel Edit");
                        $("#groupContent .form-group #name").val($("#groupTableBody #name" + group.id).html());
                    });
                });

                groupTable.on("click", "#deleteButton" + group.id, function () {
                    $.post("/delete-group", {id: group.id}, function (isSuccess) {
                        if (isSuccess == "true") {
                            groupTable.row("#tableRow" + group.id).remove().draw();
                        } else {
                            alert("Delete was unsuccessful.  Please check that another table entry " +
                                "does not rely on this Group");
                        }
                    });
                });
            });
        });
    }

    $("#groupContent #clearFormButton").click(function () {
        $.post("/clear-group-form", {}, function () {
            $("#groupContent #submitFormButton").html("Add");
            $("#groupContent #clearFormButton").html("Clear");
            $("#groupContent .form-group #name").val("");
        });
    });

    //only load data when tab is active
    $('a[href="#DeviceGroupContent"]').on('shown.bs.tab', function (e) {
        getGroups();
    });
});