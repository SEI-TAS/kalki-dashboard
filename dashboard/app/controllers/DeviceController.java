package controllers;

import edu.cmu.sei.ttg.kalki.models.*;
import edu.cmu.sei.ttg.kalki.database.Postgres;

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
        return ok(views.html.deviceInfo.info.render(id));
    }

    public CompletionStage<Result> getDevices() {
        return CompletableFuture.supplyAsync(() -> {
            List<Device> devices = Postgres.findAllDevices();
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
            Device device = Postgres.findDevice(idToInt);
            try {
                return ok(ow.writeValueAsString(device));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> addOrUpdateDevice() {
        return CompletableFuture.supplyAsync(() -> {
            Form<Device> deviceForm = formFactory.form(Device.class);
            Form<Device> filledForm = deviceForm.bindFromRequest();
            if (filledForm.hasErrors()) {
                return badRequest(views.html.form.render(filledForm));
            } else {
                Device d = filledForm.get();
                // filledForm.get is not handling typeId and groupId correctly
                // may have to map the form to the correct Device constructor that accepts the id's

                d = new Device(d.getName(), d.getDescription(), Integer.valueOf(filledForm.field("typeId").getValue().get()), Integer.valueOf(filledForm.field("groupId").getValue().get()), d.getIp(), d.getStatusHistorySize(), d.getSamplingRate(), d.getSamplingRate());

                List<Integer> tagIdsList = new ArrayList<Integer>();

                String tagIdsString = filledForm.field("tagIds").getValue().get();

                String[] tagIdsStringArray = tagIdsString.substring(1, tagIdsString.length() - 1).split(", ");

                //convert array of strings to list of integer

                for (String s : tagIdsStringArray) {
                    if (!s.equals("-1")) {    //need to remove dummy -1 value
                        tagIdsList.add(Integer.valueOf(s));
                    }
                }

                d.setTagIds(tagIdsList);
                d.setId(this.updatingId);

                this.updatingId = -1;

                int n = d.insertOrUpdate();
                return redirect(routes.DBManagementController.dbManagementView(n));
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
            Boolean isSuccess = Postgres.deleteDevice(idToInt);
            return ok(isSuccess.toString());
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result clearDeviceForm() {
        this.updatingId = -1;
        return ok();
    }

    public CompletionStage<Result> getDeviceSecurityState(int deviceId) {
        return CompletableFuture.supplyAsync(() -> {
            DeviceSecurityState state = Postgres.findDeviceSecurityStateByDevice(deviceId);
            try {
                return ok(ow.writeValueAsString(state));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getDeviceStatusHistory(int deviceId) {
        return CompletableFuture.supplyAsync(() -> {
            List<DeviceStatus> deviceHistory = Postgres.findNDeviceStatuses(deviceId, 50);
            try {
                return ok(ow.writeValueAsString(deviceHistory));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getNextDeviceStatusHistory(int deviceId, int lowestId) {
        return CompletableFuture.supplyAsync(() -> {
            List<DeviceStatus> deviceHistory = Postgres.findSubsetNDeviceStatuses(deviceId, 50, lowestId);
            try {
                return ok(ow.writeValueAsString(deviceHistory));
            } catch (JsonProcessingException e){
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getAlertHistory(int deviceId) {
        return CompletableFuture.supplyAsync(() -> {
            List<Alert> alertHistory = Postgres.findAlertsByDevice(deviceId);
            try {
                return ok(ow.writeValueAsString(alertHistory));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getDeviceStatus(int deviceStatusId) {
        return CompletableFuture.supplyAsync(() -> {
           DeviceStatus status = Postgres.findDeviceStatus(deviceStatusId);
            try {
                return ok(ow.writeValueAsString(status));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getUmboxInstances(int deviceId) {
        return CompletableFuture.supplyAsync(() -> {
            List<UmboxInstance> instances = Postgres.findUmboxInstances(deviceId);
            try {
                return ok(ow.writeValueAsString(instances));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> resetSecurityState(Integer deviceId) {
        return CompletableFuture.supplyAsync(() -> {
            Postgres.resetSecurityState(deviceId);
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }
}
