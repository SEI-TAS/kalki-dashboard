var deviceId = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($)  => {
    $.get("/device", { id: deviceId }, (deviceString) => {
        let device = JSON.parse(deviceString);
        if(device === null) {
            console.log("Invalid ID");
        }
        else {
            document.getElementById("name").value = device.name;
            document.getElementById("description").value = device.description;
            document.getElementById("ipAddress").value = device.ip;
            document.getElementById("historySize").value = device.historySize;
            document.getElementById("samplingRate").value = device.samplingRate;
        }
    });
});