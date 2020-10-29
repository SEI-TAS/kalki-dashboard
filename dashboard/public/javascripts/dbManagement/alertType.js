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
    let alertTypeTable = $('#alertTypeTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );

    //retrieve all alert types and populate the table
    function getAlertTypes() {
        alertTypeTable.clear();

        $.get("/alert-types", (alertTypes) => {
            $.each(JSON.parse(alertTypes), (index, alertType) => {
                let newRow = "<tr id='tableRow" + alertType.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + alertType.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + alertType.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td class='fit' id='alertTypeID" + alertType.id + "'>" + alertType.id + "</td>\n" +
                    "    <td id='name" + alertType.id + "'>" + alertType.name + "</td>\n" +
                    "    <td id='description" + alertType.id + "'>" + alertType.description + "</td>\n" +
                    "    <td id='source" + alertType.id + "'>" + alertType.source + "</td>\n" +
                    "</tr>"
                alertTypeTable.row.add($(newRow)).draw();

                alertTypeTable.on("click", "#editButton" + alertType.id, function () {
                    $('html, body').animate({scrollTop: 0}, 'fast', function () {});
                    $("#alertTypeContent #submitFormButton").html("Update");
                    $("#alertTypeContent #clearFormButton").html("Cancel Edit");
                    $("#alertTypeIdHidden").val(alertType.id);
                    $("#alertTypeName").val(alertType.name);
                    $("#alertTypeContent .form-group #description").val($("#alertTypeTableBody #description" + alertType.id).html());
                    $("#alertTypeContent .form-control#source").val($("#alertTypeTableBody #source" + alertType.id).html()).change();
                });

                alertTypeTable.on("click", "#deleteButton" + alertType.id, function () {
                    if(confirm("Are you sure you want to delete the Alert Type  " + alertType.name + "?") === true) {
                        $.post("/delete-alert-type", {id: alertType.id}, function (isSuccess) {
                            if (isSuccess == "true") {
                                alertTypeTable.row("#tableRow" + alertType.id).remove().draw();
                            } else {
                                alert("Delete was unsuccessful.  Please check that another table entry " +
                                    "does not rely on this Alert Type");
                            }

                        });
                    }
                });
            });
        });
    }

    $("#alertTypeContent #clearFormButton").click(function () {
        $("#alertTypeContent #submitFormButton").html("Add");
        $("#alertTypeContent #clearFormButton").html("Clear");
        $("#alertTypeIdHidden").val(0);
        $("#alertTypeContent .form-group #alertTypeName").val("");
        $("#alertTypeContent .form-group #description").val("");
        $("#alertTypeContent .form-control#source").val(
        $("#alertTypeContent .form-control#source option:first").val());
    });

    //only load data when tab is active
    $('a[href="#AlertTypeContent"]').on('shown.bs.tab', function (e) {
        getAlertTypes();
    });
});