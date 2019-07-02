var id = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    function createTagString(tags) {
        let tagNames = [];

        if(tags != null) {
            tags.forEach((tag) => {
                tagNames = tagNames.concat(tag.name);
            });
        }

        return tagNames.join(", ");
    }

    $.get('/device', {"id": id}, (deviceString) => {
        let device = JSON.parse(deviceString);
        if(device === null) {
            console.log("Invalid ID");
        }
        else {
            $("#deviceInfoPage #name").text(device.name);
            $("#deviceInfoPage #type").text(device.type.name);
            if(device.group != null) {
                $("#deviceInfoPage #group").text(device.group.name);
            } else {
                $("#deviceInfoPage #group").text("N/A");
            }
        }

        $.get('/get-tags-by-device', {"id": id}, (tags) => {
           let tagString = createTagString(JSON.parse(tags));
            $("#deviceInfoPage #tags").text(tagString);
        });
    });

    $.get("/device-security-state", { id: id }, function(stateHistory) {
        let deviceState = JSON.parse(stateHistory);
        if(deviceState !== null) {
            $("#securityState").text(deviceState.name);
        }
    });
});
