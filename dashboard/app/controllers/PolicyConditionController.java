package controllers;

import edu.cmu.sei.kalki.db.models.*;
import edu.cmu.sei.kalki.db.daos.PolicyConditionDAO;

import models.DatabaseExecutionContext;

import play.libs.concurrent.HttpExecution;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

public class PolicyConditionController extends Controller {
    private final FormFactory formFactory;
    private final DatabaseExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public PolicyConditionController(FormFactory formFactory, DatabaseExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new policy condition
    }

    public CompletionStage<Result> getPolicyCondition(int id) {
        return CompletableFuture.supplyAsync(() -> {
            PolicyCondition policyCondition = PolicyConditionDAO.findPolicyCondition(id);
            try {
                return ok(ow.writeValueAsString(policyCondition));
            } catch (JsonProcessingException ignored) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getPolicyConditions() {
        return CompletableFuture.supplyAsync(() -> {
            List<PolicyCondition> policyConditions = PolicyConditionDAO.findAllPolicyConditions();
            try {
                return ok(ow.writeValueAsString(policyConditions));
            } catch (JsonProcessingException ignored) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> addOrUpdatePolicyCondition() {
        return CompletableFuture.supplyAsync(() -> {
            Form<PolicyCondition> policyRuleForm = formFactory.form(PolicyCondition.class);
            Form<PolicyCondition> filledForm = policyRuleForm.bindFromRequest();

            if (filledForm.hasErrors()) {
                return badRequest(views.html.form.render(filledForm));
            } else {
                PolicyCondition pc = filledForm.get();
                pc.setId(this.updatingId);
                this.updatingId = -1;

                int n = pc.insert();
                return redirect(routes.DBManagementController.dbManagementDeviceTypeView(n));
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }
}