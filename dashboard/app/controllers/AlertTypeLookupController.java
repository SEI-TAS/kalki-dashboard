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

import edu.cmu.sei.kalki.db.daos.AlertConditionDAO;
import edu.cmu.sei.kalki.db.models.*;
import edu.cmu.sei.kalki.db.daos.AlertTypeLookupDAO;

import models.DatabaseExecutionContext;
import play.libs.concurrent.HttpExecution;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

public class AlertTypeLookupController extends Controller {
    private final FormFactory formFactory;
    private final DatabaseExecutionContext ec;
    private final ObjectWriter ow;

    // Intermediate class used to automatically bind multiple conditions to list from the form, since complex objects
    // can't be automatically mapped by the Play framework.
    public static class TempConditionListClass {
        private List<Integer> alertConditionIds = new ArrayList<>();
        private List<Integer> alertConditionSensors = new ArrayList<>();
        private List<Integer> alertConditionStatuses = new ArrayList<>();
        private List<String> alertConditionCalculations = new ArrayList<>();
        private List<String> alertConditionComparisons = new ArrayList<>();
        private List<String> alertConditionThesholds = new ArrayList<String>();

        public List<Integer> getAlertConditionIds() {
            return alertConditionIds;
        }

        public void setAlertConditionIds(List<Integer> alertConditionIds) {
            this.alertConditionIds = alertConditionIds;
        }

        public List<Integer> getAlertConditionSensors() {
            return alertConditionSensors;
        }

        public void setAlertConditionSensors(List<Integer> alertConditionSensors) {
            this.alertConditionSensors = alertConditionSensors;
        }

        public List<Integer> getAlertConditionStatuses() {
            return alertConditionStatuses;
        }

        public void setAlertConditionStatuses(List<Integer> alertConditionStatuses) {
            this.alertConditionStatuses = alertConditionStatuses;
        }

        public List<String> getAlertConditionCalculations() {
            return alertConditionCalculations;
        }

        public void setAlertConditionCalculations(List<String> alertConditionCalculations) {
            this.alertConditionCalculations = alertConditionCalculations;
        }

        public List<String> getAlertConditionComparisons() {
            return alertConditionComparisons;
        }

        public void setAlertConditionComparisons(List<String> alertConditionComparisons) {
            this.alertConditionComparisons = alertConditionComparisons;
        }

        public List<String> getAlertConditionThesholds() {
            return alertConditionThesholds;
        }

        public void setAlertConditionThesholds(List<String> alertConditionThesholds) {
            this.alertConditionThesholds = alertConditionThesholds;
        }
    }

    @Inject
    public AlertTypeLookupController(FormFactory formFactory, DatabaseExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    public CompletionStage<Result> getAlertTypeLookups() {
        return CompletableFuture.supplyAsync(() -> {
            List<AlertTypeLookup> alertTypeLookups = AlertTypeLookupDAO.findAllAlertTypeLookups();
            try {
                return ok(ow.writeValueAsString(alertTypeLookups));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getAlertTypeLookupsByDeviceType(int id) {
        return CompletableFuture.supplyAsync(() -> {
            List<AlertTypeLookup> alertTypeLookups = AlertTypeLookupDAO.findAlertTypeLookupsByDeviceType(id);
            try {
                return ok(ow.writeValueAsString(alertTypeLookups));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> addOrUpdateAlertTypeLookup() {
        return CompletableFuture.supplyAsync(() -> {
            Form<AlertTypeLookup> filledForm = formFactory.form(AlertTypeLookup.class).bindFromRequest();
            Form<AlertContext> alertContextFormData = formFactory.form(AlertContext.class).bindFromRequest();
            Form<TempConditionListClass> conditionsFormData = formFactory.form(TempConditionListClass.class).bindFromRequest();
            DynamicForm generalForm = formFactory.form().bindFromRequest();

            if (filledForm.hasErrors()) {
                return badRequest(views.html.form.render(filledForm));
            } else {
                AlertTypeLookup atl = filledForm.get();
                atl.insertOrUpdate();

                // LogicalOperator is automatically set, but we must manually set alid since 1) it may be a new insert,
                // and 2) it will simply come as "id" as AlertTypeLookup is the main form.
                AlertContext alertContext = alertContextFormData.get();
                alertContext.setAlertTypeLookupId(atl.getId());

                // We must manually get the alertContext id since by default we have set the AlertTypeLookup as our id.
                alertContext.setId(Integer.parseInt(generalForm.get("idContext")));

                // Insert or update and get conditions, if any.
                alertContext.insertOrUpdate();
                List<AlertCondition> storedConditions = AlertConditionDAO.findAlertConditionsForContext(alertContext.getId());
                alertContext.setConditions(storedConditions);

                // Get condition structures.
                TempConditionListClass formConditions = conditionsFormData.get();

                // Check that every condition that was already in the DB came back in the form. If one condition was not
                // found, that means it was removed and we have to delete it from the DB.
                for(AlertCondition storedCondition : alertContext.getConditions()) {
                    if(!formConditions.getAlertConditionIds().contains(storedCondition.getId())) {
                        AlertConditionDAO.deleteAlertCondition(storedCondition.getId());
                    }
                }

                // Now add new conditions. Only those with an id of 0 are new.
                for(int i=0; i<formConditions.getAlertConditionIds().size(); i++) {
                    int formConditionId = formConditions.getAlertConditionIds().get(i);
                    if(formConditionId == 0) {
                        AlertCondition newCondition = new AlertCondition();
                        newCondition.setId(formConditionId);
                        newCondition.setContextId(alertContext.getId());
                        newCondition.setAttributeId(formConditions.getAlertConditionSensors().get(i));
                        newCondition.setNumStatues(formConditions.getAlertConditionStatuses().get(i));
                        newCondition.setCalculation(formConditions.getAlertConditionCalculations().get(i));
                        newCondition.setCompOperator(formConditions.getAlertConditionComparisons().get(i));
                        newCondition.setThresholdValue(formConditions.getAlertConditionThesholds().get(i));
                        newCondition.insert();
                    }
                }

                return redirect(routes.DBManagementController.dbManagementDeviceTypeView(atl.getDeviceTypeId()));
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> deleteAlertTypeLookup() {
        return CompletableFuture.supplyAsync(() -> {
            String id = formFactory.form().bindFromRequest().get("id");
            int idToInt;
            try {
                idToInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                idToInt = -1;
            }
            Boolean isSuccess = AlertTypeLookupDAO.deleteAlertTypeLookup(idToInt);
            return ok(isSuccess.toString());
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }
}
