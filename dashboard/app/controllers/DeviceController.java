package controllers;

import kalkidb.models.Device;
import kalkidb.models.UmboxInstance;
import kalkidb.models.AlertHistory;
import kalkidb.models.Type;
import kalkidb.database.Postgres;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.ArrayList;

public class DeviceController extends Controller {

    private final FormFactory formFactory;
    private final HttpExecutionContext ec;
    private final ObjectWriter ow;
    private boolean editPage;
    private String deviceId;

    @Inject
    public DeviceController(FormFactory formFactory, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.editPage = false;
        this.deviceId = "-1";
    }

    public Result addDevicePage() {
        this.editPage = false;
        this.deviceId = "-1";
        return ok(views.html.add.render());
    }

    public Result editDevicePage(String id) {
        this.editPage = true;
        this.deviceId = id;
        return ok(views.html.edit.render(id));
    }

    public Result deviceInfo(String id) {
        return ok(views.html.info.render(id));
    }

    public CompletionStage<Result> addOrEditDevice() throws Exception {
        Form<Device> deviceForm = formFactory.form(Device.class);
        Form<Device> filledForm = deviceForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> { return badRequest(views.html.form.render(filledForm)); });
        } else {
            Device device = filledForm.get();
            MultipartFormData<File> body = request().body().asMultipartFormData();
            return device.insertOrUpdate().thenApplyAsync(n -> {
                return redirect(routes.HomeController.index());
            }, ec.current());
        }
    }

    public CompletionStage<Result> deleteDevice() {
        String id = formFactory.form().bindFromRequest().get("id");
        int idToInt;
        try {
            idToInt = Integer.parseInt(id);
        }
        catch (NumberFormatException e) {
            idToInt = -1;
        }
        return Postgres.deleteDevice(idToInt).thenApplyAsync(isSuccess -> {
            if(isSuccess) {
                return ok();
            } else {
                return internalServerError();
            }
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

    public CompletionStage<Result> getDevices() {
        return Postgres.findAllDevices().thenApplyAsync(devices -> {
            try {
                return ok(ow.writeValueAsString(devices));
            }
            catch(JsonProcessingException e) {}
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> getGroups() {
        return Postgres.findAllGroups().thenApplyAsync(groups -> {
            try {
                return ok(ow.writeValueAsString(groups));
            }
            catch(JsonProcessingException e) {}
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> getTypes() {
        return Postgres.findAllTypes().thenApplyAsync(types -> {
            try {
                return ok(ow.writeValueAsString(types));
            }
            catch(JsonProcessingException e) {}
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> getTags() {
        return Postgres.findAllTags().thenApplyAsync(tags -> {
            try {
                return ok(ow.writeValueAsString(tags));
            }
            catch(JsonProcessingException e) {}
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> getDeviceHistory(int deviceId) {
        return Postgres.findDeviceHistories(deviceId).thenApplyAsync(deviceHistory -> {
            try {
                return ok(ow.writeValueAsString(deviceHistory));
            }
            catch(JsonProcessingException e) {}
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> getStateHistory(int deviceId) {
        return Postgres.findStateHistories(deviceId).thenApplyAsync(stateHistory -> {
            try {
                return ok(ow.writeValueAsString(stateHistory));
            }
            catch(JsonProcessingException e) {}
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> getAlertHistory(int deviceId) {
        return Postgres.findUmboxInstances(deviceId).thenApplyAsync(umboxInstances -> {
            List<String> umboxAlerterIds = new ArrayList<String>();
            for (UmboxInstance u : umboxInstances) {
                umboxAlerterIds.add(u.getAlerterId());
            }
            // getAlertHistory is not async because Play dones't like when the return type of this method is
            // CompletionStage<CompletionStage<Result>> apparently.
            try{
                List<AlertHistory> alertHistory = Postgres.findAlertHistories(umboxAlerterIds).thenApplyAsync(histories ->{
                    return histories;
                }).toCompletableFuture().get();
                return ok(ow.writeValueAsString(alertHistory));
            }
            catch(InterruptedException e1) { e1.printStackTrace(); }
            catch(ExecutionException e2) { e2.printStackTrace(); }
            catch(JsonProcessingException e) {}
            return ok();
        }, ec.current());
    }


    public CompletionStage<Result> add(String table, String name) {
        String s = formFactory.form().bindFromRequest().get(name);
        return Postgres.addRowToTable(table, s).thenApplyAsync(n -> {
            return ok(Integer.toString(n));
        }, ec.current());
    }

    public CompletionStage<Result> addGroup() {
        return add("device_group", "group");
    }

    public CompletionStage<Result> addType() {
        DynamicForm filledForm = formFactory.form().bindFromRequest();
        Type type = new Type(-1, filledForm.get("type"));

        MultipartFormData<File> body = request().body().asMultipartFormData();
        FilePart<File> policy = body.getFile("policyFile");

        if(filledForm.hasErrors() || policy == null) {
            return CompletableFuture.supplyAsync(() -> {return badRequest(views.html.form.render(filledForm)); });
        } else {

            type.setPolicyFileName( policy.getFilename() );

            try {
                type.setPolicyFile( Files.readAllBytes(policy.getFile().toPath()) );
            }
            catch (IOException e) {}
            catch (OutOfMemoryError e) {}
            catch (SecurityException e) {}

            return type.insert().thenApplyAsync(n -> {
                System.out.println("type name: "+type.getName());
                System.out.println("type policy file name:"+type.getPolicyFileName());
                if(editPage){
                    return ok(views.html.edit.render(this.deviceId));
                } else {
                    return ok(views.html.add.render());
                }
                }, ec.current());
        }
    }

    public CompletionStage<Result> addTag() {
        return add("tag", "tag");
    }

}
