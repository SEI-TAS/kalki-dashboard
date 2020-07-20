/*
 * Kalki - A Software-Defined IoT Security Platform
 * Copyright 2020 Carnegie Mellon University.
 * NO WARRANTY. THIS CARNEGIE MELLON UNIVERSITY AND SOFTWARE ENGINEERING INSTITUTE MATERIAL IS FURNISHED ON AN "AS-IS" BASIS. CARNEGIE MELLON UNIVERSITY MAKES NO WARRANTIES OF ANY KIND, EITHER EXPRESSED OR IMPLIED, AS TO ANY MATTER INCLUDING, BUT NOT LIMITED TO, WARRANTY OF FITNESS FOR PURPOSE OR MERCHANTABILITY, EXCLUSIVITY, OR RESULTS OBTAINED FROM USE OF THE MATERIAL. CARNEGIE MELLON UNIVERSITY DOES NOT MAKE ANY WARRANTY OF ANY KIND WITH RESPECT TO FREEDOM FROM PATENT, TRADEMARK, OR COPYRIGHT INFRINGEMENT.
 * Released under a MIT (SEI)-style license, please see license.txt or contact permission@sei.cmu.edu for full terms.
 * [DISTRIBUTION STATEMENT A] This material has been approved for public release and unlimited distribution.  Please see Copyright notice for non-US Government use and distribution.
 * This Software includes and/or makes use of the following Third-Party Software subject to its own license:
 * 1. Google Guava (https://github.com/google/guava) Copyright 2007 The Guava Authors.
 * 2. JSON.simple (https://code.google.com/archive/p/json-simple/) Copyright 2006-2009 Yidong Fang, Chris Nokleberg.
 * 3. JUnit (https://junit.org/junit5/docs/5.0.1/api/overview-summary.html) Copyright 2020 The JUnit Team.
 * 4. Play Framework (https://www.playframework.com/) Copyright 2020 Lightbend Inc..
 * 5. PostgreSQL (https://opensource.org/licenses/postgresql) Copyright 1996-2020 The PostgreSQL Global Development Group.
 * 6. Jackson (https://github.com/FasterXML/jackson-core) Copyright 2013 FasterXML.
 * 7. JSON (https://www.json.org/license.html) Copyright 2002 JSON.org.
 * 8. Apache Commons (https://commons.apache.org/) Copyright 2004 The Apache Software Foundation.
 * 9. RuleBook (https://github.com/deliveredtechnologies/rulebook/blob/develop/LICENSE.txt) Copyright 2020 Delivered Technologies.
 * 10. SLF4J (http://www.slf4j.org/license.html) Copyright 2004-2017 QOS.ch.
 * 11. Eclipse Jetty (https://www.eclipse.org/jetty/licenses.html) Copyright 1995-2020 Mort Bay Consulting Pty Ltd and others..
 * 12. Mockito (https://github.com/mockito/mockito/wiki/License) Copyright 2007 Mockito contributors.
 * 13. SubEtha SMTP (https://github.com/voodoodyne/subethasmtp) Copyright 2006-2007 SubEthaMail.org.
 * 14. JSch - Java Secure Channel (http://www.jcraft.com/jsch/) Copyright 2002-2015 Atsuhiko Yamanaka, JCraft,Inc. .
 * 15. ouimeaux (https://github.com/iancmcc/ouimeaux) Copyright 2014 Ian McCracken.
 * 16. Flask (https://github.com/pallets/flask) Copyright 2010 Pallets.
 * 17. Flask-RESTful (https://github.com/flask-restful/flask-restful) Copyright 2013 Twilio, Inc..
 * 18. libvirt-python (https://github.com/libvirt/libvirt-python) Copyright 2016 RedHat, Fedora project.
 * 19. Requests: HTTP for Humans (https://github.com/psf/requests) Copyright 2019 Kenneth Reitz.
 * 20. netifaces (https://github.com/al45tair/netifaces) Copyright 2007-2018 Alastair Houghton.
 * 21. ipaddress (https://github.com/phihag/ipaddress) Copyright 2001-2014 Python Software Foundation.
 * DM20-0543
 *
 */

let given_id_conditions = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    let alertConditionTable = $('#alertConditionTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    //map of characters we wish to escape before placing directly into HTML
    let entityMap = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#39;',
        '/': '&#x2F;',
        '`': '&#x60;',
        '=': '&#x3D;'
    };

    let conditionIdToDeviceIdMap = {};
    let conditionIdToLookupIdMap = {};

    //a counter that will give each new variable a unique id
    let variableCounter = 0;

    function escapeHtml (string) {
        return String(string).replace(/[&<>"'`=\/]/g, function (s) {
            return entityMap[s];
        });
    }

    //construct a string from a variable map so that it can be displayed in the table
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

    //add a row to the top form for a new variable mapping
    function addVariableRow(key, value) {
        variableCounter++;
        let currentCount = variableCounter;

        let newRow = "<tr id='variableTableRow" + currentCount + "'>\n" +
            "    <td class='fit'><button type='button' class='btn btn-primary btn-sm' id='removeButton" + currentCount + "'>Remove</button></td>" +
            "    <td id='key" + currentCount + "'>" + key + "</td>\n" +
            "    <td id='value" + currentCount + "'>" + value + "</td>\n" +
            "</tr>"

        $("#editVariablesModal #variableTable").find("tbody").append($(newRow));

        //add pairing to form data
        $("#editVariablesModal #variableFormInput").append("<input type='text' id='variableInput" + currentCount + "' " +
            "name='variables[" + escapeHtml(key) + "]' value='" + escapeHtml(value) + "' hidden>");
        $("#editVariablesModal #variableTableBody #removeButton" + currentCount).click(function () {
            $("#editVariablesModal #variableTableBody #variableTableRow" + currentCount).remove();

            //remove pairing from form data
            $("#editVariablesModal #variableFormInput #variableInput" + currentCount).remove()
        });
    }

    //when editing an alert condition, populate the form with each of the variables from that table row
    function populateVariablesTableFromString(variablesString) {
        if (variablesString != "") {
            let variablesArray = variablesString.split("<br>");

            variablesArray.forEach(variableString => {
                let colonIndex = variableString.indexOf(":");
                let key = variableString.substring(0, colonIndex);
                let value = variableString.substring(colonIndex + 2, variableString.length);

                addVariableRow(key, value);
            });
        }
    }

    //retrieve all alert conditions and populate the table
    function getAllAlertConditions() {
        alertConditionTable.clear();

        $.get("/get-alert-conditions-by-device",{id:given_id_conditions}, (alertConditions) => {
            let conditions = JSON.parse(alertConditions);

            $.each(conditions, (index, alertCondition) => {
                conditionIdToDeviceIdMap[alertCondition.id] = alertCondition.deviceId;
                conditionIdToLookupIdMap[alertCondition.id] = alertCondition.alertTypeLookupId;

                let newRow = "<tr id='tableRow" + alertCondition.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + alertCondition.id + "'"+
                    "                   role='button' data-toggle='modal' data-target='#editVariablesModal'>Edit</button> "+
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + alertCondition.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td id='device" + alertCondition.id + "'>" + alertCondition.deviceName + "</td>\n" +
                    "    <td id='alertType" + alertCondition.id + "'>" + alertCondition.alertTypeName + "</td>\n" +
                    "    <td class='fit' id='variables" + alertCondition.id + "'>" + makeVariablesString(alertCondition.variables) + "</td>\n" +
                    "</tr>"
                alertConditionTable.row.add($(newRow)).draw();

                alertConditionTable.on("click", "#editButton" + alertCondition.id, function () {
                    $("#alertConditionContent #variableFormInput").append("<input type='text' id='deviceIdInput' name='deviceId' " +
                        "value='" + escapeHtml(conditionIdToDeviceIdMap[alertCondition.id]) + "' hidden>");
                    $("#alertConditionContent #variableFormInput").append("<input type='text' id='alertTypeLookupIdInput' name='alertTypeLookupId' " +
                        "value='" + escapeHtml(conditionIdToLookupIdMap[alertCondition.id]) + "' hidden>");
                    $("#alertConditionContent #variableTableBody").empty();
                    populateVariablesTableFromString($("#alertConditionTableBody #variables" + alertCondition.id).html());
                });

                alertConditionTable.on("click", "#deleteButton" + alertCondition.id, function () {
                    $.post("/delete-alert-condition", {id: alertCondition.id}, function (isSuccess) {
                        if (isSuccess == "true") {
                            alertConditionTable.row("#tableRow" + alertCondition.id).remove().draw();
                        } else {
                            alert("Delete was unsuccessful.  Please check that another table entry " +
                                "does not rely on this Alert Condition");
                        }
                    });
                });
            });
        });
    }

    $("#alertConditionContent #addVariableButton").click(function () {
        let keyInput = $("#editVariablesModal .form-control#variableKey");
        let valueInput = $("#editVariablesModal .form-control#variableValue");

        addVariableRow(keyInput.val(), valueInput.val());

        keyInput.val("");
        valueInput.val("");
    });

    //only load content if the tab is active
    $('a[href="#deviceAlertConditionsContent"]').on('shown.bs.tab', function (e) {
        getAllAlertConditions();
    });
});
