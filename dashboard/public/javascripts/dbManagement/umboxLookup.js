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

    let editing = false;
    let editingDagOrder = null;
    let editingUmboxImageId = null;

    let stateIdToNameMap = {};
    let stateNameToIdMap = {};

    let deviceTypeIdToNameMap = {};
    let deviceTypeNameToIdMap = {};

    let umboxImageIDtoNameMap = {};
    let umboxImageNameToIdMap = {};

    let rowCounter = 0;

    function addOrderRow(umboxImageId, order) {
        //make sure the imageId is not null
        if(umboxImageId == null) {
            alert("please select a valid umbox image");
            return false;
        }

        let hasDuplicate = false;
        Object.keys(currentUmboxImageIdDagOrderMap).forEach((uid) => {
            let dagOrder = parseInt(currentUmboxImageIdDagOrderMap[uid]);
            let imageId = parseInt(uid);

            if (umboxImageId == imageId) {
                alert("cannot add duplicate image");
                hasDuplicate = true;
            }
            if (dagOrder == order) {
                alert("cannot add duplicate dag order");
                hasDuplicate = true;
            }
        });

        if (hasDuplicate) {
            return false;
        } else {
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
        return true;
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
    async function getDeviceTypes() {
        $("#umboxLookupContent #type").empty();

        return $.get("/device-types", (types) => {
            $.each(JSON.parse(types), (id, type) => {
                deviceTypeIdToNameMap[type.id] = type.name;
                deviceTypeNameToIdMap[type.name] = type.id;

                $("#umboxLookupContent #type").append("<option id='typeOption" + type.id + "' value='" + type.id + "'>" + type.name + "</option>");
            });
        });
    }

    //fill security states in form
    async function getSecurityStates() {
        $("#umboxLookupContent #securityState").empty();

        return $.get("/security-states", (securityStates) => {
            $.each(JSON.parse(securityStates), (id, securityState) => {
                stateIdToNameMap[securityState.id] = securityState.name;
                stateNameToIdMap[securityState.name] = securityState.id;

                $("#umboxLookupContent #securityState").append("<option id='securityStateOption" + securityState.id + "' value='" + securityState.id + "'>"
                    + securityState.name + "</option>");
            });
        });
    }

    //fill umbox images in form
    async function getUmboxImages() {
        $("#umboxLookupContent #umboxImage").empty();

        return $.get("/umbox-images", (umboxImages) => {
            $.each(JSON.parse(umboxImages), (id, umboxImage) => {
                umboxImageIDtoNameMap[umboxImage.id] = umboxImage.name;
                umboxImageNameToIdMap[umboxImage.name] = umboxImage.id;

                $("#umboxLookupContent #umboxImage").append("<option id='umboxImageOption" + umboxImage.id + "' value='" + umboxImage.id + "'>"
                    + umboxImage.name + "</option>");
            });
        });
    }

    async function getUmboxLookups() {
        await getDeviceTypes();
        await getSecurityStates();
        await getUmboxImages();

        umboxLookupTable.clear();

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
                    editing = true;

                    $.post("/edit-umbox-lookup", {id: umboxLookup.id}, function () {
                        let dagOrder = parseInt($("#umboxLookupTable #order" + umboxLookup.id).html());
                        let umboxImageId = umboxImageNameToIdMap[$("#umboxLookupTable #umboxImage" + umboxLookup.id).html()];

                        $('html, body').animate({scrollTop: 0}, 'fast', function () {
                        });
                        $("#umboxLookupContent #submitFormButton").html("Update");
                        $("#umboxLookupContent #clearFormButton").html("Cancel Edit");
                        $("#umboxLookupContent .form-control#type").val(deviceTypeNameToIdMap[$("#umboxLookupTable #deviceType" + umboxLookup.id).html()]);
                        $("#umboxLookupContent .form-control#securityState").val(stateNameToIdMap[$("#umboxLookupTable #securityState" + umboxLookup.id).html()]);
                        $("#umboxLookupContent .form-control#umboxImage").val(umboxImageId);
                        $("#umboxLookupContent .form-control#order").val(dagOrder);
                        $("#alertConditionContent #umboxImageOrderTable").find("tr:gt(0)").remove();   //remove all rows except header

                        editingDagOrder = dagOrder;
                        editingUmboxImageId = umboxImageId;

                        switchToEditForm();
                    });
                });

                umboxLookupTable.on("click", "#deleteButton" + umboxLookup.id, function () {
                    $.post("/delete-umbox-lookup", {id: umboxLookup.id}, function (isSuccess) {
                        if (isSuccess == "true") {
                            umboxLookupTable.row("#tableRow" + umboxLookup.id).remove().draw();
                        } else {
                            alert("Delete was unsuccessful.  Please check that another table entry " +
                                "does not rely on this Umbox Lookup");
                        }
                    });
                });
            });
        });
    }

    $("#umboxLookupContent #clearFormButton").click(function () {
        editing = false;

        let typeSelect = $("#umboxLookupContent .form-control#type");
        let securityStateSelect = $("#umboxLookupContent .form-control#securityState");
        let umboxImageSelect = $("#umboxLookupContent .form-control#umboxImage");

        $.post("/clear-umbox-lookup-form", {}, function () {
            $("#umboxLookupContent #submitFormButton").html("Add");
            $("#umboxLookupContent #clearFormButton").html("Clear");
            typeSelect.val(typeSelect.find("option:first").val());
            securityStateSelect.val(securityStateSelect.find("option:first").val());
            umboxImageSelect.val(umboxImageSelect.find("option:first").val());
            $("#umboxLookupContent .form-control#order").val(1);
            $("#umboxLookupContent #umboxImageOrderTable").find("tr:gt(0)").remove();   //remove all rows except header

            currentUmboxImageIdDagOrderMap = {};
            editingDagOrder = null;
            editingUmboxImageId = null;

            switchToInsertForm();
        });
    });

    $("#umboxLookupContent #addOrderButton").click(function () {
        let umboxImageInput = $(".form-control#umboxImage");
        let orderInput = $(".form-control#order");

        if (addOrderRow(umboxImageInput.val(), orderInput.val())) { //if the add was successful
            umboxImageInput.val(umboxImageInput.find("option:first").val());
            orderInput.val(1);
        }
    });

    //before submitting, ensure that an image or a dag order is not being repeated for the
    //device type and state compared to what is already in the database
    $('#umboxLookupContent form').on("submit", function () {
        let deviceTypeId = $("#umboxLookupContent .form-control#type").val();
        let stateId = $("#umboxLookupContent .form-control#securityState").val();
        let key = deviceTypeId.toString() + stateId.toString();

        let usedDagOrders = globalDeviceTypeAndStateOrderMap[key];
        let usedImageIds = globalDeviceTypeAndStateImageMap[key];

        let areDupImgIds = false;
        let areDupOrders = false;

        let retVal = true;

        if (editing) {
            let newImageId = parseInt($("#umboxLookupContent .form-control#umboxImage").val());
            let newDagOrder = parseInt($("#umboxLookupContent .form-control#order").val());

            currentUmboxImageIdDagOrderMap = {};
            currentUmboxImageIdDagOrderMap[newImageId] = newDagOrder
        }

        // check to ensure that orders are not repeated for the current device type and state
        if (usedDagOrders) {
            let duplicateImageIds = new Set();
            let duplicateOrders = new Set();
            Object.keys(currentUmboxImageIdDagOrderMap).forEach((umboxImageId) => {
                let dagOrder = parseInt(currentUmboxImageIdDagOrderMap[umboxImageId]);
                let imageId = parseInt(umboxImageId);

                if (usedImageIds.has(imageId) && imageId != editingUmboxImageId) {
                    duplicateImageIds.add(imageId);
                    areDupImgIds = true;
                }
                if (usedDagOrders.has(dagOrder) && dagOrder != editingDagOrder) {
                    duplicateOrders.add(dagOrder);
                    areDupOrders = true;
                }
            });

            retVal = !(areDupImgIds || areDupOrders);
            console.log(retVal);

            if (retVal == false) {
                let alertMessage = "Error adding umboxLookup:\n";
                if (areDupImgIds) {
                    alertMessage += "\nPlease remove duplicate umboxImages:\n";
                    duplicateImageIds.forEach((imageId) => {
                        alertMessage += "" + umboxImageIDtoNameMap[imageId] + "\n";
                    });
                }
                if (areDupOrders) {
                    alertMessage += "\nPlease remove duplicate orders:\n";
                    duplicateOrders.forEach((order) => {
                        alertMessage += "" + order + "\n";
                    });
                }
                alert(alertMessage);
            }
        }

        return retVal;
    });

    //only load data when tab is active
    $('a[href="#UmboxLookupContent"]').on('shown.bs.tab', function (e) {
        getUmboxLookups();
    });
});