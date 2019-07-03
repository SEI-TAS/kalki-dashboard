let given_id_transitions = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const timeFormat = "MMM Do YY, h:mm:ss a";

    let foundImageLookups = [];
    let foundCommandLookups = [];

    let stateToImage = {};
    let stateToCommand = {};

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

    async function addToImageRelationship(lookup) {
        let foundState;
        await $.get("/security-state", {id: lookup.stateId}, (state) => {
            foundState = state;
        });

        return $.get("/umbox-image", {id: lookup.imageId}, (image) => {
            if (stateToImage[foundState.name] == null) {
                stateToImage[foundState.name] = [];
            }
            stateToImage[foundState.name].push(image.name);
        });
    }

    async function addToCommandRelationship(lookup) {
        let foundState;
        await $.get("/security-state", {id: lookup.stateId}, (state) => {
            foundState = state;
        });

        return $.get("/command", {id: lookup.commandId}, (command) => {
            if (stateToCommand[foundState.name] == null) {
                stateToCommand[foundState.name] = [];
            }
            stateToCommand[foundState.name].push(command.name);
        });
    }

    async function fillTable() {
        await getImageLookups();
        await getCommandLookups();

        for(let lookup in foundImageLookups) {
            await addToImageRelationship(lookup);
        }
        for(let lookup in foundCommandLookups) {
            await addToCommandRelationship(lookup);
        }

        //TODO: combine the relationships

    }
});
