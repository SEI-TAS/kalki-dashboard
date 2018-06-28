package controllers;

import models.ApplicationDatabase;
import models.Device;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
//import play.libs.Json;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import java.util.Scanner;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class DeviceController extends Controller {

    private final FormFactory formFactory;
    private final ApplicationDatabase db;
    private final HttpExecutionContext ec;

    @Inject
    public DeviceController(FormFactory formFactory, ApplicationDatabase db, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.db = db;
        this.ec = ec;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result addDevice() {
        return ok(views.html.add.render());
    }

    public Result deviceInfo(String id) {
        return ok(views.html.info.render(id));
    }

    public CompletionStage<Result> submit() {
        Device device;
        device = formFactory.form(Device.class).bindFromRequest().get();
        return db.addDevice(device).thenApplyAsync(n -> {
            return redirect(routes.HomeController.index());
        }, ec.current());
    }

    public CompletionStage<Result> clean() {
        return db.dropAllTables().thenApplyAsync(n -> {
            return redirect(routes.HomeController.index());
        }, ec.current());
    }

    public CompletionStage<Result> logDevices() {
        return db.logDevices().thenApplyAsync(n -> {
            return redirect(routes.HomeController.index());
        }, ec.current());
    }

    public CompletionStage<Result> getDevices() throws Exception {
        return db.getDevices().thenApplyAsync(json -> {
            return ok(json);
        }, ec.current());
    }

    public CompletionStage<Result> getGroupIds() throws Exception {
        return db.getGroupIds().thenApplyAsync(json -> {
            return ok(json);
        }, ec.current());
    }

    public CompletionStage<Result> getTypes() throws Exception {
        return db.getTypes().thenApplyAsync(json -> {
            return ok(json);
        }, ec.current());
    }

    public CompletionStage<Result> getTags() throws Exception {
        return db.getTags().thenApplyAsync(json -> {
            return ok(json);
        }, ec.current());
    }

    public CompletionStage<Result> addGroupId() {
        String groupId = formFactory.form().bindFromRequest().get("groupId");
        return db.addGroupId(groupId).thenApplyAsync(n -> {
            return redirect(routes.DeviceController.addDevice());
        }, ec.current());
    }

    public CompletionStage<Result> addType() {
        String type = formFactory.form().bindFromRequest().get("type");
        return db.addType(type).thenApplyAsync(n -> {
            return redirect(routes.DeviceController.addDevice());
        }, ec.current());
    }

    public CompletionStage<Result> addTag() {
        String tag = formFactory.form().bindFromRequest().get("tag");
        return db.addTag(tag).thenApplyAsync(n -> {
            return redirect(routes.DeviceController.addDevice());
        }, ec.current());
    }

}
