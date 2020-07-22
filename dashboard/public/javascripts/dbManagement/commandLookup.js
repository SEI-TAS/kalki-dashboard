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

jQuery(document).ready(($) => {
    let commandLookupTable = $('#commandLookupTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    //mappings from id to name to avoid uneccesary database calls
    let stateIDtoNameMap = {};
    let stateNameToIDMap = {};
    let commandNameToIDMap = {};
    let commandIdToNameMap = {};
    let deviceTypeIDtoNameMap = {};
    let deviceTypeNametoIDMap = {};

    async function getSecurityStates() {
        $(".form-control#securityStateSelect").empty();

        return $.get("/security-states", (securityStates) => {
            $.each(JSON.parse(securityStates), (id, state) => {
                $(".form-control#currentSecurityStateSelect").append("<option id='currentSecurityStateOption" + state.id + "' value='" + state.id + "'>"
                    + state.name +
                    "</option>");
                $(".form-control#previousSecurityStateSelect").append("<option id='previousSecurityStateOption" + state.id + "' value='" + state.id + "'>"
                    + state.name +
                    "</option>");
                stateIDtoNameMap[state.id] = state.name;
                stateNameToIDMap[state.name] = state.id;
            });
        });
    }

    async function getDeviceTypes() {
        $(".form-control#deviceTypeSelect").empty();

        return $.get("/device-types", (deviceTypes) => {
            $.each(JSON.parse(deviceTypes), (id, type) => {
                $(".form-control#deviceTypeSelect").append("<option id='deviceTypeOption" + type.id + "' value='" + type.id + "'>"
                    + type.name +
                    "</option>");
                deviceTypeIDtoNameMap[type.id] = type.name;
                deviceTypeNametoIDMap[type.name] = type.id;
            });

        });
    }

    async function getDeviceCommands() {
        $(".form-control#deviceCommandSelect").empty();

        return $.get("/commands", (commands) => {
            $.each(JSON.parse(commands), (id, command) => {
                let name = deviceTypeIDtoNameMap[command.deviceTypeId]+": "+command.name;
                commandNameToIDMap[name] = command.id;
                commandIdToNameMap[command.id] = name;

                $(".form-control#deviceCommandSelect").append("<option id='deviceCommandOption" + command.id + "' value='" + command.id + "'>"
                    + name + "</option>");
            });

            //set hidden deviceCommandName to the value of the deviceCommandSelect
            $("input#deviceCommandName").val($(".form-control#deviceCommandSelect").text());
        });
    }

    async function getCommandLookups() {
        //must wait on these functions to complete to ensure the mappings are present
        await getDeviceTypes();
        await getSecurityStates();
        await getDeviceCommands();

        commandLookupTable.clear();

        $.get("/command-lookups", (commandLookups) => {
            $.each(JSON.parse(commandLookups), (index, commandLookup) => {

                let newRow = "<tr id='tableRow" + commandLookup.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + commandLookup.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + commandLookup.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td id='id" + commandLookup.id + "'>" + commandLookup.id + "</td>\n" +
                    "    <td id='currentSecurityState" + commandLookup.id + "'>" + stateIDtoNameMap[commandLookup.currentStateId] + "</td>\n" +
                    "    <td id='previousSecurityState" + commandLookup.id + "'>" + stateIDtoNameMap[commandLookup.previousStateId] + "</td>\n" +
                    "    <td id='deviceType" + commandLookup.id + "'>" + deviceTypeIDtoNameMap[commandLookup.deviceTypeId] + "</td>\n" +
                    "    <td id='deviceCommand" + commandLookup.id + "'>" + commandIdToNameMap[commandLookup.commandId] + "</td>\n" +
                    "</tr>"
                commandLookupTable.row.add($(newRow)).draw();

                commandLookupTable.on("click", "#editButton" + commandLookup.id, function () {
                    $.post("/edit-command-lookup", {id: commandLookup.id}, function () {
                        let currentSecurityState = $("#commandLookupTableBody #currentSecurityState" + commandLookup.id).html();
                        let previousSecurityState = $("#commandLookupTableBody #previousSecurityState" + commandLookup.id).html();
                        let deviceType = $("#commandLookupTableBody #deviceType"+commandLookup.id).html();
                        let deviceCommandName = $("#commandLookupTableBody #deviceCommand" + commandLookup.id).html();

                        $('html, body').animate({scrollTop: 0}, 'fast', function () {
                        });
                        $("#commandLookupContent #submitClFormButton").html("Update");
                        $("#commandLookupContent #clearClFormButton").html("Cancel Edit");
                        $("#commandLookupContent .form-control#currentSecurityStateSelect").val(stateNameToIDMap[currentSecurityState]).change();
                        $("#commandLookupContent .form-control#previousSecurityStateSelect").val(stateNameToIDMap[previousSecurityState]).change();
                        $("#commandLookupContent .form-control#deviceTypeSelect").val(deviceTypeNametoIDMap[deviceType]).change();
                        $("#commandLookupContent .form-control#deviceCommandSelect").val(commandNameToIDMap[deviceCommandName]).change();
                    });
                });

                commandLookupTable.on("click", "#deleteButton" + commandLookup.id, function () {
                    $.post("/delete-command-lookup", {id: commandLookup.id}, function (isSuccess) {
                        if (isSuccess == "true") {
                            commandLookupTable.row("#tableRow" + commandLookup.id).remove().draw();
                        } else {
                            alert("Delete was unsuccessful.  Please check that another table entry " +
                                "does not rely on this Command Lookup");
                        }
                    });
                });
            });
        });
    }

    $("#commandLookupContent #clearClFormButton").click(function () {
        let currentSecurityStateSelect = $("#commandLookupContent .form-control#currentSecurityStateSelect");
        let previousSecurityStateSelect = $("#commandLookupContent .form-control#previousSecurityStateSelect");
        let deviceTypeSelect = $("#commandLookupContent .form-control#deviceTypeSelect");
        let deviceCommandSelect = $("#commandLookupContent .form-control#deviceCommandSelect");

        $.post("/clear-command-lookup-form", {}, function () {
            $("#commandLookupContent #submitClFormButton").html("Add");
            $("#commandLookuContent #clearClFormButton").html("Clear");
            currentSecurityStateSelect.val(currentSecurityStateSelect.find("option:first").val()).change();
            previousSecurityStateSelect.val(previousSecurityStateSelect.find("option:first").val()).change();
            deviceTypeSelect.val(deviceTypeSelect.find("option:first").val()).change();
            deviceCommandSelect.val(deviceCommandSelect.find("option:first").val()).change();
            $("#commandLookupContent #deviceCommandName").val($(".form-control#deviceCommandSelect").text());
        });
    });

    //only load data when tab is active
    $('a[href="#CommandLookupContent"]').on('shown.bs.tab', function (e) {
        getCommandLookups();
    });
});
