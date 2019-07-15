let given_id_alert = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const timeFormat = "MMM Do YY, h:mm:ss a";
    const infoPollInterval = 10 * 1000;

    $.fn.dataTable.moment(timeFormat);

    let alertHistoryTable = $('#alertHistoryTable').DataTable(
        {
            order: [[0, 'desc']],
            language: {
                "emptyTable": "No alert history"
            }
        }
    );

    async function getAlertHistory() {
        $.get("/alert-history", { id: given_id_alert }, function(alertHistory) {
            let arr = JSON.parse(alertHistory);
            if(arr !== null && arr.length !== 0) {
                arr.forEach(function(alert) {
                    let newRow = "<tr id='tableRow" +alert.id+ "'>" +
                        "   <td>" + moment(alert.timestamp).format(timeFormat) + "</td>" +
                        "   <td>" + alert.name + "</td>" +
                        "</tr>";
                    alertHistoryTable.row.add($(newRow)).draw();
                });
            }
        });
    }

    function getNewAlerts() {
        $.get("/get-new-alerts", (alerts) => {
            let newAlerts = JSON.parse(alerts);
            if (newAlerts != null) {
                newAlerts.forEach((alert) => {
                    if(alert.deviceId == given_id_alert) {
                        let alertId = alert.id;
                        let alertName = alert.name;
                        let formattedTime = alert.timestamp ? moment(alert.timestamp).format(timeFormat) : "";

                        let newRow = "<tr id='tableRow" +alertId+ "'>" +
                            "   <td>" + formattedTime + "</td>" +
                            "   <td>" + alertName + "</td>" +
                            "</tr>";
                        alertHistoryTable.row.add($(newRow)).draw();

                        $("#alertHistoryTable #tableRow" + alertId).addClass("updated");

                        setTimeout(function() {
                            $("#alertHistoryTable #tableRow" + alertId).removeClass("updated");
                        }, 3000);
                    }
                });
            }
        });
    }

    function startListener() {
        $.post("/start-listener", () => {});
    }

    async function main() {
        await getAlertHistory();

        startListener();

        setInterval(function () {
            getNewAlerts();
        }, infoPollInterval);
    }

    main();
});
