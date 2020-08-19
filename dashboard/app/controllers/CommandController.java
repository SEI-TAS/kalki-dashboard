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

import edu.cmu.sei.kalki.db.models.*;
import edu.cmu.sei.kalki.db.daos.DeviceCommandDAO;
import edu.cmu.sei.kalki.db.daos.DeviceCommandLookupDAO;
import edu.cmu.sei.kalki.db.daos.DeviceDAO;

import models.DatabaseExecutionContext;
import play.libs.concurrent.HttpExecution;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import javax.inject.Inject;

import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

public class CommandController extends Controller {
    private final FormFactory formFactory;
    private final DatabaseExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public CommandController(FormFactory formFactory, DatabaseExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new alertType
    }

    public CompletionStage<Result> getCommand(int id) {
        return CompletableFuture.supplyAsync(() -> {
            DeviceCommand command = DeviceCommandDAO.findCommand(id);
            try {
                return ok(ow.writeValueAsString(command));
            } catch (JsonProcessingException e) {
                return ok();
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getCommands() {
        return CompletableFuture.supplyAsync(() -> {
            List<DeviceCommand> commands = DeviceCommandDAO.findAllCommands();
            try {
                return ok(ow.writeValueAsString(commands));
            } catch (JsonProcessingException e) {
                return ok();
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getCommandsByDeviceType(int id) {
        return CompletableFuture.supplyAsync(() -> {
            List<DeviceCommand> commands = DeviceCommandDAO.findAllCommandsByDeviceType(id);
            try {
                return ok(ow.writeValueAsString(commands));
            } catch (JsonProcessingException e) {
                return ok();
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getCommandsByDevice(int id) {
        return CompletableFuture.supplyAsync(() -> {
            Device device = DeviceDAO.findDevice(id);
            System.out.println(device);
            List<DeviceCommand> commands = new ArrayList<>();
            if(device != null) {
                List<DeviceCommandLookup> lookups = DeviceCommandLookupDAO.findCommandLookupsByDevice(device.getId());
                for(DeviceCommandLookup cl: lookups){
                    commands.add(DeviceCommandDAO.findCommand(cl.getCommandId()));
                }
            }
            else {
                commands = Collections.emptyList();
            }

            try {
                return ok(ow.writeValueAsString(commands));
            } catch (JsonProcessingException e) {
                return ok();
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result editCommand() {
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

    public CompletionStage<Result> addOrUpdateCommand() {
        return  CompletableFuture.supplyAsync(() -> {
            Form<DeviceCommand> commandForm = formFactory.form(DeviceCommand.class);
            Form<DeviceCommand> filledForm = commandForm.bindFromRequest();

            if (filledForm.hasErrors()) {
                return badRequest(views.html.form.render(filledForm));
            } else {
                DeviceCommand command = filledForm.get();

                command.setId(this.updatingId);
                this.updatingId = -1;

                int n = command.insertOrUpdate();
                return redirect(routes.DBManagementController.dbManagementDeviceTypeView(n));
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> deleteCommand() {
        return  CompletableFuture.supplyAsync(() -> {
            String id = formFactory.form().bindFromRequest().get("id");
            int idToInt;
            try {
                idToInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                idToInt = -1;
            }

            Boolean isSuccess = DeviceCommandDAO.deleteCommand(idToInt);
            return ok(isSuccess.toString());
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result clearCommandForm() {
        this.updatingId = -1;
        return ok();
    }
}
