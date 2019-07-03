let given_id_status = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const timeFormat = "MMM Do YY, h:mm:ss a";

    $.fn.dataTable.moment(timeFormat);

    let statusHistoryTable = $('#statusHistoryTable').DataTable(
        {
            order: [[0, 'desc']],
            columnDefs: [
                {type: 'time-uni', targets: 0},
                {orderable: false, targets: 1}
            ],
            language: {
                "emptyTable": "No status history"
            }
        }
    );

    function makeAttributesString(attributes) {
        let resultArray = [];
        Object.keys(attributes).forEach((key) => {
            resultArray.push(key +": "+ attributes.key);
        });

        return resultArray.join("<br>");
    }

    $.get("/device-status-history", { id: given_id_status }, function(deviceHistory) {
        let arr = JSON.parse(deviceHistory);
        if(arr !== null && arr.length !== 0) {
            arr.forEach((status) => {
                let newRow = "<tr>" +
                    "   <td>" + moment(status.timestamp).format(timeFormat) + "</td>" +
                    "   <td>" + makeAttributesString(status.attributes) + "</td>" +
                    "</tr>";
                statusHistoryTable.row.add($(newRow)).draw();
            });
        }
    });
});
