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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;

import play.data.FormFactory;
import play.data.Form;
import play.libs.concurrent.HttpExecutionContext;
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

    public CompletionStage<Result> selectMonitoringValues() throws Exception {
        Form<MonitoringValues> filledForm = formFactory.form(MonitoringValues.class).bindFromRequest();

        if(filledForm.hasErrors()){
            return CompletableFuture.supplyAsync(()-> { return badRequest(views.html.form.render(filledForm)); });
        } else {
            this.monitoringValues = filledForm.get();
            try{
                this.serialReader = new SerialReader(this.monitoringValues);
                return CompletableFuture.supplyAsync(() -> { return redirect(routes.FuncyController.funcyView()); });
            } catch (Exception e) {
                System.out.println("Bad request");
                e.printStackTrace();
                this.monitoringValues = new MonitoringValues();
                this.serialReader = new SerialReader();
                return CompletableFuture.supplyAsync(() -> { return status(400, "There was an error connecting to serial device."); });
            }
        }
    }

    public CompletionStage<Result> getMonitoringValues() throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            try{
                return ok(objectWriter.writeValueAsString(this.monitoringValues));
            }
            catch (JsonProcessingException e){

            }
            return ok();
        });
    }

    public CompletionStage<Result> getAvailableSerialPorts() {
        return CompletableFuture.supplyAsync(() -> {
            try{
                return ok(objectWriter.writeValueAsString(this.serialReader.getAvailableSerialPorts()));
            }
            catch (JsonProcessingException e){ }
            return ok();
        });
    }

    public CompletionStage<Result> getBaudRates() {
        int[] baud = {110, 300, 600, 1200, 2400, 4800, 9600, 14400, 19200, 38400, 57600, 115200, 128000, 256000};
        return CompletableFuture.supplyAsync(() -> {
            try{
                return ok(objectWriter.writeValueAsString(baud));
            }
            catch (JsonProcessingException e){ }
            return ok();
        });
    }

    public CompletionStage<Result> closeInputStream(){
        return CompletableFuture.supplyAsync(() -> {
            try {
                this.serialReader.disconnect();
                this.monitoringValues = new MonitoringValues();
                this.serialReader = new SerialReader();
            } catch(IOException e) {
                e.printStackTrace();
            }
            return ok();
        });
    }

    public WebSocket socket() {
        return WebSocket.Binary.accept(requestHeader -> {
            // sink for data (client requesting socket)
            Sink<ByteString, ?> sink = Sink.ignore();

            // source of data (serial device)
            Source<ByteString, CompletionStage<IOResult>> source = StreamConverters.fromInputStream(()-> {
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