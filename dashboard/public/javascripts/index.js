jQuery(document).ready(($) => {
    function makeAttributessString(attributes) {
        let resultString = "";
        if (attributes != null) {
            Object.keys(attributes).forEach(key => {
                resultString = resultString + key + ": " + attributes[key] + "<br>";
            });

            resultString = resultString.substring(0, resultString.length - 4); //remove the last <br>
        }

        return resultString;
    }

    $.get("/devices", (devices) => {
        $.each(JSON.parse(devices), (index, device) => {
            $("#dashboardTableBody").append("<tr id='tableRow" + device.id + "'>\n" +
                "    <td><a href='/info?id=" + device.id + "'>" + device.name + "</a></td>\n" +
                "    <td id='securityState" + device.id  + "'>No Security State</td>\n" +
                "    <td id='deviceStatus" + device.id  + "'>No Status History</td>\n" +
                "    <td id='latestAlert" + device.id  + "'>No Alert History</td>\n" +
                "</tr>");

            $.get("/device-security-state", { id: device.id }, function(stateHistory) {
                let deviceState = JSON.parse(stateHistory);
                if(deviceState !== null) {
                    document.getElementById("securityState" + device.id).innerHTML = deviceState.name;
                }
            });

            $.get("/device-status-history", { id: device.id }, function(deviceHistory) {
                let arr = JSON.parse(deviceHistory);
                if(arr !== null && arr.length !== 0) {
                    // This sorts the array in reverse order by timestamp
                    arr.sort(function(a,b){ return b.timestamp - a.timestamp });
                    let statusAttributes = arr[0].attributes;

                    document.getElementById("deviceStatus" + device.id).innerHTML =
                        makeAttributessString(statusAttributes);
                }
            });

            $.get("/alert-history", { id: device.id }, function(alertHistory) {
                let arr = JSON.parse(alertHistory);
                if(arr !== null && arr.length !== 0) {
                    // This sorts the array in reverse order by timestamp
                    arr.sort(function(a,b){ return b.timestamp - a.timestamp });
                    document.getElementById("latestAlert" + device.id).innerHTML = arr[0].name;
                }
            });
        });
    });
});