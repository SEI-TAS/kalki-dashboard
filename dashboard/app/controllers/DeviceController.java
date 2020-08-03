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

package controllers;

import edu.cmu.sei.kalki.db.models.*;
import edu.cmu.sei.kalki.db.daos.*;

import models.DatabaseExecutionContext;
import play.libs.concurrent.HttpExecution;

import java.lang.OutOfMemoryError;
import java.lang.SecurityException;
import java.lang.InterruptedException;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;

import play.data.*;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class DeviceController extends Controller {

    private final FormFactory formFactory;
    private final DatabaseExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public DeviceController(FormFactory formFactory, DatabaseExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1;
    }

    public Result deviceInfo(String id) {
        Device d = DeviceDAO.findDevice(Integer.parseInt(id));
        return ok(views.html.deviceInfo.info.render(id, d));
    }

    public CompletionStage<Result> getDevices() {
        return CompletableFuture.supplyAsync(() -> {
            List<Device> devices = DeviceDAO.findAllDevices();
            try {
                return ok(ow.writeValueAsString(devices));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getDevice(String id) {
        return CompletableFuture.supplyAsync(() -> {
            int idToInt;
            try {
                idToInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                idToInt = -1;
            }
            Device device = DeviceDAO.findDevice(idToInt);
            try {
                return ok(ow.writeValueAsString(device));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> addOrUpdateDevice() {

        return CompletableFuture.supplyAsync(() -> {
            DynamicForm deviceForm = formFactory.form();
            DynamicForm filledForm = deviceForm.bindFromRequest();
            if (filledForm.hasErrors()) {
                return badRequest(views.html.form.render(filledForm));
            } else {
                String name = filledForm.get("name");
                String description = filledForm.get("description");
                String ip = filledForm.get("ip");
                int typeId = Integer.parseInt(filledForm.get("typeId"));
                int groupId;
                if(filledForm.get("groupId") == null) {
                    groupId = 0;
                }
                else {
                    groupId = Integer.parseInt(filledForm.get("groupId"));
                }
                int statusHistorySize = Integer.parseInt(filledForm.get("statusHistorySize"));
                int samplingRate = Integer.parseInt(filledForm.get("samplingRate"));
                int dataNode = Integer.parseInt(filledForm.get("dataNode"));

                Device d = new Device(name, description, typeId, groupId, ip, statusHistorySize, samplingRate, samplingRate, dataNode);

                List<Integer> tagIdsList = new ArrayList<Integer>();

                String tagIdsString = filledForm.get("tagIds");

                if (tagIdsString != null) {
                    String[] tagIdsStringArray = tagIdsString.substring(1, tagIdsString.length() - 1).split(", ");

                    //convert array of strings to list of integer

                    for (String s : tagIdsStringArray) {
                        if (!s.equals("-1")) {    //need to remove dummy -1 value
                            tagIdsList.add(Integer.valueOf(s));
                        }
                    }

                    d.setTagIds(tagIdsList);
                }

                editDevice();
                d.setId(this.updatingId);
                this.updatingId = -1;

                int n = d.insertOrUpdate();
                return redirect(routes.HomeController.devices());
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result editDevice() {
        String id = formFactory.form().bindFromRequest().get("id");
        int idToInt;
        try {
            idToInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            idToInt = -1;
        }
        this.updatingId = idToInt;
        return ok();
    }

    public CompletionStage<Result> deleteDevice() {
        return CompletableFuture.supplyAsync(() -> {
            String id = formFactory.form().bindFromRequest().get("id");
            int idToInt;
            try {
                idToInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                idToInt = -1;
            }
            Boolean isSuccess = DeviceDAO.deleteDevice(idToInt);
            return ok(isSuccess.toString());
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result clearDeviceForm() {
        this.updatingId = -1;
        return ok();
    }

    public CompletionStage<Result> getDeviceSecurityState(int deviceId) {
        return CompletableFuture.supplyAsync(() -> {
            DeviceSecurityState state = DeviceSecurityStateDAO.findDeviceSecurityStateByDevice(deviceId);
            try {
                return ok(ow.writeValueAsString(state));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getDeviceStatusHistory(int deviceId) {
        return CompletableFuture.supplyAsync(() -> {
            List<DeviceStatus> deviceHistory = DeviceStatusDAO.findNDeviceStatuses(deviceId, 50);
            try {
                return ok(ow.writeValueAsString(deviceHistory));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getNextDeviceStatusHistory(int deviceId, int lowestId) {
        return CompletableFuture.supplyAsync(() -> {
            List<DeviceStatus> deviceHistory = DeviceStatusDAO.findSubsetNDeviceStatuses(deviceId, 50, lowestId);
            try {
                return ok(ow.writeValueAsString(deviceHistory));
            } catch (JsonProcessingException e){
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getAlertHistory(int deviceId) {
        return CompletableFuture.supplyAsync(() -> {
            List<Alert> alertHistory = AlertDAO.findAlertsByDevice(deviceId);
            try {
                return ok(ow.writeValueAsString(alertHistory));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getDeviceStatus(int deviceStatusId) {
        return CompletableFuture.supplyAsync(() -> {
           DeviceStatus status = DeviceStatusDAO.findDeviceStatus(deviceStatusId);
            try {
                return ok(ow.writeValueAsString(status));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getUmboxInstances(int deviceId) {
        return CompletableFuture.supplyAsync(() -> {
            List<UmboxInstance> instances = UmboxInstanceDAO.findUmboxInstances(deviceId);
            try {
                return ok(ow.writeValueAsString(instances));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

//    public CompletionStage<Result> getStageLogs(int deviceId) {
//        return CompletableFuture.supplyAsync(() -> {
//            List<StageLog> logs = Postgres.findAllStageLogsForDevice(deviceId);
//            try {
//                return ok(ow.writeValueAsString(logs));
//            } catch (JsonProcessingException e) {
//            }
//            return ok();
//        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
//    }
//
//    public CompletionStage<Result> getUmboxLogs(int deviceId) {
//        return CompletableFuture.supplyAsync(() -> {
//            List<UmboxLog> logs = Postgres.findAllUmboxLogsForDevice(deviceId);
//            try {
//                return ok(ow.writeValueAsString(logs));
//            } catch (JsonProcessingException e) {
//            }
//            return ok();
//        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
//    }

    public CompletionStage<Result> resetSecurityState(Integer deviceId) {
        return CompletableFuture.supplyAsync(() -> {
            DeviceDAO.resetSecurityState(deviceId);
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }
}
