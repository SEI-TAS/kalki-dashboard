let given_id_status = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const timeFormat = "MMM Do YY, h:mm:ss a";
    const infoPollInterval = 10 * 1000;

    $.fn.dataTable.moment(timeFormat);

    let statusHistoryTable = $('#statusHistoryTable').DataTable(
        {
            order: [[0, 'desc']],
            columnDefs: [
                {orderable: false, targets: 1}
            ],
            language: {
                "emptyTable": "No status history"
            }
        }
    );

    function makeAttributesString(attributes) {
        let resultArray = [];
        let keys = Object.keys(attributes);
        keys.sort();
        keys.forEach((key) => {
            resultArray.push(key +": "+ attributes[key]);
        });

        return resultArray.join("<br>");
    }

    async function getDeviceStatuses() {
        $.get("/device-status-history", { id: given_id_status }, function(deviceHistory) {
            let arr = JSON.parse(deviceHistory);
            if(arr !== null && arr.length !== 0) {
                arr.forEach((status) => {
                    let newRow = "<tr id='tableRow" +status.id+ "'>" +
                        "   <td>" + moment(status.timestamp).format(timeFormat) + "</td>" +
                        "   <td>" + makeAttributesString(status.attributes) + "</td>" +
                        "</tr>";
                    statusHistoryTable.row.add($(newRow)).draw();
                });
            }
        });
    }

    function getNewStatuses() {
        $.get("/get-new-statuses", (statuses) => {
            let newStatuses = JSON.parse(statuses);
            if (newStatuses != null) {
                newStatuses.forEach((status) => {
                    if(status.deviceId == given_id_status) {
                        let newRow = "<tr id='tableRow" +status.id+ "'>" +
                            "   <td>" + moment(status.timestamp).format(timeFormat) + "</td>" +
                            "   <td>" + makeAttributesString(status.attributes) + "</td>" +
                            "</tr>";
                        statusHistoryTable.row.add($(newRow)).draw();

                        $("#statusHistoryTable #tableRow" + status.id).addClass("updated");
                        setTimeout(function() {
                            $("#statusHistoryTable #tableRow" + status.id).removeClass("updated");
                        }, 3000);
                    }
                });
            }
        });
    }

    async function main() {
        await getDeviceStatuses();

        setInterval(function () {
            getNewStatuses();
        }, infoPollInterval);
    }

    main();
});
