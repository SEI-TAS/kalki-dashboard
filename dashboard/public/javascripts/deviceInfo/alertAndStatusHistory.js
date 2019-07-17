let given_id_alert = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const timeFormat = "MMM Do YY, h:mm:ss a";
    const infoPollInterval = 1 * 1000;

    $.fn.dataTable.moment(timeFormat);

    /*
        Alert History related
     */
    let alertHistoryTable = $('#alertHistoryTable').DataTable(
        {
            order: [[0, 'desc']],
            language: {
                "emptyTable": "No alert history"
            }
        }
    );

    async function getAlertHistory() {
        return $.get("/alert-history", { id: given_id_alert }, function(alertHistory) {
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
        return $.get("/get-new-alerts", (alerts) => {
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

    /*
        Status history related
     */
    let originalStatusIds = new Set();

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
       $.get("/device-status-history", { id: given_id_alert }, function(deviceHistory) {
            let arr = JSON.parse(deviceHistory);
            if(arr !== null && arr.length !== 0) {
                arr.forEach((status) => {
                    originalStatusIds.add(status.id);
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
                    if(status.deviceId == given_id_alert && !originalStatusIds.has(status.id)) {
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

    $("#statusHistoryContent #statusHistoryResetButton").click(() => {
        getNewStatuses();
    });

    /*
        General
     */
    function startListener() {
        $.post("/start-listener", () => {});
    }

    async function main() {
        await Promise.all([getAlertHistory(), getDeviceStatuses()]);

        startListener();

        setInterval(function () {
            getNewAlerts();
        }, infoPollInterval);
    }

    main();
});
