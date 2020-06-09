jQuery(document).ready(($) => {

});

//clear all edits on page load
//This needs to be done to clear the updating IDs of each controller
$(window).on('load', function(){
    $.post("/clear-alert-type-form", {}, function () {});
    $.post("/clear-alert-type-lookup-form", {}, function () {});
    $.post("/clear-device-type-form", {}, function () {});
    $.post("/clear-group-form", {}, function () {});
    $.post("/clear-security-state-form", {}, function () {});
    $.post("/clear-data-node-form", {}, function () {});
    $.post("/clear-tag-form", {}, function () {});
    $.post("/clear-umbox-image-form", {}, function () {});
    $.post("/clear-device-form", {}, function () {});
    $.post("/clear-command-lookup-form", {}, function () {});
    $.post("/clear-umbox-lookup-form", {}, function () {});
    $.post("/clear-command-form", {}, function () {});
});
