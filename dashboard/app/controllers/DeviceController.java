package controllers;

import edu.cmu.sei.ttg.kalki.models.*;
import edu.cmu.sei.ttg.kalki.database.Postgres;

import java.lang.OutOfMemoryError;
import java.lang.SecurityException;
import java.lang.InterruptedException;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;

import play.data.*;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
//import play.mvc.Http.Dynamicform;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DeviceController extends Controller {

    private final FormFactory formFactory;
    private final HttpExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public DeviceController(FormFactory formFactory, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1;
    }

    public Result deviceInfo(String id) {
        return ok(views.html.info.render(id));
    }

    public CompletionStage<Result> getDevices() {
        return Postgres.findAllDevices().thenApplyAsync(devices -> {
            try {
                return ok(ow.writeValueAsString(devices));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> getDevice(String id) {
        int idToInt;
        try {
            idToInt = Integer.parseInt(id);
        }
        catch (NumberFormatException e) {
            idToInt = -1;
        }
        return Postgres.findDevice(idToInt).thenApplyAsync(device -> {
            try {
                return ok(ow.writeValueAsString(device));
            }
            catch (JsonProcessingException e) {}
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> addOrUpdateDevice() throws Exception {
        Form<Device> deviceForm = formFactory.form(Device.class);
        Form<Device> filledForm = deviceForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> {
                return badRequest(views.html.form.render(filledForm));
            });
        } else {
            Device d = filledForm.get();
            // filledForm.get is not handling typeId and groupId correctly
            // may have to map the form to the correct Device constructor that accepts the id's

            d = new Device(d.getName(), d.getDescription(), Integer.valueOf(filledForm.field("typeId").getValue().get()), Integer.valueOf(filledForm.field("groupId").getValue().get()), d.getIp(), d.getStatusHistorySize(), d.getSamplingRate());

            List<Integer> tagIdsList = new ArrayList<Integer>();

            String tagIdsString = filledForm.field("tagIds").getValue().get();

            String[] tagIdsStringArray = tagIdsString.substring(1, tagIdsString.length() - 1).split(", ");

            //convert array of strings to list of integer

            for (String s : tagIdsStringArray)  {
                if(!s.equals("-1")) {    //need to remove dummy -1 value
                    tagIdsList.add(Integer.valueOf(s));
                }
            }

            d.setTagIds(tagIdsList);
            d.setId(this.updatingId);

            this.updatingId = -1;

            return d.insertOrUpdate().thenApplyAsync(n -> {
                return redirect(routes.DBManagementController.dbManagementView(n));
            }, ec.current());
        }
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
        String id = formFactory.form().bindFromRequest().get("id");
        int idToInt;
        try {
            idToInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            idToInt = -1;
        }
        return Postgres.deleteDevice(idToInt).thenApplyAsync(isSuccess -> {
            return ok(isSuccess.toString());
        }, ec.current());
    }

    public Result clearDeviceForm() {
        this.updatingId = -1;
        return ok();
    }

    public CompletionStage<Result> getDeviceHistory(int deviceId) {
        return Postgres.findDeviceStatuses(deviceId).thenApplyAsync(deviceHistory -> {
            try {
                return ok(ow.writeValueAsString(deviceHistory));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> getStateHistory(int deviceId) {
        return CompletableFuture.supplyAsync(() -> {
            DeviceSecurityState state = Postgres.findDeviceSecurityStateByDevice(deviceId);
            try {
                return ok(ow.writeValueAsString(state));
            } catch (JsonProcessingException e) {
            }
            return ok();
        });
    }

    public CompletionStage<Result> getAlertHistory(int deviceId) {
        return Postgres.findUmboxInstances(deviceId).thenApplyAsync(umboxInstances -> {
            List<String> umboxAlerterIds = new ArrayList<String>();
            for (UmboxInstance u : umboxInstances) {
                umboxAlerterIds.add(u.getAlerterId());
            }
            // getAlertHistory is not async because Play dones't like when the return type of this method is
            // CompletionStage<CompletionStage<Result>> apparently.
            try {
                List<Alert> alertHistory = Postgres.findAlerts(umboxAlerterIds).thenApplyAsync(histories -> {
                    return histories;
                }).toCompletableFuture().get();
                return ok(ow.writeValueAsString(alertHistory));
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } catch (ExecutionException e2) {
                e2.printStackTrace();
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, ec.current());
    }
}
