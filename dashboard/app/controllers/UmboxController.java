package controllers;

import models.ApplicationDatabase;
import models.UmboxImage;
import models.UmboxContainer;
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

    public CompletionStage<Result> addUmboxImage() {
        UmboxImage u = formFactory.form(UmboxImage.class).bindFromRequest().get();
        return db.addUmboxImage(u).thenApplyAsync(n -> {
            return redirect(routes.UmboxController.umboxSetup());
        }, ec.current());
    }

    public CompletionStage<Result> editUmboxImage() {
        UmboxImage u = formFactory.form(UmboxImage.class).bindFromRequest().get();
        String id = formFactory.form().bindFromRequest().get("id");
        return db.editUmboxImage(u, id).thenApplyAsync(n -> {
            return redirect(routes.UmboxController.umboxSetup());
        }, ec.current());
    }

    public CompletionStage<Result> deleteUmboxImage() {
        String id = formFactory.form().bindFromRequest().get("id");
        return db.deleteById("umbox_image", id).thenApplyAsync(n -> {
            return ok();
        }, ec.current());
    }

    public CompletionStage<Result> getAll(String table) {
        return db.getAllFromTable(table).thenApplyAsync(json -> {
            return ok(json);
        }, ec.current());
    }

    public CompletionStage<Result> getUmboxImages() throws Exception {
        return getAll("umbox_image");
    }

}
