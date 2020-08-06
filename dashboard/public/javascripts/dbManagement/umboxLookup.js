/*
 * Kalki - A Software-Defined IoT Security Platform
 * Copyright 2020 Carnegie Mellon University.
 * NO WARRANTY. THIS CARNEGIE MELLON UNIVERSITY AND SOFTWARE ENGINEERING INSTITUTE MATERIAL IS FURNISHED ON AN "AS-IS" BASIS. CARNEGIE MELLON UNIVERSITY MAKES NO WARRANTIES OF ANY KIND, EITHER EXPRESSED OR IMPLIED, AS TO ANY MATTER INCLUDING, BUT NOT LIMITED TO, WARRANTY OF FITNESS FOR PURPOSE OR MERCHANTABILITY, EXCLUSIVITY, OR RESULTS OBTAINED FROM USE OF THE MATERIAL. CARNEGIE MELLON UNIVERSITY DOES NOT MAKE ANY WARRANTY OF ANY KIND WITH RESPECT TO FREEDOM FROM PATENT, TRADEMARK, OR COPYRIGHT INFRINGEMENT.
 * Released under a MIT (SEI)-style license, please see license.txt or contact permission@sei.cmu.edu for full terms.
 * [DISTRIBUTION STATEMENT A] This material has been approved for public release and unlimited distribution.  Please see Copyright notice for non-US Government use and distribution.
 * This Software includes and/or makes use of the following Third-Party Software subject to its own license:
 * 1. Google Guava (https://github.com/google/guava) Copyright 2007 The Guava Authors.
 * 2. JSON.simple (https://code.google.com/archive/p/json-simple/) Copyright 2006-2009 Yidong Fang, Chris Nokleberg.
 * 3. JUnit (https://junit.org/junit5/docs/5.0.1/api/overview-summary.html) Copyright 2020 The JUnit Team.
 * 4. Play Framework (https://www.playframework.com/) Copyright 2020 Lightbend Inc..
 * 5. PostgreSQL (https://opensource.org/licenses/postgresql) Copyright 1996-2020 The PostgreSQL Global Development Group.
 * 6. Jackson (https://github.com/FasterXML/jackson-core) Copyright 2013 FasterXML.
 * 7. JSON (https://www.json.org/license.html) Copyright 2002 JSON.org.
 * 8. Apache Commons (https://commons.apache.org/) Copyright 2004 The Apache Software Foundation.
 * 9. RuleBook (https://github.com/deliveredtechnologies/rulebook/blob/develop/LICENSE.txt) Copyright 2020 Delivered Technologies.
 * 10. SLF4J (http://www.slf4j.org/license.html) Copyright 2004-2017 QOS.ch.
 * 11. Eclipse Jetty (https://www.eclipse.org/jetty/licenses.html) Copyright 1995-2020 Mort Bay Consulting Pty Ltd and others..
 * 12. Mockito (https://github.com/mockito/mockito/wiki/License) Copyright 2007 Mockito contributors.
 * 13. SubEtha SMTP (https://github.com/voodoodyne/subethasmtp) Copyright 2006-2007 SubEthaMail.org.
 * 14. JSch - Java Secure Channel (http://www.jcraft.com/jsch/) Copyright 2002-2015 Atsuhiko Yamanaka, JCraft,Inc. .
 * 15. ouimeaux (https://github.com/iancmcc/ouimeaux) Copyright 2014 Ian McCracken.
 * 16. Flask (https://github.com/pallets/flask) Copyright 2010 Pallets.
 * 17. Flask-RESTful (https://github.com/flask-restful/flask-restful) Copyright 2013 Twilio, Inc..
 * 18. libvirt-python (https://github.com/libvirt/libvirt-python) Copyright 2016 RedHat, Fedora project.
 * 19. Requests: HTTP for Humans (https://github.com/psf/requests) Copyright 2019 Kenneth Reitz.
 * 20. netifaces (https://github.com/al45tair/netifaces) Copyright 2007-2018 Alastair Houghton.
 * 21. ipaddress (https://github.com/phihag/ipaddress) Copyright 2001-2014 Python Software Foundation.
 * DM20-0543
 *
 */

jQuery(document).ready(($) => {
    let umboxLookupTable = $('#umboxLookupTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    let editing = false;
    let editingDagOrder = null;
    let editingUmboxImageId = null;

    //running maps from a device type and state combination that allow checking
    //for duplicates when inserting without making another DB call
    let globalDeviceTypeAndStateOrderMap = {};
    let globalDeviceTypeAndStateImageMap = {};

    //a map from image id to dag order for the current top form
    let currentUmboxImageIdDagOrderMap = {};

    //general mappings to save on database calls
    let stateIdToNameMap = {};
    let stateNameToIdMap = {};

    let deviceTypeIdToNameMap = {};
    let deviceTypeNameToIdMap = {};

    let umboxImageIDtoNameMap = {};
    let umboxImageNameToIdMap = {};

    //a counter to ensure that every row in the top form has a unique id
    let rowCounter = 0;

    //adds a dag order row to the top form
    function addOrderRow(umboxImageId, order) {
        //make sure the imageId is not null
        if(umboxImageId == null) {
            alert("please select a valid umbox image");
            return false;
        }

        let hasDuplicate = false;

        //ensure that there are no duplicate orders or image in the top form
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

            //set hidden form input to current map (needed to pass form data to controller)
            $("#orderFormInput").val(JSON.stringify(currentUmboxImageIdDagOrderMap));

            $("#umboxImageOrderTableBody #removeButton" + currentCount).click(function () {
                $("#umboxImageOrderTableBody #umboxImageOrderTableRow" + currentCount).remove();

                delete currentUmboxImageIdDagOrderMap[umboxImageId.toString()];
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
        //must wait for these functions to complete otherwise the mappings may not be set
        await getDeviceTypes();
        await getSecurityStates();
        await getUmboxImages();

        umboxLookupTable.clear();

        $.get("/umbox-lookups", (umboxLookups) => {
            $.each(JSON.parse(umboxLookups), (index, umboxLookup) => {
                let key = umboxLookup.deviceTypeId.toString() + umboxLookup.securityStateId.toString();

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
                    "    <td id='securityState" + umboxLookup.id + "'>" + stateIdToNameMap[umboxLookup.securityStateId] + "</td>\n" +
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
                        $("#umboxLookupContent #orderFormInput").val("{\"" + umboxImageId + "\":\"" + dagOrder + "\"}");
                        $("#umboxLookupContent #umboxImageOrderTable").find("tr:gt(0)").remove();   //remove all rows except header

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
            console.log("HA");
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

