jQuery(document).ready(($) => {
    // Setup the data table for the data node tab
    let dataNodeTable = $('#dataNodeTable').DataTable({
        order: [[1, 'asc']],
        columnDefs: [
            {"orderable": false, "targets": 0}
        ]
    });

    function getDataNodes() {
        /**
         * Get all data nodes and put them into the data table.
         */
        dataNodeTable.clear();

        $.get("/data-nodes", (dataNodes) => {
            $.each(JSON.parse(dataNodes), (index, dataNode) => {
                // Create the row and add it to the table.
                let newRow = "<tr id='tableRow" + dataNode.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer'>" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + dataNode.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + dataNode.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td class='fit' id='id" + dataNode.id + "'>" + dataNode.id + "</td>\n" +
                    "    <td id='name" + dataNode.id + "'>" + dataNode.name + "</td>\n" +
                    "    <td id='ipAddress" + dataNode.id + "'>" + dataNode.ipAddress + "</td>\n" +
                    "</tr>";
                dataNodeTable.row.add($(newRow)).draw();

                // Set the on edit function for the row
                dataNodeTable.on("click", "#editButton" +dataNode.id, function () {
                    $.post("/edit-data-node", {id: dataNode.id}, function () {
                        $('html, body').animate({ scrollTop: 0 }, 'fast', function () {});
                        $("#dataNodeContent #submitFormButton").html("Update");
                        $("#dataNodeContent #clearFormButton").html("Cancel Edit");

                        // Change the fields specific to this page
                        $("#dataNodeContent .form-group #name").val($("#dataNodeTableBody #name" +dataNode.id).html());
                        $("#dataNodeContent .form-group #ipAddress").val($("#dataNodeTableBody #ipAddress" +dataNode.id).html());
                    });
                });

                // Set the on delete function for the row
                dataNodeTable.on("click", "#deleteButton" +dataNode.id, function () {
                    $.post("/delete-data-node", {id: dataNode.id}, function (isSuccess) {
                        if(isSuccess == "true") {
                            dataNodeTable.row("#tableRow" + dataNode.id).remove().draw();
                        }
                        else {
                            alert("Delete was unsuccessful.  Please check that another table entry " +
                                "does not rely on this Data Node");
                        }
                    });
                });
            });
        });
    }

    // Set the clear function for the table
    $("#dataNodeContent #clearFormButton").click(function () {
        $.post("/clear-data-node-form", {}, function () {
            $("#dataNodeContent #submitFormButton").html("Add");
            $("#dataNodeContent #clearFormButton").html("Clear");

            // Reset the fields specific to this page
            $("#dataNodeContent .form-group #name").val("");
            $("#dataNodeContent .form-group #ipAddress").val("");
        });
    });

    // Only load this page when tab is active
    $('a[href="#DataNodeContent"]').on('shown.bs.tab', function (e) {
        getDataNodes();
    });
});