jQuery(document).ready(($) => {
    let securityStateTable = $('#securityStateTable').DataTable({
        order: [[1, 'asc']],
        columnDefs: [
            {"orderable": false, "targets": 0}
        ]
    });


    $.get("/security-states", (securityStates) => {
        $.each(JSON.parse(securityStates), (index, securityState) => {
            let newRow = "<tr id='tableRow" + securityState.id + "'>\n" +
                "    <td class='fit'>" +
                "        <div class='editDeleteContainer'>" +
                "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + securityState.id + "'>Edit</button>" +
                "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + securityState.id + "'>Delete</button>" +
                "        </div>" +
                "    </td>\n" +
                "    <td class='fit' id='id" + securityState.id + "'>" + securityState.id + "</td>\n" +
                "    <td id='name" + securityState.id + "'>" + securityState.name + "</td>\n" +
                "</tr>";
            securityStateTable.row.add($(newRow)).draw();

            securityStateTable.on("click", "#editButton" +securityState.id, function () {
                $.post("/edit-security-state", {id: securityState.id}, function () {
                    $('html, body').animate({ scrollTop: 0 }, 'fast', function () {});
                    $("#securityStateContent #submitFormButton").html("Update");
                    $("#securityStateContent #clearFormButton").html("Cancel Edit");
                    $("#securityStateContent .form-group #name").val($("#securityStateTableBody #name" +securityState.id).html());
                });
            });

            $("#securityStateTableBody #deleteButton" + securityState.id).click(function () {
                $.post("/delete-security-state", {id: securityState.id}, function (isSuccess) {
                    if(isSuccess == "true") {
                        securityStateTable.row("#tableRow" + securityState.id).remove().draw();
                    }
                    else {
                        alert("delete was unsuccessful");
                    }
                });
            });
        });
    });

    $("#securityStateContent #clearFormButton").click(function () {
        $.post("/clear-security-state-form", {}, function () {
            $("#securityStateContent #submitFormButton").html("Add");
            $("#securityStateContent #clearFormButton").html("Clear");
            $("#securityStateContent .form-group #name").val("");
        });
    });
});