jQuery(document).ready(($) => {
    let umboxLookupTable = $('#umboxLookupTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    let umboxImageIdDagOrderMap = {};

    let stateIdToNameMap = {};
    let deviceTypeIdToNameMap = {};
    let umboxImageIDtoNameMap = {};

    let rowCounter = 0;

    function addOrderRow(umboxImageId, order) {
        let currentCount = ++rowCounter;

        let newRow = "<tr id='umboxImageOrderTableRow" + currentCount+ "'>\n" +
            "    <td class='fit'><button type='button' class='btn btn-primary btn-sm' id='removeButton" + currentCount + "'>Remove</button></td>" +
            "    <td id='umboxImage" + currentCount + "'>" + umboxImageIDtoNameMap[umboxImageId] + "</td>\n" +
            "    <td id='order" + currentCount + "'>" + order + "</td>\n" +
            "</tr>"

        $("#umboxImageOrderTable").find("tbody").append($(newRow));

        //add umbox to dag order relationship to map
        umboxImageIdDagOrderMap[umboxImageId] = order;

        //set hidden form input to current map
        $("#orderFormInput").val(JSON.stringify(umboxImageIdDagOrderMap));

        $("#umboxImageOrderTableBody #removeButton" + currentCount).click(function () {
            $("#umboxImageOrderTableBody #umboxImageOrderTableRow" + currentCount).remove();

            //remove pairing from form data
            $("#orderFormInput #orderInput" +currentCount).remove()
        });
    }

    //fill device types in form
    $.get("/device-types", (types) => {
        $.each(JSON.parse(types), (id, type) => {
            deviceTypeIdToNameMap[type.id] = type.name;

            $("#umboxLookupContent #type").append("<option id='typeOption" + type.id + "' value='" + type.id + "'>" + type.name + "</option>");
        });


    });

    //fill security states in form
    $.get("/security-states", (securityStates) => {
        $.each(JSON.parse(securityStates), (id, securityState) => {
            stateIdToNameMap[securityState.id] = securityState.name;

            $("#umboxLookupContent #securityState").append("<option id='securityStateOption" + securityState.id + "' value='" + securityState.id + "'>"
                + securityState.name + "</option>");
        });
    });

    //fill umbox images in form
    $.get("/umbox-images", (umboxImages) => {
        $.each(JSON.parse(umboxImages), (id, umboxImage) => {
            umboxImageIDtoNameMap[umboxImage.id] = umboxImage.name;

            $("#umboxLookupContent #umboxImage").append("<option id='umboxImageOption" + umboxImage.id + "' value='" + umboxImage.id + "'>"
                + umboxImage.name + "</option>");
        });
    });

    $.get("/umbox-lookups", (umboxLookups) => {
        $.each(JSON.parse(umboxLookups), (index, umboxLookup) => {
            let newRow = "<tr id='tableRow" + umboxLookup.id + "'>\n" +
                "    <td class='fit'>" +
                "        <div class='editDeleteContainer' >" +
                "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + umboxLookup.id + "'>Edit</button>" +
                "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + umboxLookup.id + "'>Delete</button>" +
                "        </div>" +
                "    </td>\n" +
                "    <td id='deviceType" + umboxLookup.id + "'>" + deviceTypeIdToNameMap[umboxLookup.deviceTypeId] + "</td>\n" +
                "    <td id='securityState" + umboxLookup.id + "'>" + stateIdToNameMap[umboxLookup.stateId] + "</td>\n" +
                "    <td id='umboxImage" + umboxLookup.id + "'>" + umboxImageIDtoNameMap[umboxLookup.umboxImageId] + "</td>\n" +
                "    <td class='fit' id='order" + umboxLookup.id + "'>" + umboxLookup.dagOrder + "</td>\n" +
                "</tr>"
            umboxLookupTable.row.add($(newRow)).draw();

            umboxLookupTable.on("click", "#editButton" +umboxLookup.id, function () {
                $.post("/edit-umbox-lookup", {id: umboxLookup.id}, function () {
                    $('html, body').animate({scrollTop: 0}, 'fast', function () {});
                    $("#umboxLookupContent #submitFormButton").html("Update");
                    $("#umboxLookupContent #clearFormButton").html("Cancel Edit");
                    $("#umboxLookupContent .form-control#type").val("");
                    $("#umboxLookupContent .form-control#securityState").val("");
                    $("#umboxLookupContent .form-control#umboxImage").val("");
                    $("#umboxLookupContent form-control#order").val("");
                    $("#alertConditionContent #umboxImageOrderTable").find("tr:gt(0)").remove();   //remove all rows except header
                });
            });

            umboxLookupTable.on("click", "#deleteButton" +umboxLookup.id, function () {
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
            $("#umboxLookupContent .form-control#type").val("");
            $("#umboxLookupContent .form-control#securityState").val("");
            $("#umboxLookupContent .form-control#umboxImage").val("");
            $("#umboxLookupContent form-control#order").val("");
            $("#alertConditionContent #umboxImageOrderTable").find("tr:gt(0)").remove();   //remove all rows except header
        });
    });

    $("#umboxLookupContent #addOrderButton").click(function () {
        let umboxImageInput = $(".form-control#umboxImage");
        let orderInput = $(".form-control#order");

        addOrderRow(umboxImageInput.val(), orderInput.val());

        umboxImageInput.val("")
        orderInput.val(1);
    });
});