var id = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    $.get('/device', {"id": id}, (deviceString) => {
        let device = JSON.parse(deviceString);
        if(device === null) {
            console.log("Invalid ID");
        }
        else {
            $("#deviceInfoPage #name").text(device.name);
            $("#deviceInfoPage #type").text(device.type.name);
            if(device.group != null) {
                $("#deviceInfoPage #group").text(device.group.name);
            } else {
                $("#deviceInfoPage #group").text("N/A");
            }
        }

        //once findTagsByDevice is implemented add them here
    });

    $.get("/device-security-state", { id: id }, function(stateHistory) {
        let deviceState = JSON.parse(stateHistory);
        if(deviceState !== null) {
            $("#securityState").text(deviceState.name);
        }
    });
});
