package controllers;

import edu.cmu.sei.kalki.db.daos.AlertTypeDAO;
import edu.cmu.sei.kalki.db.daos.DeviceCommandLookupDAO;
import edu.cmu.sei.kalki.db.models.*;
import edu.cmu.sei.kalki.db.daos.PolicyRuleDAO;
import edu.cmu.sei.kalki.db.daos.PolicyConditionDAO;

import models.DatabaseExecutionContext;

import play.libs.concurrent.HttpExecution;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
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
    private int policyConditionId;

    @Inject
    public PolicyRuleController(FormFactory formFactory, DatabaseExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new policy
        this.policyConditionId = -1; // -1 until updating.
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

            DynamicForm policyRuleForm = formFactory.form();
            DynamicForm policyRuleFilledForm = policyRuleForm.bindFromRequest();

            Form<DeviceCommandLookup> deviceCommandLookupForm = formFactory.form(DeviceCommandLookup.class);
            Form<DeviceCommandLookup> deviceCommandLookupFilledForm = deviceCommandLookupForm.bindFromRequest();

            if (policyConditionFilledForm.hasErrors() || policyRuleFilledForm.hasErrors() ||
                    deviceCommandLookupFilledForm.hasErrors()) {
                return badRequest(views.html.form.render(policyConditionFilledForm));
            } else {
                // Forms are good, so get the various form components, and create the objects
                PolicyCondition pc = policyConditionFilledForm.get();
                // Need to insert the policy condition immediately to get the id for the policy rule.
                int policyConditionId = pc.insert();

                DeviceCommandLookup dcl = deviceCommandLookupFilledForm.get();

                int stateTransitionId = Integer.parseInt(policyRuleFilledForm.get("stateTransitionId"));
                int deviceTypeId = Integer.parseInt(policyRuleFilledForm.get("deviceTypeId"));
                int samplingRateFactor = Integer.parseInt(policyRuleFilledForm.get("samplingRateFactor"));
                PolicyRule pr = new PolicyRule(stateTransitionId, policyConditionId, deviceTypeId,
                        samplingRateFactor);

                // Update the id, so if this is an update, it will trigger an update when we insert it
                pr.setId(this.updatingId);

                // Make policy rule
                int policyRuleId = pr.insertOrUpdate();

                if (this.updatingId != -1) {
                    // We know we are updating, so we need to remove the old references to this policy rule,
                    //  so we have a clean slate. First, remove the old policy condition
                    PolicyConditionDAO.deletePolicyCondition(this.policyConditionId);
                    this.policyConditionId = -1;

                    // Now remove the old command lookup ids
                    DeviceCommandLookupDAO.deleteCommandLookupsByPolicyRule(policyRuleId);
                }

                // Make commands in reference to the new policy rule
                dcl.setPolicyRuleId(policyRuleId);
                dcl.insertMultiple();

                // Updated, so reset the updating flag.
                this.updatingId = -1;

                return redirect(routes.DBManagementController.dbManagementDeviceTypeView(policyRuleId));
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result editPolicyRule() {
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
        this.updatingId = policyRuleId;
        this.policyConditionId = policyConditionId;
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
            DeviceCommandLookupDAO.deleteCommandLookupsByPolicyRule(policyRuleId);
            boolean isSuccess = PolicyRuleDAO.deletePolicyRule(policyRuleId);
            PolicyConditionDAO.deletePolicyCondition(policyConditionId);
            return ok(Boolean.toString(isSuccess));
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result clearPolicyRuleForm() {
        this.updatingId = -1;
        this.policyConditionId = -1;
        return ok();
    }
}