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
    let deviceTable = $('#deviceTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );


    async function getDevices() {
        //must wait on these functions to complete otherwise the mappings might not be set in time
        deviceTable.clear();

        $.get("/devices", (devices) => {
            $.each(JSON.parse(devices), (index, device) => {
                let deviceGroupName = device.group ? device.group.name : "None";

                let newRow = "<tr id='tableRow" + device.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + device.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + device.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td class='fit' id='deviceID" + device.id + "'>" + device.id + "</td>\n" +
                    "    <td id='name" + device.id + "'>" + device.name + "</td>\n" +
                    "    <td id='description" + device.id + "'>" + device.description + "</td>\n" +
                    "    <td id='deviceType" + device.id + "'>" + device.type.name + "</td>\n" +
                    "    <td id='group" + device.id + "'>" + deviceGroupName + "</td>\n" +
                    "    <td id='ipAddress" + device.id + "'>" + device.ip + "</td>\n" +
                    "    <td id='statusHistorySize" + device.id + "'>" + device.statusHistorySize + "</td>\n" +
                    "    <td id='samplingRate" + device.id + "'>" + device.samplingRate + "</td>\n" +
                    "    <td id='tags" + device.id + "'>" + tagIdsToNames(device.tagIds) + "</td>\n" +
                    "</tr>"

                deviceTable.row.add($(newRow)).draw();

                //add tagIds for this device to the map
                // deviceIdToTagIdsMap[device.id] = device.tagIds;

                //Add options in copy from existing device modal
                // $("#deviceContent .form-control#deviceSelect").append("<option id='deviceSelectOption" + device.id + "' value='" + device.id + "'>\n" +
                //     device.name + "</option>");



            });
        });
    }



    //populate device form with information from the desired device
    $("#copyFromDeviceModalForm").submit(function (e) {
        e.preventDefault();
        let deviceId = document.getElementById("deviceSelect").value;
        $.get("/device", {id: deviceId}, function (device) {
            populateForm(JSON.parse(device))
        });
        $("#copyFromDeviceModal").modal("hide");
    });

    //only load data when tab is active
    $('a[href="#DeviceContent"]').on('shown.bs.tab', function (e) {
        getDevices();
    });
});
