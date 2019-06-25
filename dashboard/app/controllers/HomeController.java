package controllers;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

import java.util.Scanner;

public class HomeController extends Controller {

    private final HttpExecutionContext ec;

    @Inject
    public HomeController(HttpExecutionContext ec) {
        this.ec = ec;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result setupDatabase() {
        Postgres.setupDatabase();
        return ok();
    }

    public Result resetDatabase() {
        Postgres.resetDatabase();
        return ok();
    }

    public Result listAllDatabases() {
        Postgres.listAllDatabases();
        return ok();
    }

}
