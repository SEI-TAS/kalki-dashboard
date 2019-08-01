//This javascript allows the page to remember what tab is was on even after page refresh

jQuery(document).ready(($) => {
    let activeTab = window.sessionStorage.getItem('activeTab');

    if (activeTab) {
        $('a[href="' + activeTab + '"]').tab('show');
    }
    else {
        $(".default-tab").tab('show');
    }

    $(".dbManagementView  .nav-link").click(function (e) {
        let tab_name = this.getAttribute('href');

        window.sessionStorage.setItem('activeTab', tab_name);

        $(this).tab('show');
        return false;
    });
});

//clear all edits on page load
$(window).on('load', function(){
    $.post("/clear-alert-type-form", {}, function () {});
    $.post("/clear-alert-type-lookup-form", {}, function () {});
    $.post("/clear-device-type-form", {}, function () {});
    $.post("/clear-group-form", {}, function () {});
    $.post("/clear-security-state-form", {}, function () {});
    $.post("/clear-tag-form", {}, function () {});
    $.post("/clear-umbox-image-form", {}, function () {});
    $.post("/clear-device-form", {}, function () {});
    $.post("/clear-command-lookup-form", {}, function () {});
    $.post("/clear-umbox-lookup-form", {}, function () {});
});