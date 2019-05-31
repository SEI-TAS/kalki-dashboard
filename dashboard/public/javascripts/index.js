jQuery(document).ready(($) => {
    $.get("/devices", (devices) => {
        $.each(JSON.parse(devices), (index, device) => {
            $("#dashboardTableBody").append("<tr id='tableRow" + device.id + "'>\n" +
                "    <td><a href='/info?id=" + device.id + "' class='btn'>" + device.name + "</a></td>\n" +
                "    <td id='stateHistory" + device.id  + "'>No State History</td>\n" +
                "    <td id='deviceHistory" + device.id  + "'>No Device History</td>\n" +
                "    <td id='alertHistory" + device.id  + "'>No Alert History</td>\n" +
                "    <td><button type='button' class='btn btn-secondary' id='editButton" + device.id + "'>Edit</button></td>\n" +
                "    <td><button type='button' class='btn btn-secondary' id='deleteButton" + device.id + "'>Delete</button></td>\n" +
                "</tr>");

            $.get("/state-history", { id: device.id }, function(stateHistory) {
                let arr = JSON.parse(stateHistory);
                if(arr !== null && arr.length !== 0) {
                    // This sorts the array in reverse order by timestamp
                    arr.sort(function(a,b){ return b.timestamp - a.timestamp });
                    document.getElementById("stateHistory" + device.id).innerHTML = arr[0].state;
                }
            });

            $.get("/device-history", { id: device.id }, function(deviceHistory) {
                let arr = JSON.parse(deviceHistory);
                if(arr !== null && arr.length !== 0) {
                    // This sorts the array in reverse order by timestamp
                    arr.sort(function(a,b){ return b.timestamp - a.timestamp });
                    document.getElementById("deviceHistory" + device.id).innerHTML = JSON.stringify(arr[0].attributes);
                }
            });

            $.get("/alert-history", { id: device.id }, function(alertHistory) {
                let arr = JSON.parse(alertHistory);
                if(arr !== null && arr.length !== 0) {
                    // This sorts the array in reverse order by timestamp
                    arr.sort(function(a,b){ return b.timestamp - a.timestamp });
                    document.getElementById("alertHistory" + device.id).innerHTML = arr[0].name;
                }
            });

            $("#editButton" + device.id).click(function() {
                $(location).prop("href","/edit-device?id=" + device.id);
            });

            $("#deleteButton" + device.id).click(function() {
                $.post("/delete-device", { id: device.id }, function() {
                    $("#tableRow" + device.id).remove();
                });
            });
        });
    });

    $("#setupDatabaseButton").click(function() {
        $.post("/setup-database");
    });

    $("#resetDatabaseButton").click(function() {
        $.post("/reset-database");
    });

    $("#listDatabasesButton").click(function() {
        $.post("/list-all-databases");
    });
});