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

let given_id_alert = document.currentScript.getAttribute('data-id');

jQuery(document).ready(($) => {
    const timeFormat = "MMM Do YY, h:mm:ss a";
    const infoPollInterval = 1 * 1000;
    let lowestStatusId = -1;

    $.fn.dataTable.moment(timeFormat);

    /*
        Alert History related
     */
    let alertHistoryTable = $('#alertHistoryTable').DataTable(
        {
            order: [[0, 'desc']],
            language: {
                "emptyTable": "No alert history"
            }
        }
    );

    async function getAlertHistory() {
        return $.get("/alert-history", { id: given_id_alert }, function(alertHistory) {
            let arr = JSON.parse(alertHistory);
            if(arr !== null && arr.length !== 0) {
                arr.forEach(function(alert) {
                    appendToAlertTable(alert);
                    // let newRow = "<tr id='tableRow" +alert.id+ "'>" +
                    //     "   <td>" + moment(alert.timestamp).format(timeFormat) + "</td>" +
                    //     "   <td>" + alert.name + "</td>" +
                    //     "</tr>";
                    // alertHistoryTable.row.add($(newRow)).draw();
                });
            }
        });
    }

    function getNewAlerts() {
        return $.get("/get-new-alerts", (alerts) => {
            let newAlerts = JSON.parse(alerts);
            if (newAlerts != null) {
                newAlerts.forEach((alert) => {
                    if(alert.deviceId == given_id_alert) {
                        appendToAlertTable(alert);
                    }
                });
            }
        });
    }

    // add id, device_status_id, alerter_id. possibly link alerter_id to umbox_instance page
    function appendToAlertTable(alert) {
        let formattedTime = alert.timestamp ? moment(alert.timestamp).format(timeFormat) : "";

        let newRow = "<tr id='tableRow" +alert.id+ "'>" +
            "   <td>" + alert.id + "</td>" +
            "   <td>" + alert.name + "</td>" +
            "   <td>" + alert.deviceStatusId + "</td>" +
            "   <td>" + alert.alerterId + "</td>" +
            "   <td>" + alert.info + "</td>" +
            "   <td>" + formattedTime + "</td>" +
            "</tr>";

        alertHistoryTable.row.add($(newRow)).draw();

        //add updated class to highlight updated row
        $("#alertHistoryTable #tableRow" + alert.id).addClass("updated");

        //remove row highlight after three seconds
        setTimeout(function() {
            $("#alertHistoryTable #tableRow" + alert.id).removeClass("updated");
        }, 3000);
    }

    /*
        Status history related
     */
    
    //a set to determine which statuses were populated on page load
    let originalStatusIds = new Set();

    let statusHistoryTable = $('#statusHistoryTable').DataTable(
        {
            order: [[0, 'desc']],
            columnDefs: [
                {orderable: false, targets: 1}
            ],
            language: {
                "emptyTable": "No status history"
            }
        }
    );

    function makeAttributesString(attributes) {
        let resultArray = [];
        let keys = Object.keys(attributes);
        keys.sort();
        keys.forEach((key) => {
            resultArray.push(key +": "+ attributes[key]);
        });

        return resultArray.join("<br>");
    }

    async function getDeviceStatuses() {
       $.get("/device-status-history", { id: given_id_alert }, function(deviceHistory) {
            let arr = JSON.parse(deviceHistory);
            addStatusesToTable(arr, false);
        });
    }

    function getNewStatuses() {
        $.get("/get-new-statuses", (statuses) => {
            let newStatuses = JSON.parse(statuses);
            addStatusesToTable(newStatuses, true);
        });
    }

    function addStatusesToTable(statuses, newStatuses){
        if (statuses !== null && statuses.length !== 0) {
            let ind = statuses.length - 1;
            if(statuses[ind].id < lowestStatusId || lowestStatusId < 0){
                lowestStatusId = statuses[ind].id;
            }
            statuses.forEach((status) => {
		if(getUrlParameter('id') == status.deviceId && !originalStatusIds.has(status.id)){
                    originalStatusIds.add(status.id);
                    let newRow = "<tr id='tableRow" + status.id + "'>" +
                        "   <td>" + status.id + "</td>" +
                        "   <td>" + moment(status.timestamp).format(timeFormat) + "</td>" +
                        "   <td>" + makeAttributesString(status.attributes) + "</td>" +
                        "</tr>";
                    statusHistoryTable.row.add($(newRow)).draw();

                    if(newStatuses){
                        $("#statusHistoryTable #tableRow" + status.id).addClass("updated");
                        setTimeout(function() {
                            $("#statusHistoryTable #tableRow" + status.id).removeClass("updated");
                        }, 3000);
                    }
                }
            });
        }
    }


    $("#statusHistoryContent #statusHistoryResetButton").click(() => {
        getNewStatuses();
    });

    $("#fetchMoreStatuses").click(() => {
        return $.get("/next-statuses",{ id: given_id_alert, lowestId: lowestStatusId }, (statuses) => {
            let arr = JSON.parse(statuses);
            addStatusesToTable(arr, false);
            statusHistoryTable.order(0, 'desc');
        });
    });

    function getUrlParameter(sParam) {
	var pageUrl = window.location.search.substring(1), urlVariables = pageUrl.split('&'), parameterName, i;
        for(i=0; i<urlVariables.length;i++){
		parameterName = urlVariables[i].split('=');
		if(parameterName[0] == sParam){
			return parameterName[1] == undefined?true:decodeURIComponent(parameterName[1]);
		}

	}
    }

    /*
        General
     */

    //tell the controller to start listening for new updates
    function startListener() {
        $.post("/start-listener", () => {});
    }

    async function main() {
        //wait for the tables be populated before listening for new entries
        await Promise.all([getAlertHistory(), getDeviceStatuses()]);

        startListener();

        setInterval(function () {
            getNewAlerts();
        }, infoPollInterval);
    }

    main();
});
