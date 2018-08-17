var id = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    $.get('/device', {"id": id}, (deviceString) => {
        let device = JSON.parse(deviceString);
        if(device === null) {
            console.log("Invalid ID");
        }
        else {
            $("#name").append(device.name);
            $("#type").append(device.typeId);
            $("#policyFile").append(device.policyFileName);

            $.get("/state-history", { id: device.id }, function(stateHistory) {
                let arr = JSON.parse(stateHistory);
                if(arr !== null && arr.length !== 0) {
                    // This sorts the array in reverse order by timestamp
                    arr.sort(function(a,b){ return b.timestamp - a.timestamp });
                    $("#securityState").append(arr[0].state);
                }
            });

            $.get("/alert-history", { id: device.id }, function(alertHistory) {
                let arr = JSON.parse(alertHistory);
                if(arr !== null && arr.length !== 0) {
                    // This sorts the array in reverse order by timestamp
                    arr.sort(function(a,b){ return b.timestamp - a.timestamp });
                    arr.forEach(function(alert) {
                        $("#alertHistoryTableBody").append("<tr>" +
                            "<td>" + new Date(alert.timestamp).toString() + "</td>" +
                            "<td>" + alert.externalId + "</td>" +
                            "<td>" + alert.name + "</td>" +
                            "</tr>")
                    });
                }
            });

            $.get("/device-history", { id: device.id }, function(deviceHistory) {
                let arr = JSON.parse(deviceHistory);
                if(arr !== null && arr.length !== 0) {
                    // This sorts the array in reverse order by timestamp
                    arr.sort(function(a,b){ return b.timestamp - a.timestamp });
                    let obj = arr[0].attributes;
                    for (var key in obj) {
                        $("#variablesTableBody").append("<tr>" +
                            "<td>" + key + "</td>" +
                            "<td>" + obj[key] + "</td>" +
                            "</tr>")
                    }
                }
            });
        }
    })
});
