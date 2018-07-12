var deviceId = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($)  => {
    $.get("/device", { id: deviceId }, (device) => {
        console.log(device);
        document.getElementById("name").value = device.name;
        document.getElementById("description").value = device.description;
        document.getElementById("ipAddress").value = device.ip_address;
        document.getElementById("historySize").value = device.history_size;
        document.getElementById("samplingRate").value = device.sampling_rate;
    });

    $.get("/group-ids", (groupIds) => {
        $.each(groupIds, (id,groupId) => {
            $("#groupId").append("<option>" + groupId.name + "</option>");
        });
    });

    // Default types
    ["Hue Light", "Dlink Camera", "WeMo Insight", "Udoo Neo"].forEach(type => {
        $("#type").append("<option>" + type + "</option>");
    });

    $.get("/types", (types) => {
        $.each(types, (id,type) => {
            $("#type").append("<option>" + type.name + "</option>");
        });
    });

    $.get("/tags", (tags) => {
        $.each(tags, (id,tag) => {
            $("#tags").append("<div class='form-check col-2'>\n" +
                "    <input class='form-check-input' type='checkbox' id='" + tag.id + "' value='" + tag.id + "'>\n" +
                "    <label class='form-check-label' for='" + tag.id + "'>" + tag.name + "</label>\n" +
                "</div>");
        });
    });

    $("#copyFromExisting").click(() => {
        console.log("Copy from Existing Device")
    });

    $("#discoverIp").click(() => {
        console.log("Discover IP")
    });

});