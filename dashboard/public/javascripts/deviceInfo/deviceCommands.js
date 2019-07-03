let given_id_commands = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const timeFormat = "MMM Do YY, h:mm:ss a";

    $.fn.dataTable.moment(timeFormat);

    let deviceCommandsTable = $('#deviceCommandsTable').DataTable(
        {
            order: [[0, 'desc']],
            columnDefs: [
                {type: 'time-uni', targets: 0}
            ],
            language: {
                "emptyTable": "No Device Commands"
            }
        }
    );

    $.get("/get-commands-by-device", {id: given_id_commands}, function (commands) {
        let arr = JSON.parse(commands);
        if (arr !== null && arr.length !== 0) {
            arr.forEach((command) => {
                let newRow = "<tr>" +
                    "   <td>" + command.name + "</td>" +
                    "</tr>";
                deviceCommandsTable.row.add($(newRow)).draw();
            });
        }
    });
});
