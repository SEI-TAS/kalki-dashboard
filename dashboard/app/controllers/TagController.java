package controllers;

import edu.cmu.sei.ttg.kalki.models.*;
import edu.cmu.sei.ttg.kalki.database.Postgres;

import models.DatabaseExecutionContext;
import play.libs.concurrent.HttpExecution;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
import play.api.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

public class TagController extends Controller {
    private final FormFactory formFactory;
    private final DatabaseExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public TagController(FormFactory formFactory, DatabaseExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new alertType
    }

    public CompletionStage<Result> getTags() {
        return CompletableFuture.supplyAsync(() -> {
            List<Tag> tags = Postgres.findAllTags();
            try {
                return ok(ow.writeValueAsString(tags));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getTagsByDevice(int id) {
        return CompletableFuture.supplyAsync(() -> {
            List<Tag> tags = Postgres.findTagsByDevice(id);
            try {
                return ok(ow.writeValueAsString(tags));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result editTag() {
        String id = formFactory.form().bindFromRequest().get("id");
        int idToInt;
        try {
            idToInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            idToInt = -1;
        }
        this.updatingId = idToInt;
        return ok();
    }

    public CompletionStage<Result> addOrUpdateTag() {
        return CompletableFuture.supplyAsync(() -> {
            Form<Tag> tagForm = formFactory.form(Tag.class);
            Form<Tag> filledForm = tagForm.bindFromRequest();
            if (filledForm.hasErrors()) {
                return badRequest(views.html.form.render(filledForm));
            } else {
                Tag dt = filledForm.get();
                dt.setId(this.updatingId);
                this.updatingId = -1;

                int n = dt.insertOrUpdate();
                return redirect(routes.DBManagementController.dbManagementView(n));
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> deleteTag() {
        return CompletableFuture.supplyAsync(() -> {
            String id = formFactory.form().bindFromRequest().get("id");
            int idToInt;
            try {
                idToInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                idToInt = -1;
            }
            Boolean isSuccess = Postgres.deleteTag(idToInt);
            return ok(isSuccess.toString());
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result clearTagForm() {
        this.updatingId = -1;
        return ok();
    }
}