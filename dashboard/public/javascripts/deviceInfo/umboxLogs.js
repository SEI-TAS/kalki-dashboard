let given_id_umboxlogs = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const timeFormat = "MMM Do YY, h:mm:ss a";

    let umboxLogTable = $('#umboxLogTable');

    $.get("/umbox-logs", { id: given_id_umboxlogs }, function(umboxLogs) {
        let arr = JSON.parse(umboxLogs);
        if(arr !== null && arr.length !== 0) {
            arr.forEach((log) => {
                console.log("log: ", log);
                appendToTable(log);
            });
        }
    });

    function appendToTable(log) {
        let newRow = "<tr id='tableRow" + log.id + "'>\n" +
            "<td id='id" + log.id + "'>" + log.id + "</td>"+
            "<td id='id" + log.id + "'>" + log.alerterId + "</td>"+
            "<td id='id" + log.id + "'>" + log.details + "</td>"+
            "<td id='id" + log.id + "'>" + moment(log.timestamp).format(timeFormat) + "</td>"+
            "</tr>";

        umboxLogTable.append(newRow);
    }
});
