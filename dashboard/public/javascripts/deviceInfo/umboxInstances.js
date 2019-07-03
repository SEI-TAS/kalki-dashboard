let given_id_umbox = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const timeFormat = "MMM Do YY, h:mm:ss a";

    $.fn.dataTable.moment(timeFormat);

    let umboxInstancesTable = $('#umboxInstancesTable').DataTable(
        {
            order: [[0, 'desc']],
            columnDefs: [
                {type: 'time-uni', targets: 0}
            ],
            language: {
                "emptyTable": "No umBox Instances"
            }
        }
    );

    $.get("/umbox-instances", { id: given_id_umbox }, function(umboxInstances) {
        let arr = JSON.parse(umboxInstances);
        if(arr !== null && arr.length !== 0) {
            arr.forEach((instance) => {
                $.get("/umbox-image", { id: instance.umboxImageId }, function(image) {
                    let newRow = "<tr>" +
                        "   <td>" + moment(instance.startedAt).format(timeFormat) + "</td>" +
                        "   <td>" + JSON.parse(image).name + "</td>" +
                        "</tr>";
                    umboxInstancesTable.row.add($(newRow)).draw();
                });
            });
        }
    });
});
