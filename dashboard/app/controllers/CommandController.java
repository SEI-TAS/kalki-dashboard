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

import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

public class CommandController extends Controller {
    private final FormFactory formFactory;
    private final DatabaseExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public CommandController(FormFactory formFactory, DatabaseExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new alertType
    }

    public CompletionStage<Result> getCommands() {
        return CompletableFuture.supplyAsync(() -> {
            List<DeviceCommand> commands = Postgres.findAllCommands();
            try {
                return ok(ow.writeValueAsString(commands));
            } catch (JsonProcessingException e) {
                return ok();
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getCommandsByDevice(int id) {
        return CompletableFuture.supplyAsync(() -> {
            Device device = Postgres.findDevice(id);
            List<DeviceCommand> commands;
            if(device != null) {
                commands = Postgres.findCommandsByDevice(device);
            }
            else {
                commands = Collections.emptyList();
            }

            try {
                return ok(ow.writeValueAsString(commands));
            } catch (JsonProcessingException e) {
                return ok();
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result editCommand() {
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

    public CompletionStage<Result> addOrUpdateCommand() {
        return  CompletableFuture.supplyAsync(() -> {
            Form<DeviceCommand> commandForm = formFactory.form(DeviceCommand.class);
            Form<DeviceCommand> filledForm = commandForm.bindFromRequest();

            if (filledForm.hasErrors()) {
                return badRequest(views.html.form.render(filledForm));
            } else {
                DeviceCommand command = filledForm.get();

                command.setId(this.updatingId);
                this.updatingId = -1;

                int n = command.insertOrUpdate();
                return redirect(routes.DBManagementController.dbManagementView(n));
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> deleteCommand() {
        return  CompletableFuture.supplyAsync(() -> {
            String id = formFactory.form().bindFromRequest().get("id");
            int idToInt;
            try {
                idToInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                idToInt = -1;
            }

            Boolean isSuccess = Postgres.deleteCommand(idToInt);
            return ok(isSuccess.toString());
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result clearCommandForm() {
        this.updatingId = -1;
        return ok();
    }
}