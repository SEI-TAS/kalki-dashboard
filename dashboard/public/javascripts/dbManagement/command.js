jQuery(document).ready(($) => {
    let commandTable = $('#commandTable').DataTable({
        order: [[1, 'asc']],
        columnDefs: [
            {"orderable": false, "targets": 0}
        ]
    });


    $.get("/commands", (commands) => {
        $.each(JSON.parse(commands), (index, command) => {
            let newRow = "<tr id='tableRow" + command.id + "'>\n" +
                "    <td class='fit'>" +
                "        <div class='editDeleteContainer'>" +
                "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + command.id + "'>Edit</button>" +
                "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + command.id + "'>Delete</button>" +
                "        </div>" +
                "    </td>\n" +
                "    <td class='fit' id='id" + command.id + "'>" + command.id + "</td>\n" +
                "    <td id='name" + command.id + "'>" + command.name + "</td>\n" +
                "</tr>";
            commandTable.row.add($(newRow)).draw();

            commandTable.on("click", "#editButton" +command.id, function () {
                $.post("/edit-command", {id: command.id}, function () {
                    $('html, body').animate({ scrollTop: 0 }, 'fast', function () {});
                    $("#commandContent #submitFormButton").html("Update");
                    $("#commandContent #clearFormButton").html("Cancel Edit");
                    $("#commandContent .form-group #name").val($("#commandTableBody #name" +command.id).html());
                });
            });

            commandTable.on("click", "#deleteButton" +command.id, function () {
                $.post("/delete-command", {id: command.id}, function (isSuccess) {
                    if(isSuccess == "true") {
                        commandTable.row("#tableRow" + command.id).remove().draw();
                    }
                    else {
                        alert("delete was unsuccessful");
                    }
                });
            });
        });
    });

    $("#commandContent #clearFormButton").click(function () {
        $.post("/clear-command-form", {}, function () {
            $("#commandContent #submitFormButton").html("Add");
            $("#commandContent #clearFormButton").html("Clear");
            $("#commandContent .form-group #name").val("");
        });
    });
});