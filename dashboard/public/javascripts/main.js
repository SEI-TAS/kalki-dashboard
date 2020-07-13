//This javascript allows the page to remember what tab it was on even after page refresh
jQuery(document).ready(($) => {
    let activeTab = window.sessionStorage.getItem('activeTab');

    if (activeTab) {
        $('a[href="' + activeTab + '"]').tab('show');
    }
    else {
        $('#default-tab').tab('show');
    }

    $(".dbManagementView  .nav-link").click(function (e) {
        console.log("Setting active tab");
        let tab_name = this.getAttribute('href');

        window.sessionStorage.setItem('activeTab', tab_name);

        $(this).tab('show');
        return false;
    });

    $("#deviceInfoPage  .nav-link").click(function (e) {
        console.log("Setting active tab");
        let tab_name = this.getAttribute('href');

        window.sessionStorage.setItem('activeTab', tab_name);

        $(this).tab('show');
        return false;
    });

    $(".main-header  .nav-link").click(function (e) {
        console.log("resetting active tab");

        window.sessionStorage.setItem('activeTab', "");

    });
});
