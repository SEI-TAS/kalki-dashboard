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
import java.util.concurrent.TimeUnit;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.inject.Inject;

public class SecurityStateController extends Controller {
    private final FormFactory formFactory;
    private final HttpExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public SecurityStateController(FormFactory formFactory, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new alertType
    }

    public Result getSecurityStates() {
        List<SecurityState> securityStates = Postgres.findAllSecurityStates();
        try {
            return ok(ow.writeValueAsString(securityStates));
        } catch (JsonProcessingException e) {
        }
        return ok();
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

    public Result addOrUpdateSecurityState() {
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
    }

    public Result deleteSecurityState() {
        String id = formFactory.form().bindFromRequest().get("id");
        int idToInt;
        try {
            idToInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            idToInt = -1;
        }
        Boolean isSuccess = Postgres.deleteSecurityState(idToInt);
        return ok(isSuccess.toString());
    }

    public Result clearSecurityStateForm() {
        this.updatingId = -1;
        return ok();
    }
}