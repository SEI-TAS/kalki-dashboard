jQuery(document).ready(($) => {
    let timeFormat = "MMM Do YY, h:mm:ss a"
    $.fn.dataTable.moment(timeFormat);

    let homeTable = $('#homeTable').DataTable(
        {
            order: [[3, 'desc']],
            columnDefs: [
                {type: 'time-uni', targets: 3},
                {orderable: false, targets: 4}
            ],
            language: {
                "emptyTable": "No devices to show"
            }
        }
    );

    let currentDevices = null;
    let currentState = null;
    let currentLatestAlert = null;
    let currentDeviceStatus = null;

    function makeAttributesString(attributes) {
        let resultString = "";
        if (attributes != null) {
            Object.keys(attributes).forEach(key => {
                resultString = resultString + key + ": " + attributes[key] + "<br>";
            });

            resultString = resultString.substring(0, resultString.length - 4); //remove the last <br>
        }

        return resultString;
    }

    async function getSecurityState(id) {
        return $.get("/device-security-state", {id: id}, function (stateHistory) {
            let deviceState = JSON.parse(stateHistory);
            if(deviceState != null) {
                currentState = deviceState;
            }
            else {
                currentState = {name: "no current state"};
            }
        });
    }

    async function getLatestAlert(id) {
        return $.get("/alert-history", {id: id}, function (alertHistory) {
            let arr = JSON.parse(alertHistory);
            if (arr !== null && arr.length !== 0) {
                // This sorts the array in reverse order by timestamp
                arr.sort(function (a, b) {
                    return b.timestamp - a.timestamp
                });
                currentLatestAlert = arr[0];
            } else {
                currentLatestAlert = {name: "no alert history", deviceStatusId: -1};
            }
        });
    }

    async function getDeviceStatus(id) {
        return $.get("/device-status", {id: id}, function (deviceStatus) {
            if (deviceStatus != null) {
                currentDeviceStatus = JSON.parse(deviceStatus);
            } else {
                currentDeviceStatus = null;
            }
        });
    }

    async function getDevices() {
        return $.get("/devices", (devices) => {
            let deviceArray = [];
            $.each(JSON.parse(devices), (index, device) => {
                deviceArray.push(device);
            });
            currentDevices = deviceArray;
        });
    }

    async function fillTable() {
        await getDevices();

        for (const device of currentDevices) {
            await getSecurityState(device.id);
            await getLatestAlert(device.id);
            await getDeviceStatus(currentLatestAlert.deviceStatusId);

            let attributes = currentDeviceStatus ? makeAttributesString(currentDeviceStatus.attributes) : "";
            let formattedTime = currentLatestAlert.timestamp ? moment(currentLatestAlert.timestamp).format(timeFormat): "";

            let newRow = "<tr id='tableRow" + device.id + "'>\n" +
                "    <td><a href='/info?id=" + device.id + "'>" + device.name + "</a></td>\n" +
                "    <td id='securityState" + device.id + "'>" + currentState.name + "</td>\n" +
                "    <td id='latestAlert" + device.id + "'>" + currentLatestAlert.name + "</td>\n" +
                "    <td id='time" + device.id + "'>" + formattedTime + "</td>\n" +
                "    <td id='deviceStatus" + device.id + "'>" + attributes + "</td>\n" +
                "</tr>";
            homeTable.row.add($(newRow)).draw();
        }
    }

    fillTable();
});