jQuery(document).ready(function($) {
    $("#groupId").append("<option>Example ID 1</option>\n" +
        "<option>Example ID 2</option>\n" +
        "<option>Example ID 3</option>");

    $("#type").append("<option>Example Type 1</option>\n" +
        "<option>Example Type 2</option>\n" +
        "<option>Example Type 3</option>");

    $("#tags").append("<div class=\"form-check col-2\">\n" +
        "    <input class=\"form-check-input\" type=\"checkbox\" id=\"tag1\" value=\"option1\">\n" +
        "    <label class=\"form-check-label\" for=\"tag1\">Tag 1</label>\n" +
        "</div>\n" +
        "<div class=\"form-check col-2\">\n" +
        "    <input class=\"form-check-input\" type=\"checkbox\" id=\"tag2\" value=\"option2\">\n" +
        "    <label class=\"form-check-label\" for=\"tag2\">Tag 2</label>\n" +
        "</div>\n" +
        "<div class=\"form-check col-2\">\n" +
        "    <input class=\"form-check-input\" type=\"checkbox\" id=\"tag3\" value=\"option3\">\n" +
        "    <label class=\"form-check-label\" for=\"tag3\">Tag 3</label>\n" +
        "</div>");

    $("#copyFromExisting").click(function() {
        console.log("Copy from Existing Device")
    });

    $("#newGroupId").click(function() {
        console.log("New Group ID")
    });

    $("#newType").click(function() {
        console.log("New Type")
    });

    $("#discoverIp").click(function() {
        console.log("Discover IP")
    });

    $("#newTag").click(function() {
        console.log("New Tag")
    });
});