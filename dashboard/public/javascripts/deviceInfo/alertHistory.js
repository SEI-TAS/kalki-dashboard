let given_id_alert = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const timeFormat = "MMM Do YY, h:mm:ss a";

    $.fn.dataTable.moment(timeFormat);

    let alertHistoryTable = $('#alertHistoryTable').DataTable(
        {
            order: [[0, 'desc']],
            language: {
                "emptyTable": "No alert history"
            }
        }
    );

    $.get("/alert-history", { id: given_id_alert }, function(alertHistory) {
        let arr = JSON.parse(alertHistory);
        if(arr !== null && arr.length !== 0) {
            arr.forEach(function(alert) {
                let newRow = "<tr>" +
                "   <td>" + moment(alert.timestamp).format(timeFormat) + "</td>" +
                "   <td>" + alert.name + "</td>" +
                "</tr>";
                alertHistoryTable.row.add($(newRow)).draw();
            });
        }
    });
});
