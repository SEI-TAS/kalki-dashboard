package controllers;

import edu.cmu.sei.ttg.kalki.models.*;
import edu.cmu.sei.ttg.kalki.database.Postgres;

import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
import play.api.mvc.*;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.inject.Inject;

public class DeviceTypeController extends Controller {
    private final FormFactory formFactory;
    private final HttpExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public DeviceTypeController(FormFactory formFactory, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new alertType
    }

    public CompletionStage<Result> getDeviceTypes() {
        return Postgres.findAllDeviceTypes().thenApplyAsync(deviceTypes -> {
            try {
                return ok(ow.writeValueAsString(deviceTypes));
            }
            catch(JsonProcessingException e) {}
            return ok();
        }, ec.current());
    }

    public Result editDeviceType() {
        String id = formFactory.form().bindFromRequest().get("id");
        int idToInt;
        try {
            idToInt = Integer.parseInt(id);
        }
        catch (NumberFormatException e) {
            idToInt = -1;
        }
        this.updatingId = idToInt;
        return ok();
    }

    public CompletionStage<Result> addOrUpdateDeviceType() {
        Form<DeviceType> deviceTypeForm = formFactory.form(DeviceType.class);
        Form<DeviceType> filledForm = deviceTypeForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> { return badRequest(views.html.form.render(filledForm)); });
        } else {
            DeviceType dt = filledForm.get();
            dt.setId(this.updatingId);
            this.updatingId = -1;

            return dt.insertOrUpdate().thenApplyAsync(n -> {
                return redirect(routes.DBManagementController.dbManagementView(n));
            }, ec.current());
        }
    }

    public CompletionStage<Result> deleteDeviceType() {
        String id = formFactory.form().bindFromRequest().get("id");
        int idToInt;
        try {
            idToInt = Integer.parseInt(id);
        }
        catch (NumberFormatException e) {
            idToInt = -1;
        }
        return Postgres.deleteDeviceType(idToInt).thenApplyAsync(isSuccess -> {
            return ok(isSuccess.toString());
        }, ec.current());
    }

    public Result clearDeviceTypeForm() {
        this.updatingId = -1;
        return ok();
    }
}