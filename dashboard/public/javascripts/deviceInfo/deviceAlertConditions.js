let given_id_conditions = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const timeFormat = "MMM Do YY, h:mm:ss a";

    $.fn.dataTable.moment(timeFormat);

    let conditionsTable = $('#deviceAlertConditionsTable').DataTable(
        {
            order: [[0, 'desc']],
            columnDefs: [
                {type: 'time-uni', targets: 0},
            ],
            language: {
                "emptyTable": "No alert conditions"
            }
        }
    );

    let foundAlertConditions = [];

    function makeVariablesString(variables) {
        let resultString = "";
        if (variables != null) {
            Object.keys(variables).forEach(key => {
                resultString = resultString + key + ": " + variables[key] + "<br>";
            });

            resultString = resultString.substring(0, resultString.length - 4); //remove the last <br>
        }

        return resultString;
    }

    async function getAlertConditions() {
        return $.get("/get-alert-conditions-by-device", { id: given_id_conditions }, function(alertConditions) {
            let arr = JSON.parse(alertConditions);
            if(arr !== null && arr.length !== 0) {

                arr.forEach(function(alertCondition) {
                    foundAlertConditions.push(alertCondition)

                });
            }
        });
    }

    async function fillTable() {
        await getAlertConditions();

        foundAlertConditions.forEach((alertCondition) => {
            $.get("/alert-type", {id: alertCondition.alertTypeId}, (alertType) => {
                alertType = JSON.parse(alertType);
                console.log(alertType);
                let newRow = "<tr>" +
                    "   <td>" + makeVariablesString(alertCondition.variables) + "</td>" +
                    "   <td>" + alertType.name + "</td>" +
                    "</tr>";
                conditionsTable.row.add($(newRow)).draw();
            });
        });
    }

    fillTable();
});
