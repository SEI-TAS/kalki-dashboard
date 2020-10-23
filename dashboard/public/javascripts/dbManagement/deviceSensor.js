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
    let deviceSensorTable = $('#deviceSensorTable').DataTable({
        order: [[1, 'asc']],
        columnDefs: [
            {"orderable": false, "targets": 0}
        ]
    });

    // Load the device sensors for the currently selected device type.
    async function getDeviceSensors() {
        deviceSensorTable.clear();
        deviceSensorTable.draw();

        $.get("/device-sensors-device-type/?id="+$("#sensorDeviceTypeIdHidden").val(), (sensors) => {
            $.each(JSON.parse(sensors), (index, sensor) => {
                let newRow = "<tr id='tableRow" + sensor.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer'>" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + sensor.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + sensor.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td class='fit' id='id" + sensor.id + "'>" + sensor.id + "</td>\n" +
                    "    <td id='name" + sensor.id + "'>" + sensor.name + "</td>\n" +
                    "</tr>";
                deviceSensorTable.row.add($(newRow)).draw();

                deviceSensorTable.on("click", "#editButton" +sensor.id, function () {
                    $('html, body').animate({ scrollTop: 0 }, 'fast', function () {});
                    $("#deviceSensorContent #dsSubmitFormButton").html("Update");
                    $("#deviceSensorContent #dsClearFormButton").html("Cancel Edit");
                    $("#deviceSensorContent .form-group #deviceSensorIdHidden").val(sensor.id);
                    $("#deviceSensorContent .form-group #dsName").val($("#deviceSensorTableBody #name" +sensor.id).html());
                });

                deviceSensorTable.on("click", "#deleteButton" +sensor.id, function () {
                    if(confirm("Are you sure you want to delete sensor " + sensor.name + "?") === true) {
                        $.post("/delete-device-sensor", {id: sensor.id}, function (isSuccess) {
                            if (isSuccess === "true") {
                                deviceSensorTable.row("#tableRow" + sensor.id).remove().draw();
                            } else {
                                alert("Delete was unsuccessful. Please check that another table entry " +
                                    "does not rely on this Device Sensor.");
                            }
                        });
                    }
                });
            });
        });
    }

    $("#deviceSensorContent #clearFormButton").click(function () {
        $("#deviceSensorContent #dsSubmitFormButton").html("Add");
        $("#deviceSensorContent #dsClearFormButton").html("Clear");
        $("#deviceSensorContent .form-group #deviceSensorIdHidden").val(0);
        $("#deviceSensorContent .form-group #dsName").val("");
    });

    // Reload sensors when selected device type changes.
    $("#sensorDeviceTypeIdHidden").change(function() {
        getDeviceSensors();
    });
});