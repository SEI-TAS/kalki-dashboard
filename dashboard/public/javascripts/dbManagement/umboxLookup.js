jQuery(document).ready(($) => {
    let umboxLookupTable = $('#umboxLookupTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    //fill device types in form
    $.get("/device-types", (types) => {
        $.each(JSON.parse(types), (id, type) => {
            $("#umboxLookupContent #type").append("<option id='typeOption" + type.id + "' value='" + type.id + "'>" + type.name + "</option>");
        });
    });

    //fill security states in form
    $.get("/security-states", (securityStates) => {
        $.each(JSON.parse(securityStates), (id, securityState) => {
            $("#umboxLookupContent #securityState").append("<option id='securityStateOption" + securityState.id + "' value='" + securityState.id + "'>"
                + securityState.name + "</option>");
        });
    });

    //fill umbox images in form
    $.get("/umbox-images", (umboxImages) => {
        $.each(JSON.parse(umboxImages), (id, umboxImage) => {
            $("#umboxLookupContent #umboxImage").append("<option id='umboxImageOption" + umboxImage.id + "' value='" + umboxImage.id + "'>"
                + umboxImage.name + "</option>");
        });
    });

    /*

    $.get("/umbox-lookups", (umboxLookups) => {
        $.each(JSON.parse(umboxLookups), (index, umboxLookup) => {
            let newRow = "<tr id='tableRow" + umboxLookup.id + "'>\n" +
                "    <td class='fit'>" +
                "        <div class='editDeleteContainer' >" +
                "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + umboxLookup.id + "'>Edit</button>" +
                "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + umboxLookup.id + "'>Delete</button>" +
                "        </div>" +
                "    </td>\n" +
                "    <td id='umboxImage" + umboxLookup.id + "'>" + umboxLookup.umboxImage.name + "</td>\n" +
                "    <td id='deviceType" + umboxLookup.id + "'>" + umboxLookup.deviceType.name + "</td>\n" +
                "    <td id='securityState" + umboxLookup.id + "'>" + umboxLookup.securityState.name + "</td>\n" +
                "    <td class='fit' id='order" + umboxLookup.id + "'>" + umboxLookup.order + "</td>\n" +
                "</tr>"
            umboxLookupTable.row.add($(newRow)).draw();

            $("#umboxLookupTableBody #editButton" + umboxLookup.id).click(function () {
                $.post("/edit-umbox-lookup", {id: umboxLookup.id}, function () {
                    $('html, body').animate({scrollTop: 0}, 'fast', function () {});
                    $("#umboxLookupContent #submitFormButton").html("Update");
                    $("#umboxLookupContent #clearFormButton").html("Cancel Edit");
                });
            });

            $("#umboxLookupTableBody #deleteButton" + umboxLookup.id).click(function () {
                $.post("/delete-umbox-lookup", {id: umboxLookup.id}, function (isSuccess) {
                    if (isSuccess == "true") {
                        umboxLookupTable.row("#tableRow" + umboxLookup.id).remove().draw();
                    } else {
                        alert("delete was unsuccessful");
                    }
                });
            });
        });
    });
    
    $("#umboxLookupContent #clearFormButton").click(function () {
        $.post("/clear-umbox-lookup-form", {}, function () {
            $("#umboxLookupContent #submitFormButton").html("Add");
            $("#umboxLookupContent #clearFormButton").html("Clear");
            $("#umboxLookupContent .form-group #name").val("");
            $("#umboxLookupContent .form-group #description").val("");
            $("#umboxLookupContent .form-control#source").val("IoT Monitor");
        });
    });
     */
});