var id = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    $.get('/device', {"id": id}, (deviceString) => {
        let device = JSON.parse(deviceString);
        if(device === null) {
            console.log("Invalid ID");
        }
        else {
            $("#name").text(device.name);
            $("#type").text(device.type.name);

            if(device.type.policyFileName) {
                $("#policyFile").text(device.type.policyFileName);
            }
            else {
                $("#policyFile").text("No policy file");
            }


            $.get("/device-security-state", { id: device.id }, function(stateHistory) {
                let deviceState = JSON.parse(stateHistory);
                if(deviceState !== null) {
                    $("#securityState").text(deviceState.name);
                }
            });

            $.get("/alert-history", { id: device.id }, function(alertHistory) {
                let arr = JSON.parse(alertHistory);
                console.log(alertHistory);
                if(arr !== null && arr.length !== 0) {
                    // This sorts the array in reverse order by timestamp
                    arr.sort(function(a,b){ return b.timestamp - a.timestamp });
                    arr.forEach(function(alert) {
                        $("#alertHistoryTableBody").append("<tr>" +
                            "<td>" + new Date(alert.timestamp).toLocaleString() + "</td>" +
                            "<td>" + alert.name + "</td>" +
                            "</tr>")
                    });
                }
            });

            $.get("/device-status-history", { id: device.id }, function(deviceHistory) {
                let arr = JSON.parse(deviceHistory);
                if(arr !== null && arr.length !== 0) {
                    // This sorts the array in reverse order by timestamp
                    arr.sort(function(a,b){ return b.timestamp - a.timestamp });
                    let obj = arr[0].attributes;
                    for (var key in obj) {
                        $("#attributesTableBody").append("<tr>" +
                            "<td>" + key + "</td>" +
                            "<td>" + obj[key] + "</td>" +
                            "</tr>")
                    }
                }
            });
        }
    })
});
