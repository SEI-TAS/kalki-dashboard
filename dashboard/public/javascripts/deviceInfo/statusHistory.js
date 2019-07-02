let given_id_status = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const timeFormat = "MMM Do YY, h:mm:ss a";

    $.fn.dataTable.moment(timeFormat);

    let statusHistoryTable = $('#statusHistoryTable').DataTable(
        {
            order: [[0, 'desc']],
            columnDefs: [
                {type: 'time-uni', targets: 0},
                {orderable: false, targets: 2}
            ],
            language: {
                "emptyTable": "No alert history"
            }
        }
    );

    $.get("/device-status-history", { id: given_id_status }, function(deviceHistory) {
        let arr = JSON.parse(deviceHistory);
        if(arr !== null && arr.length !== 0) {
            let obj = arr[0].attributes;
            for (let key in obj) {
                let newRow = "<tr>" +
                    "   <td>" + moment(alert.timestamp).format(timeFormat) + "</td>" +
                    "   <td>" + key + "</td>" +
                    "   <td>" + obj[key] + "</td>" +
                    "</tr>";
                statusHistoryTable.row.add($(newRow)).draw();
            }
        }
    });
});
