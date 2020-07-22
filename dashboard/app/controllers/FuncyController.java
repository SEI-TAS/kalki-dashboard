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

import helper.*;

import akka.NotUsed;
//import akka.actor.Cancellable;
import akka.stream.*;
import akka.stream.javadsl.*;
import akka.util.ByteString;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.DataOutputStream;
import java.lang.IllegalArgumentException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;

import play.data.FormFactory;
import play.data.Form;
import play.libs.concurrent.HttpExecutionContext;
import java.util.concurrent.CompletionStage;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import scala.concurrent.Future;

public class FuncyController extends Controller {
    private final FormFactory formFactory;
    private final HttpExecutionContext executionContext;
    private final ObjectWriter objectWriter;
    private final Materializer materializer;

    private Set<String> portList;
    private MonitoringValues monitoringValues;
    private SerialReader serialReader;

    @Inject
    public FuncyController(FormFactory formFactory, HttpExecutionContext ec, Materializer mz) {
        this.formFactory = formFactory;
        this.executionContext = ec;
        this.materializer = mz;
        this.monitoringValues = new MonitoringValues();
        this.objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.serialReader = new SerialReader();
    }

    public Result funcyView() {
        return ok(views.html.funcy.render());
    }

    public Result selectMonitoringValues() throws Exception {
        Form<MonitoringValues> filledForm = formFactory.form(MonitoringValues.class).bindFromRequest();

        if (filledForm.hasErrors()) {
            return badRequest(views.html.form.render(filledForm));
        } else {
            this.monitoringValues = filledForm.get();
            try {
                this.serialReader = new SerialReader(this.monitoringValues);
                return redirect(routes.FuncyController.funcyView());
            } catch (Exception e) {
                System.out.println("Bad request");
                e.printStackTrace();
                this.monitoringValues = new MonitoringValues();
                this.serialReader = new SerialReader();
                return status(400, "There was an error connecting to serial device.");
            }
        }
    }

    public Result getMonitoringValues() throws Exception {
        try {
            return ok(objectWriter.writeValueAsString(this.monitoringValues));
        } catch (JsonProcessingException e) {

        }
        return ok();
    }

    public Result getAvailableSerialPorts() {
        try {
            return ok(objectWriter.writeValueAsString(this.serialReader.getAvailableSerialPorts()));
        } catch (JsonProcessingException e) {
        }
        return ok();
    }

    public Result getBaudRates() {
        int[] baud = {110, 300, 600, 1200, 2400, 4800, 9600, 14400, 19200, 38400, 57600, 115200, 128000, 256000};
        try {
            return ok(objectWriter.writeValueAsString(baud));
        } catch (JsonProcessingException e) {
        }
        return ok();
    }

    public Result closeInputStream() {
        try {
            this.serialReader.disconnect();
            this.monitoringValues = new MonitoringValues();
            this.serialReader = new SerialReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok();
    }

    public WebSocket socket() {
        return WebSocket.Binary.accept(requestHeader -> {
            // sink for data (client requesting socket)
            Sink<ByteString, ?> sink = Sink.ignore();

            // source of data (serial device)
            Source<ByteString, CompletionStage<IOResult>> source = StreamConverters.fromInputStream(() -> {
                return this.serialReader.getInputStream();
            });

            // create graph from source
            RunnableGraph<Source<ByteString, NotUsed>> runnableGraph = source.toMat(BroadcastHub.of(ByteString.class, 256), Keep.right());

            // create data source from graph
            Source<ByteString, NotUsed> fromSource = runnableGraph.run(this.materializer);

            //create flow from data source to sink
            Flow<ByteString, ByteString, UniqueKillSwitch> flow = Flow.fromSinkAndSourceCoupled(sink, fromSource).joinMat(KillSwitches.singleBidi(), Keep.right());

            return flow;
        });
    }

}