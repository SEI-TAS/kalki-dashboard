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
                int groupId = Integer.parseInt(filledForm.get("groupId"));
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
