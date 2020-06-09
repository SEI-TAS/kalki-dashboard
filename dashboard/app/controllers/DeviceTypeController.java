package controllers;

import edu.cmu.sei.kalki.db.models.*;
import edu.cmu.sei.kalki.db.daos.DeviceTypeDAO;

import models.DatabaseExecutionContext;
import play.libs.concurrent.HttpExecution;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
import play.api.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

public class DeviceTypeController extends Controller {
    private final FormFactory formFactory;
    private final DatabaseExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public DeviceTypeController(FormFactory formFactory, DatabaseExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new alertType
    }

    public CompletionStage<Result> getDeviceTypes() {
        return CompletableFuture.supplyAsync(() -> {
            List<DeviceType> deviceTypes = DeviceTypeDAO.findAllDeviceTypes();
            try {
                return ok(ow.writeValueAsString(deviceTypes));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result editDeviceType() {
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

    public CompletionStage<Result> addOrUpdateDeviceType() {
        return CompletableFuture.supplyAsync(() -> {
            Form<DeviceType> deviceTypeForm = formFactory.form(DeviceType.class);
            Form<DeviceType> filledForm = deviceTypeForm.bindFromRequest();
            if (filledForm.hasErrors()) {
                return badRequest(views.html.form.render(filledForm));
            } else {
                DeviceType dt = filledForm.get();
                dt.setId(this.updatingId);
                this.updatingId = -1;

                int n = dt.insertOrUpdate();
                return redirect(routes.DBManagementController.dbManagementDeviceTypeView(n));
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> deleteDeviceType() {
        return CompletableFuture.supplyAsync(() -> {
            String id = formFactory.form().bindFromRequest().get("id");
            int idToInt;
            try {
                idToInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                idToInt = -1;
            }
            Boolean isSuccess = DeviceTypeDAO.deleteDeviceType(idToInt);
            return ok(isSuccess.toString());
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result clearDeviceTypeForm() {
        this.updatingId = -1;
        return ok();
    }
}
