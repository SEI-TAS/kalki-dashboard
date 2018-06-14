package controllers;

import models.ApplicationDatabase;
import models.Umbox;
import models.Device;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

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

    public CompletionStage<Result> submit() {
        Device device = null;
        //device = formFactory.form(Device.class).bindFromRequest().get();
        return db.addDevice(device).thenApplyAsync(n -> {
            return redirect(routes.HomeController.index());
        }, ec.current());
    }

    public CompletionStage<Result> clean() {
        return db.dropAllTables().thenApplyAsync(n -> {
            return redirect(routes.DeviceController.addDevice());
        }, ec.current());
    }

    public CompletionStage<Result> logUmboxes() {
        return db.logUmboxes().thenApplyAsync(n -> {
            return redirect(routes.DeviceController.addDevice());
        }, ec.current());
    }

}
