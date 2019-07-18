var id = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const infoPollInterval = 1 * 1000;

    function createTagString(tags) {
        let tagNames = [];

        if(tags != null) {
            tags.forEach((tag) => {
                tagNames = tagNames.concat(tag.name);
            });
        }

        return tagNames.join(", ");
    }

    async function getDevice() {
        $.get('/device', {"id": id}, (deviceString) => {
            let device = JSON.parse(deviceString);
            if(device === null) {
                console.log("Invalid ID");
            }
            else {
                $("#deviceInfoPage #name").text(device.name);
                $("#deviceInfoPage #description").text(device.description);
                $("#deviceInfoPage #type").text(device.type.name);
                if(device.group != null) {
                    $("#deviceInfoPage #group").text(device.group.name);
                } else {
                    $("#deviceInfoPage #group").text("N/A");
                }
                $("#deviceInfoPage #ipAddress").text(device.ip);
            }
        });
    }

    async function getSecurityState() {
        $.get("/device-security-state", { id: id }, function(stateHistory) {
            let deviceState = JSON.parse(stateHistory);
            if(deviceState !== null) {
                $("#deviceInfoPage #securityState").text(deviceState.name);
            }
        });
    }

    async function getTags() {
        $.get('/get-tags-by-device', {"id": id}, (tags) => {
            let tagString = createTagString(JSON.parse(tags));
            $("#deviceInfoPage #tags").text(tagString);
        });
    }

    function getNewStates() {
        $.get("/get-new-states", (states) => {
            let newStates = JSON.parse(states);
            if (newStates != null) {
                newStates.forEach((state) => {
                    if(state.deviceId == id) {
                        $("#deviceInfoPage #securityState").text(state.name);

                        $("#deviceInfoPage #securityState").addClass("updated");
                        setTimeout(function() {
                            $("#deviceInfoPage #securityState").removeClass("updated");
                        }, 3000);
                    }
                });
            }
        });
    }

    async function main() {
        await Promise.all([getDevice(), getSecurityState(), getTags()]);

        getNewStates();

        setInterval(function () {
            getNewStates();
        }, infoPollInterval);
    }

    $("#stateResetButton").click(() => {
        $.get('/state-reset', {"id": id}, function() {
            window.location.reload();
        });
    });

    main();
});
