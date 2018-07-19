package controllers;

import kalkidb.database.Postgres;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class HomeController extends Controller {

    private final HttpExecutionContext ec;

    @Inject
    public HomeController(HttpExecutionContext ec) {
        Postgres.initialize("127.0.0.1", "5432", "kalki", "kalki", "kalki");
        this.ec = ec;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public CompletionStage<Result> clean() {
        return Postgres.dropTables().thenApplyAsync(n -> {
            return redirect(routes.HomeController.index());
        }, ec.current());
    }

}
