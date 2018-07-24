package controllers;

import kalkidb.models.UmboxImage;
import kalkidb.models.UmboxContainer;
import kalkidb.database.Postgres;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

public class UmboxController extends Controller {

    private final FormFactory formFactory;
    private final HttpExecutionContext ec;
    private final ObjectWriter ow;

    @Inject
    public UmboxController(FormFactory formFactory, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    public Result umboxSetup() {
        return ok(views.html.umbox.render());
    }

    public CompletionStage<Result> addUmboxImage() {
        UmboxImage u = formFactory.form(UmboxImage.class).bindFromRequest().get();
        return Postgres.addUmboxImage(u).thenApplyAsync(n -> {
            return redirect(routes.UmboxController.umboxSetup());
        }, ec.current());
    }

    public CompletionStage<Result> editUmboxImage() {
        UmboxImage u = formFactory.form(UmboxImage.class).bindFromRequest().get();
//        String id = formFactory.form().bindFromRequest().get("id");
        return Postgres.editUmboxImage(u).thenApplyAsync(n -> {
            return redirect(routes.UmboxController.umboxSetup());
        }, ec.current());
    }

    public CompletionStage<Result> deleteUmboxImage() {
        String id = formFactory.form().bindFromRequest().get("id");
        int idToInt;
        try {
            idToInt = Integer.parseInt(id);
        }
        catch (NumberFormatException e) {
            idToInt = -1;
        }
        return Postgres.deleteById("umbox_image", idToInt).thenApplyAsync(isSuccess -> {
            if(isSuccess) {
                return ok();
            } else {
                return internalServerError();
            }
        }, ec.current());
    }

    public CompletionStage<Result> getUmboxImages() throws Exception {
        return Postgres.getAllUmboxImages().thenApplyAsync(list -> {
            try {
                return ok(ow.writeValueAsString(list));
            }
            catch(JsonProcessingException e) {

            }
            return ok();
        }, ec.current());
    }

}
