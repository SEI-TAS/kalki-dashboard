package controllers;

import models.ApplicationDatabase;
import models.Device;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

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

    public Result addDevicePage() {
        return ok(views.html.add.render());
    }

    public Result deviceInfo(int id) {
        return ok(views.html.info.render(id));
    }

    public CompletionStage<Result> addDevice() {
        Device device = formFactory.form(Device.class).bindFromRequest().get();
        return db.addDevice(device).thenApplyAsync(n -> {
            return redirect(routes.HomeController.index());
        }, ec.current());
    }

    public CompletionStage<Result> deleteDevice() {
        String id = formFactory.form().bindFromRequest().get("id");
        return db.deleteDevice(id).thenApplyAsync(n -> {
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

    public CompletionStage<Result> getDevice(int id) throws Exception {
        return db.getDevice(id).thenApplyAsync(json -> {
            return ok(json);
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
