package controllers;

import models.ApplicationDatabase;
import models.Umbox;
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
public class HomeController extends Controller {

    private final FormFactory formFactory;
    private final ApplicationDatabase db;
    private final HttpExecutionContext ec;

    @Inject
    public HomeController(FormFactory formFactory, ApplicationDatabase db, HttpExecutionContext ec) {
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
    public Result index() {
        return ok(views.html.index.render());
    }

    public CompletionStage<Result> addUmbox() {
        Umbox umbox = formFactory.form(Umbox.class).bindFromRequest().get();
        return db.addUmbox(umbox).thenApplyAsync(n -> {
            return redirect(routes.HomeController.index());
        }, ec.current());
    }

    public CompletionStage<Result> clean() {
        return db.dropAllTables().thenApplyAsync(n -> {
            return redirect(routes.HomeController.index());
        }, ec.current());
    }

    public CompletionStage<Result> logUmboxes() {
        return db.logUmboxes().thenApplyAsync(n -> {
            return redirect(routes.HomeController.index());
        }, ec.current());
    }

}
