jQuery(document).ready(($)  => {
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