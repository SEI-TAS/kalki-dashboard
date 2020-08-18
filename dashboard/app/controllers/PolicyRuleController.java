/*
 * Kalki - A Software-Defined IoT Security Platform
 * Copyright 2020 Carnegie Mellon University.
 * NO WARRANTY. THIS CARNEGIE MELLON UNIVERSITY AND SOFTWARE ENGINEERING INSTITUTE MATERIAL IS FURNISHED ON AN "AS-IS" BASIS. CARNEGIE MELLON UNIVERSITY MAKES NO WARRANTIES OF ANY KIND, EITHER EXPRESSED OR IMPLIED, AS TO ANY MATTER INCLUDING, BUT NOT LIMITED TO, WARRANTY OF FITNESS FOR PURPOSE OR MERCHANTABILITY, EXCLUSIVITY, OR RESULTS OBTAINED FROM USE OF THE MATERIAL. CARNEGIE MELLON UNIVERSITY DOES NOT MAKE ANY WARRANTY OF ANY KIND WITH RESPECT TO FREEDOM FROM PATENT, TRADEMARK, OR COPYRIGHT INFRINGEMENT.
 * Released under a MIT (SEI)-style license, please see license.txt or contact permission@sei.cmu.edu for full terms.
 * [DISTRIBUTION STATEMENT A] This material has been approved for public release and unlimited distribution.  Please see Copyright notice for non-US Government use and distribution.
 * This Software includes and/or makes use of the following Third-Party Software subject to its own license:
 * 1. Google Guava (https://github.com/google/guava) Copyright 2007 The Guava Authors.
 * 2. JSON.simple (https://code.google.com/archive/p/json-simple/) Copyright 2006-2009 Yidong Fang, Chris Nokleberg.
 * 3. JUnit (https://junit.org/junit5/docs/5.0.1/api/overview-summary.html) Copyright 2020 The JUnit Team.
 * 4. Play Framework (https://www.playframework.com/) Copyright 2020 Lightbend Inc..
 * 5. PostgreSQL (https://opensource.org/licenses/postgresql) Copyright 1996-2020 The PostgreSQL Global Development Group.
 * 6. Jackson (https://github.com/FasterXML/jackson-core) Copyright 2013 FasterXML.
 * 7. JSON (https://www.json.org/license.html) Copyright 2002 JSON.org.
 * 8. Apache Commons (https://commons.apache.org/) Copyright 2004 The Apache Software Foundation.
 * 9. RuleBook (https://github.com/deliveredtechnologies/rulebook/blob/develop/LICENSE.txt) Copyright 2020 Delivered Technologies.
 * 10. SLF4J (http://www.slf4j.org/license.html) Copyright 2004-2017 QOS.ch.
 * 11. Eclipse Jetty (https://www.eclipse.org/jetty/licenses.html) Copyright 1995-2020 Mort Bay Consulting Pty Ltd and others..
 * 12. Mockito (https://github.com/mockito/mockito/wiki/License) Copyright 2007 Mockito contributors.
 * 13. SubEtha SMTP (https://github.com/voodoodyne/subethasmtp) Copyright 2006-2007 SubEthaMail.org.
 * 14. JSch - Java Secure Channel (http://www.jcraft.com/jsch/) Copyright 2002-2015 Atsuhiko Yamanaka, JCraft,Inc. .
 * 15. ouimeaux (https://github.com/iancmcc/ouimeaux) Copyright 2014 Ian McCracken.
 * 16. Flask (https://github.com/pallets/flask) Copyright 2010 Pallets.
 * 17. Flask-RESTful (https://github.com/flask-restful/flask-restful) Copyright 2013 Twilio, Inc..
 * 18. libvirt-python (https://github.com/libvirt/libvirt-python) Copyright 2016 RedHat, Fedora project.
 * 19. Requests: HTTP for Humans (https://github.com/psf/requests) Copyright 2019 Kenneth Reitz.
 * 20. netifaces (https://github.com/al45tair/netifaces) Copyright 2007-2018 Alastair Houghton.
 * 21. ipaddress (https://github.com/phihag/ipaddress) Copyright 2001-2014 Python Software Foundation.
 * DM20-0543
 *
 */

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

    public CompletionStage<Result> getPolicyRulesByDeviceId(int deviceId) {
        return CompletableFuture.supplyAsync(() -> {
            List<PolicyRule> policyRules = PolicyRuleDAO.findPolicyRules(deviceId);
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