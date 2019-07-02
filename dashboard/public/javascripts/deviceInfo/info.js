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
        }
    })
});
