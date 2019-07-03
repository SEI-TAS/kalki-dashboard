package controllers;

import edu.cmu.sei.ttg.kalki.models.*;
import edu.cmu.sei.ttg.kalki.database.Postgres;

import models.DatabaseExecutionContext;
import play.libs.concurrent.HttpExecution;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
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

public class CommandLookupController extends Controller {
    private final FormFactory formFactory;
    private final DatabaseExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public CommandLookupController(FormFactory formFactory, DatabaseExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new alertType
    }

    public CompletionStage<Result> getCommandLookups() {
        return CompletableFuture.supplyAsync(() -> {
            List<DeviceCommandLookup> commandLookups = Postgres.findAllCommandLookups();
            try {
                return ok(ow.writeValueAsString(commandLookups));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getCommandLookupsByDevice(int deviceId) {
        return CompletableFuture.supplyAsync(() -> {
            List<DeviceCommandLookup> commandLookups = Postgres.findCommandLookupsByDevice(id);
            try {
                return ok(ow.writeValueAsString(commandLookups));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result editCommandLookup() {
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

    public CompletionStage<Result> addOrUpdateCommandLookup() {
        return CompletableFuture.supplyAsync(() -> {
            Form<DeviceCommandLookup> commandLookupForm = formFactory.form(DeviceCommandLookup.class);
            Form<DeviceCommandLookup> filledForm = commandLookupForm.bindFromRequest();

            if (filledForm.hasErrors()) {
                return badRequest(views.html.form.render(filledForm));
            } else {
                DeviceCommandLookup cl = filledForm.get();

                cl.setId(this.updatingId);
                this.updatingId = -1;

                int n = cl.insertOrUpdate();
                return redirect(routes.DBManagementController.dbManagementView(n));
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> deleteCommandLookup() {
        return CompletableFuture.supplyAsync(() -> {
            String id = formFactory.form().bindFromRequest().get("id");
            int idToInt;
            try {
                idToInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                idToInt = -1;
            }
            Boolean isSuccess = Postgres.deleteCommandLookup(idToInt);
            return ok(isSuccess.toString());
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result clearCommandLookupForm() {
        this.updatingId = -1;
        return ok();
    }
}