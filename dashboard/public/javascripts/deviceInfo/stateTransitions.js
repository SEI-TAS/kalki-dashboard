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
                foundImageLookups = arr;
                foundImageLookups.forEach((imageLookup) => {
                   $.get("/umbox-image", {id: imageLookup.umboxImageId}, (umb) => {
                      let umbox = JSON.parse(umb);
                      imageLookup.name = umbox.name;
                   });
                });
            }
        });
    }

    async function getCommandLookups() {
        return $.get("/get-command-lookups-by-device", {id: given_id_transitions}, function (commandLookups) {
            let arr = JSON.parse(commandLookups);
            if (arr !== null && arr.length !== 0) {
                foundCommandLookups = arr;
                foundCommandLookups.forEach((commandLookup) => {
                    $.get("/command", {id:commandLookup.commandId}, (comm) =>{
                        let command = JSON.parse(comm);
                        commandLookup.name = command.name;
                    });
                })
            }
        });
    }

    //given a umboxImageLookup, add a relationship from its security state to its image
    function addToImageRelationship() {
        foundImageLookups.forEach((image) => {
            Object.keys(stateToImagesAndCommands[image.stateId]).forEach((previousState) => {
                stateToImagesAndCommands[image.stateId][previousState].images.push(image.name)
            });
        });
    }

    //given a commandLookup, add a relationship from its security state to its command
    function addToCommandRelationship() {
        foundCommandLookups.forEach((commandLookup) => {
            stateToImagesAndCommands[commandLookup.currentStateId][commandLookup.previousStateId].commands.push(commandLookup.name);
        });
    }

    async function configureStates() {
        //wait to get the security state before proceeding
        await $.get("/security-states", (states) => {
            let securityStates = JSON.parse(states);
            securityStates.forEach((currentState) => {
                if(stateToImagesAndCommands[currentState.id] == null) {
                    stateToImagesAndCommands[currentState.id] = {};
                    securityStates.forEach((previousState) => {
                        stateToImagesAndCommands[currentState.id][previousState.id] = {
                            currentState: currentState.name,
                            previousState: previousState.name,
                            images: [],
                            commands: []
                        };
                    });
                }
            });
        });
    }

    async function fillTable() {
        //wait to gather all imageLookups and commandLookups before proceeding
        await getImageLookups();
        await getCommandLookups();
        await configureStates();

        addToImageRelationship();
        addToCommandRelationship();

        Object.keys(stateToImagesAndCommands).forEach((currState) => {
            Object.keys(stateToImagesAndCommands[currState]).forEach((prevState) => {
                let state = stateToImagesAndCommands[currState][prevState];
                let newRow = "<tr>" +
                    "   <td>" + state.previousState + "</td>" +
                    "   <td>" + state.currentState + "</td>" +
                    "   <td>" + state.images.join("<br>") + "</td>" +
                    "   <td>" + state.commands.join("<br>") + "</td>" +
                    "</tr>";

                $("#stateTransitionsTableBody").append($(newRow));
            });

        });
    }

    fillTable();
});
