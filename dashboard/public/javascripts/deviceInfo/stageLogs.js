let given_id_stagelogs = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const timeFormat = "MMM Do YY, h:mm:ss a";

    let stageLogTable = $('#stageLogTable').DataTable(
        {
            order: [[0, 'desc']],
            language: {
                "emptyTable": "No stage logs"
            }
        }
    );

   $.get("/stage-logs", { id: given_id_stagelogs }, function(stageLogs) {
      let arr = JSON.parse(stageLogs);
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
            "<td id='id" + log.id + "'>" + log.action + "</td>"+
            "<td id='id" + log.id + "'>" + log.stage + "</td>"+
            "<td id='id" + log.id + "'>" + log.info + "</td>"+
            "<td id='id" + log.id + "'>" + moment(log.timestamp).format(timeFormat) + "</td>"+
            "</tr>";

        stageLogTable.row.add($(newRow)).draw();
   }
});
