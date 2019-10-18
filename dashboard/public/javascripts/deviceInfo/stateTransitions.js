let given_id_transitions = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    //list of found items on page load
    let foundImageLookups = [];
    let foundCommandLookups = [];

    //relationship from security state to list of images and commands
    let stateToImagesAndCommands = {};


    async function getImageLookups() {
        return $.get("/get-umbox-lookups-by-device", {id: given_id_transitions}, function (umboxLookups) {
            let arr = JSON.parse(umboxLookups);
            if (arr !== null && arr.length !== 0) {
                arr.forEach((lookup) => {
                    foundImageLookups.push(lookup);
                });
            }
        });
    }

    async function getCommandLookups() {
        return $.get("/get-command-lookups-by-device", {id: given_id_transitions}, function (commandLookups) {
            let arr = JSON.parse(commandLookups);
            if (arr !== null && arr.length !== 0) {
                arr.forEach((lookup) => {
                    foundCommandLookups.push(lookup);
                });
            }
        });
    }

    //given a umboxImageLookup, add a relationship from its security state to its image
    async function addToImageRelationship(lookup) {
        let foundState;

        //wait to get the security state before proceeding
        await $.get("/security-state", {id: lookup.stateId}, (state) => {
            foundState = JSON.parse(state);
        });

        return $.get("/umbox-image", {id: lookup.umboxImageId}, (image) => {
            image = JSON.parse(image);
            if (stateToImagesAndCommands[foundState.name] == null) {
                stateToImagesAndCommands[foundState.name] = {
                    images: [],
                    commands: []
                };
            }
            stateToImagesAndCommands[foundState.name].images.push(image.name);
        });
    }

    //given a commandLookup, add a relationship from its security state to its command
    async function addToCommandRelationship(lookup) {
        let foundState;

        //wait to get the security state before proceeding
        await $.get("/security-state", {id: lookup.stateId}, (state) => {
            foundState = JSON.parse(state);
        });

        return $.get("/command", {id: lookup.commandId}, (command) => {
            command = JSON.parse(command);
            if (stateToImagesAndCommands[foundState.name] == null) {
                stateToImagesAndCommands[foundState.name] = {
                    images: [],
                    commands: []
                };
            }
            stateToImagesAndCommands[foundState.name].commands.push(command.name);
        });
    }

    async function fillTable() {
        //wait to gather all imageLookups and commandLookups before proceeding
        await getImageLookups();
        await getCommandLookups();

        for (let index in foundImageLookups) {
            await addToImageRelationship(foundImageLookups[index]);
        }
        for (let index in foundCommandLookups) {
            await addToCommandRelationship(foundCommandLookups[index]);
        }

        Object.keys(stateToImagesAndCommands).forEach((state) => {
            let newRow = "<tr>" +
                "   <td>" + state + "</td>" +
                "   <td>" + stateToImagesAndCommands[state].images.join("<br>") + "</td>" +
                "   <td>" + stateToImagesAndCommands[state].commands.join("<br>") + "</td>" +
                "</tr>";

            $("#stateTransitionsTableBody").append($(newRow));
        });
    }

    fillTable();
});
