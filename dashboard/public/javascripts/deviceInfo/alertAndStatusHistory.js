let given_id_alert = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const timeFormat = "MMM Do YY, h:mm:ss a";
    const infoPollInterval = 1 * 1000;
    let lowestStatusId = -1;

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

                        //add updated class to highlight updated row
                        $("#alertHistoryTable #tableRow" + alertId).addClass("updated");

                        //remove row highlight after three seconds
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
    
    //a set to determine which statuses were populated on page load
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
            addStatusesToTable(arr, false, given_id_alert);
        });
    }

    function getNewStatuses() {
        $.get("/get-new-statuses", (statuses) => {
            let newStatuses = JSON.parse(statuses);
            addStatusesToTable(newStatuses, true, given_id_alert);
        });
    }

    function addStatusesToTable(statuses, newStatuses, device_id){
        if (statuses !== null && statuses.length !== 0) {
            let ind = statuses.length - 1;
            if(statuses[ind].id < lowestStatusId){
                lowestStatusId = statuses[ind].id;
            }
            statuses.forEach((status) => {
                if(device_id == status.deviceId && !originalStatusIds.has(status.deviceId)){
                    originalStatusIds.add(status.id);
                    let newRow = "<tr id='tableRow" + status.id + "'>" +
                        "   <td>" + moment(status.timestamp).format(timeFormat) + "</td>" +
                        "   <td>" + makeAttributesString(status.attributes) + "</td>" +
                        "</tr>";
                    statusHistoryTable.row.add($(newRow)).draw();

                    if(newStatuses){
                        $("#statusHistoryTable #tableRow" + status.id).addClass("updated");
                        setTimeout(function() {
                            $("#statusHistoryTable #tableRow" + status.id).removeClass("updated");
                        }, 3000);
                    }
                }
            });
        }
    }


    $("#statusHistoryContent #statusHistoryResetButton").click(() => {
        getNewStatuses();
    });

    $("#fetchMoreStatuses").click(() => {
        return $.get("/next-statuses",{ id: given_id_alert, lowestId: lowestStatusId }, (statuses) => {
            let arr = JSON.parse(statuses);
            addStatusesToTable(arr, false, given_id_alert);
            statusHistoryTable.order(0, 'desc');
        });
    });

    /*
        General
     */

    //tell the controller to start listening for new updates
    function startListener() {
        $.post("/start-listener", () => {});
    }

    async function main() {
        //wait for the tables be populated before listening for new entries
        await Promise.all([getAlertHistory(), getDeviceStatuses()]);

        startListener();

        setInterval(function () {
            getNewAlerts();
        }, infoPollInterval);
    }

    main();
});
