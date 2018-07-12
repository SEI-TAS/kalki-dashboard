package controllers;

import models.ApplicationDatabase;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class HomeController extends Controller {

    private final ApplicationDatabase db;
    private final HttpExecutionContext ec;

    @Inject
    public HomeController(ApplicationDatabase db, HttpExecutionContext ec) {
        this.db = db;
        this.ec = ec;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public CompletionStage<Result> clean() {
        return db.dropAllTables().thenApplyAsync(n -> {
            return redirect(routes.HomeController.index());
        }, ec.current());
    }

}
