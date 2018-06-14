/**
 * Created by keegan on 1/12/18.
 */

/***
 * Change the footer so that it is always hugging the bottom of the page, no matter the page height.
 * If the page doesn't reach the bottom, then the footer is pulled down.
 */
// Do this check the first time the page is loaded
$(document).ready(function() {
    moveFooter()
});
// Check this also when the page is resized
window.addEventListener('resize', moveFooter, true);

function moveFooter() {
    // From https://stackoverflow.com/a/20971428
    // Solution for sticky footer issue
    var docHeight = $(window).height();
    var footerHeight = $('footer').outerHeight();
    var footerTop = $('footer').position().top + footerHeight;
    if (footerTop < docHeight) {
        $('footer').css('margin-top', 1 + (docHeight - footerTop) + 'px');
    } else {
        $('footer').css('margin-top', '0px');
    }
}