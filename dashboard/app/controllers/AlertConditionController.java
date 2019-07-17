package controllers;

import edu.cmu.sei.ttg.kalki.models.*;
import edu.cmu.sei.ttg.kalki.database.Postgres;

import models.DatabaseExecutionContext;
import play.libs.concurrent.HttpExecution;

import play.mvc.Controller;
import play.db.*;
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

public class AlertConditionController extends Controller {
    private final FormFactory formFactory;
    private final DatabaseExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public AlertConditionController(FormFactory formFactory, DatabaseExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new alertType
    }

    public CompletionStage<Result> getAlertConditions() {
        return CompletableFuture.supplyAsync(() -> {
            List<AlertCondition> alertConditions = Postgres.findAllAlertConditions();
            try {
                return ok(ow.writeValueAsString(alertConditions));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getAlertConditionsByDevice(int id) {
        return CompletableFuture.supplyAsync(() -> {
            List<AlertCondition> alertConditions = Postgres.findAlertConditionsByDevice(id);
            try {
                return ok(ow.writeValueAsString(alertConditions));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result editAlertCondition() {
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

    public CompletionStage<Result> addOrUpdateAlertCondition() {
        return CompletableFuture.supplyAsync(() -> {
            Form<AlertCondition> alertTypeForm = formFactory.form(AlertCondition.class);
            Form<AlertCondition> filledForm = alertTypeForm.bindFromRequest();

            if (filledForm.hasErrors()) {
                return badRequest(views.html.form.render(filledForm));
            } else {
                AlertCondition at = filledForm.get();
                at.setId(this.updatingId);
                this.updatingId = -1;

                int n = at.insertOrUpdate();
                return redirect(routes.DBManagementController.dbManagementView(n));
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> deleteAlertCondition() {
        return CompletableFuture.supplyAsync(() -> {
            String id = formFactory.form().bindFromRequest().get("id");
            int idToInt;
            try {
                idToInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                idToInt = -1;
            }
            Boolean isSuccess = Postgres.deleteAlertCondition(idToInt);
            return ok(isSuccess.toString());
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result clearAlertConditionForm() {
        this.updatingId = -1;
        return ok();
    }
}