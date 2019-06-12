package controllers;

import edu.cmu.sei.ttg.kalki.models.*;
import edu.cmu.sei.ttg.kalki.database.Postgres;

import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
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

public class AlertConditionController extends Controller {
    private final FormFactory formFactory;
    private final HttpExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public AlertConditionController(FormFactory formFactory, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new alertType
    }

    public CompletionStage<Result> getAlertConditions() {
        return CompletableFuture.supplyAsync(() -> {
            List<AlertCondition> alertTypes = Postgres.findAllAlertConditions();
            try {
                return ok(ow.writeValueAsString(alertTypes));
            } catch (JsonProcessingException e) {
            }
            return ok();
        });
    }

    public Result editAlertCondition() {
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

    public CompletionStage<Result> addOrUpdateAlertCondition() {
        Form<AlertCondition> alertTypeForm = formFactory.form(AlertCondition.class);
        Form<AlertCondition> filledForm = alertTypeForm.bindFromRequest();

        System.out.println(filledForm.toString());


        if(filledForm.hasErrors()) {
            return CompletableFuture.supplyAsync(() -> { return badRequest(views.html.form.render(filledForm)); });
        } else {
            AlertCondition at = filledForm.get();
            at.setId(this.updatingId);
            this.updatingId = -1;

            System.out.println("trying to insert alert condition: \n" +at.toString());
            System.out.println(at.getVariables().getClass().getName());

            return at.insertOrUpdate().thenApplyAsync(n -> {
                return redirect(routes.DBManagementController.dbManagementView());
            }, ec.current());
        }
    }

    public CompletionStage<Result> deleteAlertCondition() {
        String id = formFactory.form().bindFromRequest().get("id");
        int idToInt;
        try {
            idToInt = Integer.parseInt(id);
        }
        catch (NumberFormatException e) {
            idToInt = -1;
        }
        return Postgres.deleteAlertCondition(idToInt).thenApplyAsync(isSuccess -> {
            return ok(isSuccess.toString());
        }, ec.current());
    }

    public Result clearAlertConditionForm() {
        this.updatingId = -1;
        return ok();
    }
}