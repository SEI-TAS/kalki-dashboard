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

public class DBManagementController extends Controller {
    private final FormFactory formFactory;
    private final HttpExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public DBManagementController(FormFactory formFactory, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new alertType
    }

    public Result dbManagementView() {
        return ok(views.html.dbManagement.dbManagementScreen.render());
    }


    //  AlertType methods
    public CompletionStage<Result> getAlertTypes() {
        return CompletableFuture.supplyAsync(() -> {
            List<AlertType> alertTypes = Postgres.findAllAlertTypes();
            System.out.println("***********" +alertTypes.size());
            try {
                return ok(ow.writeValueAsString(alertTypes));
            } catch (JsonProcessingException e) {
            }
            return ok();
        });
    }

    public Result editAlertType(int id) {
        return ok();
        //TODO: populate the form with the correct values and set the updating ID to the editing type
    }

    public Result addOrUpdateAlertType() {
        return ok();
        //TODO: either create a new alertType in the db, or update an existing one based on if
        //editAlertType had been called first
    }

    public CompletionStage<Result> deleteAlertType() {
        String id = formFactory.form().bindFromRequest().get("id");
        int idToInt;
        try {
            idToInt = Integer.parseInt(id);
        }
        catch (NumberFormatException e) {
            idToInt = -1;
        }
        return Postgres.deleteAlertType(idToInt).thenApplyAsync(isSuccess -> {
            if (isSuccess) {
                return ok();
            } else {
                return internalServerError();
            }
        }, ec.current());
    }
}