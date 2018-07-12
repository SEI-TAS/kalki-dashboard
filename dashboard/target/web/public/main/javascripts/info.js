var id = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    $.get('/device', {"id": id}, (device) => {
        $("#name").append(device.name);
        $("#type").append(device.type);
    })
});
