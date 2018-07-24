jQuery(document).ready(($)  => {
    $.get("/types", (types) => {
        $.each(JSON.parse(types), (id,type) => {
            $("#type").append("<option id='typeOption" + type.id + "' value='" + type.id + "'>" + type.name + "</option>");
        });
    });

    $.get("/groups", (groups) => {
        $.each(JSON.parse(groups), (id,group) => {
            $("#group").append("<option id='groupOption" + group.id + "' value='" + group.id + "'>" + group.name + "</option>");
        });
        $("#group").append("<option value='-1' hidden></option>");
    });

    $.get("/tags", (tags) => {
        $.each(JSON.parse(tags), (id,tag) => {
            $("#tags").append("<div class='form-check col-2'>\n" +
                "    <input class='form-check-input' type='checkbox' id='tagCheckBox" + tag.id + "' value='" + tag.id + "'>\n" +
                "    <label class='form-check-label' for='tagCheckBox" + tag.id + "'>" + tag.name + "</label>\n" +
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