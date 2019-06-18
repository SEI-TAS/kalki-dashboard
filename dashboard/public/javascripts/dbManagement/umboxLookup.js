jQuery(document).ready(($) => {
    let umboxLookupTable = $('#umboxLookupTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    let globalDeviceTypeAndStateOrderMap = {};
    let globalDeviceTypeAndStateImageMap = {};

    let currentUmboxImageIdDagOrderMap = {};

    let stateIdToNameMap = {};
    let stateNameToIdMap = {};

    let deviceTypeIdToNameMap = {};
    let deviceTypeNameToIdMap = {};

    let umboxImageIDtoNameMap = {};
    let umboxImageNameToIdMap = {};

    let rowCounter = 0;

    function addOrderRow(umboxImageId, order) {
        let currentCount = ++rowCounter;

        let newRow = "<tr id='umboxImageOrderTableRow" + currentCount + "'>\n" +
            "    <td class='fit'><button type='button' class='btn btn-primary btn-sm' id='removeButton" + currentCount + "'>Remove</button></td>" +
            "    <td id='umboxImage" + currentCount + "'>" + umboxImageIDtoNameMap[umboxImageId] + "</td>\n" +
            "    <td id='order" + currentCount + "'>" + order + "</td>\n" +
            "</tr>"

        $("#umboxImageOrderTable").find("tbody").append($(newRow));

        //add umbox to dag order relationship to map
        currentUmboxImageIdDagOrderMap[umboxImageId] = order;

        //set hidden form input to current map
        $("#orderFormInput").val(JSON.stringify(currentUmboxImageIdDagOrderMap));

        $("#umboxImageOrderTableBody #removeButton" + currentCount).click(function () {
            $("#umboxImageOrderTableBody #umboxImageOrderTableRow" + currentCount).remove();

            delete currentUmboxImageIdDagOrderMap[umboxImageId.toString()];
            console.log(currentUmboxImageIdDagOrderMap);
        });
    }

    function switchToEditForm() {
        $("#umboxImageOrderTable").hide();
        $("#addOrderButton").hide();

        $(".form-control#umboxImage").attr("name", "umboxImageId");
        $(".form-control#order").attr("name", "dagOrder");
    }

    function switchToInsertForm() {
        console.log("called");
        $("#umboxImageOrderTable").show();
        $("#addOrderButton").show();

        $(".form-control#umboxImage").removeAttr("name");
        $(".form-control#order").removeAttr("name");
    }

    //fill device types in form
    $.get("/device-types", (types) => {
        $.each(JSON.parse(types), (id, type) => {
            deviceTypeIdToNameMap[type.id] = type.name;
            deviceTypeNameToIdMap[type.name] = type.id;

            $("#umboxLookupContent #type").append("<option id='typeOption" + type.id + "' value='" + type.id + "'>" + type.name + "</option>");
        });
    });

    //fill security states in form
    $.get("/security-states", (securityStates) => {
        $.each(JSON.parse(securityStates), (id, securityState) => {
            stateIdToNameMap[securityState.id] = securityState.name;
            stateNameToIdMap[securityState.name] = securityState.id;

            $("#umboxLookupContent #securityState").append("<option id='securityStateOption" + securityState.id + "' value='" + securityState.id + "'>"
                + securityState.name + "</option>");
        });
    });

    //fill umbox images in form
    $.get("/umbox-images", (umboxImages) => {
        $.each(JSON.parse(umboxImages), (id, umboxImage) => {
            umboxImageIDtoNameMap[umboxImage.id] = umboxImage.name;
            umboxImageNameToIdMap[umboxImage.name] = umboxImage.id;

            $("#umboxLookupContent #umboxImage").append("<option id='umboxImageOption" + umboxImage.id + "' value='" + umboxImage.id + "'>"
                + umboxImage.name + "</option>");
        });
    });

    $.get("/umbox-lookups", (umboxLookups) => {
        $.each(JSON.parse(umboxLookups), (index, umboxLookup) => {
            let key = umboxLookup.deviceTypeId.toString() + umboxLookup.stateId.toString();

            if (globalDeviceTypeAndStateOrderMap[key] == null) {
                globalDeviceTypeAndStateOrderMap[key] = new Set();
            }
            globalDeviceTypeAndStateOrderMap[key].add(umboxLookup.dagOrder)

            if (globalDeviceTypeAndStateImageMap[key] == null) {
                globalDeviceTypeAndStateImageMap[key] = new Set();
            }
            globalDeviceTypeAndStateImageMap[key].add(umboxLookup.umboxImageId)

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

            umboxLookupTable.on("click", "#editButton" + umboxLookup.id, function () {
                $.post("/edit-umbox-lookup", {id: umboxLookup.id}, function () {
                    $('html, body').animate({scrollTop: 0}, 'fast', function () {
                    });
                    $("#umboxLookupContent #submitFormButton").html("Update");
                    $("#umboxLookupContent #clearFormButton").html("Cancel Edit");
                    $("#umboxLookupContent .form-control#type").val(deviceTypeNameToIdMap[$("#umboxLookupTable #deviceType" + umboxLookup.id).html()]);
                    $("#umboxLookupContent .form-control#securityState").val(stateNameToIdMap[$("#umboxLookupTable #securityState" + umboxLookup.id).html()]);
                    $("#umboxLookupContent .form-control#umboxImage").val(umboxImageNameToIdMap[$("#umboxLookupTable #umboxImage" + umboxLookup.id).html()]);
                    $("#umboxLookupContent form-control#order").val($("#umboxLookupTable #order" + umboxLookup.id).html());
                    $("#alertConditionContent #umboxImageOrderTable").find("tr:gt(0)").remove();   //remove all rows except header

                    switchToEditForm();
                });
            });

            umboxLookupTable.on("click", "#deleteButton" + umboxLookup.id, function () {
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
            $("#umboxLookupContent form-control#order").val($(""));
            $("#umboxLookupContent #umboxImageOrderTable").find("tr:gt(0)").remove();   //remove all rows except header

            currentUmboxImageIdDagOrderMap = {};

            switchToInsertForm();
        });
    });

    $("#umboxLookupContent #addOrderButton").click(function () {
        let umboxImageInput = $(".form-control#umboxImage");
        let orderInput = $(".form-control#order");

        addOrderRow(umboxImageInput.val(), orderInput.val());

        umboxImageInput.val("")
        orderInput.val(1);
    });

    $('#umboxLookupContent form').on("submit", function () {
        let deviceTypeId = $("#umboxLookupContent .form-control#type").val();
        let stateId = $("#umboxLookupContent .form-control#securityState").val();
        let key = deviceTypeId.toString() + stateId.toString();

        let usedDagOrders = globalDeviceTypeAndStateOrderMap[key];
        let usedImageIds = globalDeviceTypeAndStateImageMap[key];

        let retVal = true;

        console.log(usedImageIds);

        // check to ensure that orders are not repeated for the current device type and state
        if (usedDagOrders) {
            Object.keys(currentUmboxImageIdDagOrderMap).forEach((umboxImageId) => {
                if (usedImageIds.has(parseInt(umboxImageId))) {
                    alert("cannot duplicate image");
                    retVal = false;
                }
                if (usedDagOrders.has(parseInt(currentUmboxImageIdDagOrderMap[umboxImageId]))) {
                    alert("cannot duplicate dag order");
                    retVal = false;
                }
            });
        }

        return retVal;
    });
});