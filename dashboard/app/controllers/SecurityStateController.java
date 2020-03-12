package controllers;

import edu.cmu.sei.kalki.db.models.*;
import edu.cmu.sei.kalki.db.daos.SecurityStateDAO;

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

public class SecurityStateController extends Controller {
    private final FormFactory formFactory;
    private final DatabaseExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public SecurityStateController(FormFactory formFactory, DatabaseExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new alertType
    }

    public CompletionStage<Result> getSecurityState(int id) {
        return CompletableFuture.supplyAsync(() -> {
            SecurityState securityState = SecurityStateDAO.findSecurityState(id);
            try {
                return ok(ow.writeValueAsString(securityState));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getSecurityStates() {
        return CompletableFuture.supplyAsync(() -> {
            List<SecurityState> securityStates = SecurityStateDAO.findAllSecurityStates();
            try {
                return ok(ow.writeValueAsString(securityStates));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result editSecurityState() {
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

    public CompletionStage<Result> addOrUpdateSecurityState() {
        return CompletableFuture.supplyAsync(() -> {
            Form<SecurityState> securityStateForm = formFactory.form(SecurityState.class);
            Form<SecurityState> filledForm = securityStateForm.bindFromRequest();
            if (filledForm.hasErrors()) {
                return badRequest(views.html.form.render(filledForm));
            } else {
                SecurityState dt = filledForm.get();
                dt.setId(this.updatingId);
                this.updatingId = -1;

                int n = dt.insertOrUpdate();
                return redirect(routes.DBManagementController.dbManagementView(n));
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> deleteSecurityState() {
        return CompletableFuture.supplyAsync(() -> {
            String id = formFactory.form().bindFromRequest().get("id");
            int idToInt;
            try {
                idToInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                idToInt = -1;
            }
            Boolean isSuccess = SecurityStateDAO.deleteSecurityState(idToInt);
            return ok(isSuccess.toString());
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result clearSecurityStateForm() {
        this.updatingId = -1;
        return ok();
    }
}
