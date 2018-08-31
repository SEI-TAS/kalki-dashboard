package controllers;

import kalkidb.database.Postgres;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;

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

    public CompletionStage<Result> setupDatabase() {
        return CompletableFuture.runAsync(() -> {Postgres.setupDatabase();}).thenApplyAsync(n -> {
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> resetDatabase() {
        return CompletableFuture.runAsync(() -> {Postgres.resetDatabase();}).thenApplyAsync(n -> {
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> listAllDatabases() {
        return Postgres.listAllDatabases().thenApplyAsync(n -> {
            return ok();
        }, ec.current());
    }

}
