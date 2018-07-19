var id = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    $.get('/device', {"id": id}, (deviceString) => {
        let device = JSON.parse(deviceString);
        if(device === null) {
            console.log("Invalid ID");
        }
        else {
            $("#name").append(device.name);
            $("#type").append(device.type);
        }
    })
});
