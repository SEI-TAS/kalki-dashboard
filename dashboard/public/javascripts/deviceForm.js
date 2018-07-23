jQuery(document).ready(($)  => {
    $.get("/groups", (groups) => {
        $.each(JSON.parse(groups), (id,group) => {
            $("#group").append("<option>" + group.name + "</option>");
        });
    });

    $.get("/types", (types) => {
        $.each(JSON.parse(types), (id,type) => {
            $("#type").append("<option>" + type.name + "</option>");
        });
    });

    $.get("/tags", (tags) => {
        $.each(JSON.parse(tags), (id,tag) => {
            $("#tags").append("<div class='form-check col-2add.js'>\n" +
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