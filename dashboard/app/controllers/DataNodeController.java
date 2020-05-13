package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import edu.cmu.sei.kalki.db.daos.DataNodeDAO;
import edu.cmu.sei.kalki.db.models.DataNode;
import models.DatabaseExecutionContext;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecution;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class DataNodeController extends Controller {
    private final FormFactory formFactory;
    private final DatabaseExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public DataNodeController(FormFactory formFactory, DatabaseExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new alertType
    }

    public CompletionStage<Result> getDataNode(int id) {
        return CompletableFuture.supplyAsync(() -> {
            DataNode DataNode = DataNodeDAO.findDataNode(id);
            try {
                return ok(ow.writeValueAsString(DataNode));
            } catch (JsonProcessingException e) {
                return redirect(routes.DBManagementController.dbManagementView(-1));
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getDataNodes() {
        return CompletableFuture.supplyAsync(() -> {
            List<DataNode> DataNodes = DataNodeDAO.findAllDataNodes();
            try {
                return ok(ow.writeValueAsString(DataNodes));
            } catch (JsonProcessingException e) {
                return redirect(routes.DBManagementController.dbManagementView(-1));
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result editDataNode() {
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

    public CompletionStage<Result> addOrUpdateDataNode() {
        return CompletableFuture.supplyAsync(() -> {
            Form<DataNode> DataNodeForm = formFactory.form(DataNode.class);
            Form<DataNode> filledForm = DataNodeForm.bindFromRequest();
            if (filledForm.hasErrors()) {
                return badRequest(views.html.form.render(filledForm));
            } else {
                DataNode dt = filledForm.get();
                dt.setId(this.updatingId);
                this.updatingId = -1;

                int n = dt.insertOrUpdate();
                return redirect(routes.DBManagementController.dbManagementView(n));
            }
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> deleteDataNode() {
        return CompletableFuture.supplyAsync(() -> {
            String id = formFactory.form().bindFromRequest().get("id");
            int idToInt;
            try {
                idToInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                idToInt = -1;
            }
            Boolean isSuccess = DataNodeDAO.deleteDataNode(idToInt);
            return ok(isSuccess.toString());
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public Result clearDataNodeForm() {
        this.updatingId = -1;
        return ok();
    }
}
