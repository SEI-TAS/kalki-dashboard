var id = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    $.get('/device', {"id": id}, (device) => {
        $("#name").append(device[0].name);
        $("#type").append(device[0].type);
    })
});
