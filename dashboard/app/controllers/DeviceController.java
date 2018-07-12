package controllers;

import models.ApplicationDatabase;
import models.Device;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import java.util.Scanner;

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

    public Result editDevicePage(String id) {
        return ok(views.html.edit.render(id));
    }

    public Result deviceInfo(String id) {
        return ok(views.html.info.render(id));
    }

    public CompletionStage<Result> addDevice() {
        Device device = formFactory.form(Device.class).bindFromRequest().get();
        return db.addDevice(device).thenApplyAsync(n -> {
            return redirect(routes.HomeController.index());
        }, ec.current());
    }

    public CompletionStage<Result> editDevice() {
        Device device = formFactory.form(Device.class).bindFromRequest().get();
        String id = formFactory.form().bindFromRequest().get("id");
        return db.editDevice(device, id).thenApplyAsync(n -> {
            return redirect(routes.HomeController.index());
        }, ec.current());
    }

    public CompletionStage<Result> deleteDevice() {
        String id = formFactory.form().bindFromRequest().get("id");
        return db.deleteById("device", id).thenApplyAsync(n -> {
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> clean() {
        return db.dropAllTables().thenApplyAsync(n -> {
            return redirect(routes.HomeController.index());
        }, ec.current());
    }

    public CompletionStage<Result> getDevice(String id) throws Exception {
        return db.getById("device", id).thenApplyAsync(json -> {
            return ok(json);
        }, ec.current());
    }

    public CompletionStage<Result> getAll(String table) {
        return db.getAllFromTable(table).thenApplyAsync(json -> {
            return ok(json);
        }, ec.current());
    }

    public CompletionStage<Result> getDevices() throws Exception {
        return getAll("device");
    }

    public CompletionStage<Result> getGroupIds() throws Exception {
        return getAll("group_id");
    }

    public CompletionStage<Result> getTypes() throws Exception {
        return getAll("type");
    }

    public CompletionStage<Result> getTags() throws Exception {
        return getAll("tag");
    }

    public CompletionStage<Result> add(String table, String name) {
        String s = formFactory.form().bindFromRequest().get(name);
        return db.addRowToTable(table, s).thenApplyAsync(n -> {
            return redirect(routes.DeviceController.addDevicePage());
        }, ec.current());
    }

    public CompletionStage<Result> addGroupId() {
        return add("group_id", "groupId");
    }

    public CompletionStage<Result> addType() {
        return add("type", "type");
    }

    public CompletionStage<Result> addTag() {
        return add("tag", "tag");
    }

}
