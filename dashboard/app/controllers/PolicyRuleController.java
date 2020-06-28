package controllers;

import edu.cmu.sei.kalki.db.daos.AlertTypeDAO;
import edu.cmu.sei.kalki.db.models.*;
import edu.cmu.sei.kalki.db.daos.PolicyRuleDAO;
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

public class PolicyRuleController extends Controller {
    private final FormFactory formFactory;
    private final DatabaseExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public PolicyRuleController(FormFactory formFactory, DatabaseExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new policy
    }

    public CompletionStage<Result> getPolicyRule(int id) {
        return CompletableFuture.supplyAsync(() -> {
            PolicyRule policyRule = PolicyRuleDAO.findPolicyRule(id);
            try {
                return ok(ow.writeValueAsString(policyRule));
            } catch (JsonProcessingException ignored) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getPolicyRules() {
        return CompletableFuture.supplyAsync(() -> {
            List<PolicyRule> policyRules = PolicyRuleDAO.findAllPolicyRules();
            try {
                return ok(ow.writeValueAsString(policyRules));
            } catch (JsonProcessingException ignored) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> addOrUpdatePolicyRule() {
        return CompletableFuture.supplyAsync(() -> {
            // We need to create a policy condition which will be used in the policy rule
            Form<PolicyCondition> policyConditionForm = formFactory.form(PolicyCondition.class);
            Form<PolicyCondition> policyConditionFilledForm = policyConditionForm.bindFromRequest();

            if (policyConditionFilledForm.hasErrors()) {
                return badRequest(views.html.form.render(policyConditionFilledForm));
            } else {
                // Form is good, so create the policy condition and insert it into the db
                PolicyCondition pc = policyConditionFilledForm.get();

                int policyConditionId = pc.insert();

                // Policy condition created, so now lets make the policy rule
                DynamicForm policyRuleForm = formFactory.form();
                DynamicForm policyRuleFilledForm = policyRuleForm.bindFromRequest();

                if (policyRuleFilledForm.hasErrors()) {
                    // Remove the policy condition just made, because we cant convert it into a policy rule
                    PolicyConditionDAO.deletePolicyCondition(policyConditionId);
                    return badRequest(views.html.form.render(policyRuleFilledForm));
                } else {
                    // Form is good, so gather the components from the form, create the policy rule, and insert it
                    int stateTransitionId = Integer.parseInt(policyRuleFilledForm.get("stateTransitionId"));
                    int deviceTypeId = Integer.parseInt(policyRuleFilledForm.get("deviceTypeId"));
                    int samplingRateFactor = Integer.parseInt(policyRuleFilledForm.get("samplingRateFactor"));

                    PolicyRule pr = new PolicyRule(stateTransitionId, policyConditionId, deviceTypeId,
                            samplingRateFactor);

                    pr.setId(this.updatingId);
                    this.updatingId = -1;

                    int n = pr.insert();
                    return redirect(routes.DBManagementController.dbManagementDeviceTypeView(n));
                }
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result editPolicyRule() {
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

    public CompletionStage<Result> deletePolicyRule() {
        return CompletableFuture.supplyAsync(() -> {
            String policyRuleIdString = formFactory.form().bindFromRequest().get("policyRuleId");
            String policyConditionIdString = formFactory.form().bindFromRequest().get("policyConditionId");
            int policyRuleId;
            int policyConditionId;
            try {
                policyRuleId = Integer.parseInt(policyRuleIdString);
                policyConditionId = Integer.parseInt(policyConditionIdString);
            } catch (NumberFormatException e) {
                policyRuleId = -1;
                policyConditionId = -1;
            }
            Boolean isSuccess = PolicyRuleDAO.deletePolicyRule(policyRuleId);
            PolicyConditionDAO.deletePolicyCondition(policyConditionId);
            return ok(isSuccess.toString());
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result clearPolicyRuleForm() {
        this.updatingId = -1;
        return ok();
    }
}