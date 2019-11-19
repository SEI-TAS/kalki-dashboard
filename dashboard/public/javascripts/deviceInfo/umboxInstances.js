let given_id_umbox = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const timeFormat = "MMM Do YY, h:mm:ss a";

    let umboxInstancesTable = $("#umboxInstancesTable");

    $.get("/umbox-instances", { id: given_id_umbox }, function(umboxInstances) {
        let arr = JSON.parse(umboxInstances);
        if(arr !== null && arr.length !== 0) {
            arr.forEach((instance) => {
                $.get("/umbox-image", { id: instance.umboxImageId }, function(image) {
                    let img = JSON.parse(image);

                    let newRow = "<tr>" +
                        "   <td>" + instance.id +"</td>" +
                        "   <td>" + instance.alerterId +"</td>" +
                        "   <td>" + moment(instance.startedAt).format(timeFormat) + "</td>" +
                        "   <td>" + JSON.parse(image).name + "</td>" +
                        "</tr>";
                    umboxInstancesTable.append(newRow);
                });
            });
        }
    });
});
