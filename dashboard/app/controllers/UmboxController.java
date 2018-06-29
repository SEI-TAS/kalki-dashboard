package controllers;

import models.ApplicationDatabase;
import models.Umbox;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class UmboxController extends Controller {

    private final FormFactory formFactory;
    private final ApplicationDatabase db;
    private final HttpExecutionContext ec;

    @Inject
    public UmboxController(FormFactory formFactory, ApplicationDatabase db, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.db = db;
        this.ec = ec;
    }

    public Result umboxSetup() {
        return ok(views.html.umbox.render());
    }

    public CompletionStage<Result> logUmboxes() {
        return db.logUmboxes().thenApplyAsync(n -> {
            return redirect(routes.HomeController.index());
        }, ec.current());
    }

}
