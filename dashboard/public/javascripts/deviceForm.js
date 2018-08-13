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
                "    <input class='form-check-input' type='checkbox' id='tagCheckbox" + tag.id + "' name='tagIds[]' value='" + tag.id + "'>\n" +
                "    <label class='form-check-label' for='tagCheckBox" + tag.id + "'>" + tag.name + "</label>\n" +
                "</div>");
        });
    });

    $("#newTypeModalForm").submit(function(e) {
        e.preventDefault();
        let typeName = document.getElementById("newTypeInput").value;
        $.post("/add-type", { type: typeName }, function(typeId) {
            $("#newTypeModal").modal('hide');
            $("#type").append("<option id='typeOption" + typeId + "' value='" + typeId + "' selected>" + typeName + "</option>");
            document.getElementById("newTypeInput").value = "";
        });
    });

    $("#newGroupModalForm").submit(function(e) {
        e.preventDefault();
        let groupName = document.getElementById("newGroupInput").value;
        $.post("/add-group", { group: groupName }, function(groupId) {
            $("#newGroupModal").modal('hide');
            $("#group").append("<option id='groupOption" + groupId + "' value='" + groupId + "' selected>" + groupName + "</option>");
            document.getElementById("newGroupInput").value = "";
        });
    });

    $("#newTagModalForm").submit(function(e) {
        e.preventDefault();
        let tagName = document.getElementById("newTagInput").value;
        $.post("/add-tag", { tag: tagName }, function(tagId) {
            $("#newTagModal").modal('hide');
            $("#tags").append("<div class='form-check col-2'>\n" +
                "    <input class='form-check-input' type='checkbox' id='tagCheckBox" + tagId + "' name='tagIds[]' value='" + tagId + "' checked>\n" +
                "    <label class='form-check-label' for='tagCheckBox" + tagId + "'>" + tagName + "</label>\n" +
                "</div>");
            document.getElementById("newTagInput").value = "";
        });
    });

    $("#copyFromExisting").click(() => {
        console.log("Copy from Existing Device")
    });

    $("#discoverIp").click(() => {
        console.log("Discover IP")
    });

    $("#policyFile").change(function () {
        document.getElementById("policyFileLabel").innerHTML = this.value.replace(/C:\\fakepath\\/i, '');
    });

});