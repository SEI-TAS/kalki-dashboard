package controllers;

import kalkidb.models.Device;
import kalkidb.database.Postgres;
import java.lang.OutOfMemoryError;
import java.lang.SecurityException;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import play.data.*;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Scanner;

public class DeviceController extends Controller {

    private final FormFactory formFactory;
    private final HttpExecutionContext ec;
    private final ObjectWriter ow;

    @Inject
    public DeviceController(FormFactory formFactory, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    public Result addDevicePage() {
        return ok(views.html.add.render());
    }

    public Result editDevicePage(String id) {
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
            FilePart<File> policy = body.getFile("policyFile");
            if(policy != null) {
                String policyName = policy.getFilename();
                String contentType = policy.getContentType();
                File policyFile = policy.getFile();
                byte[] policyFileBytes = null;
                try {
                    policyFileBytes = Files.readAllBytes(policyFile.toPath());
                }
                catch (IOException e) {}
                catch (OutOfMemoryError e) {}
                catch (SecurityException e) {}

                device.setPolicyFileName(policyName);
                device.setPolicyFile(policyFileBytes);
            }
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
        return Postgres.getAllDevices().thenApplyAsync(devices -> {
            try {
                return ok(ow.writeValueAsString(devices));
            }
            catch(JsonProcessingException e) {

            }
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> getGroups() {
        return Postgres.getAllGroups().thenApplyAsync(groups -> {
            try {
                return ok(ow.writeValueAsString(groups));
            }
            catch(JsonProcessingException e) {

            }
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> getTypes() {
        return Postgres.getAllTypes().thenApplyAsync(types -> {
            try {
                return ok(ow.writeValueAsString(types));
            }
            catch(JsonProcessingException e) {

            }
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> getTags() {
        return Postgres.getAllTags().thenApplyAsync(tags -> {
            try {
                return ok(ow.writeValueAsString(tags));
            }
            catch(JsonProcessingException e) {

            }
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
        return add("type", "type");
    }

    public CompletionStage<Result> addTag() {
        return add("tag", "tag");
    }

}
