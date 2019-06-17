package controllers;

import edu.cmu.sei.ttg.kalki.models.*;
import edu.cmu.sei.ttg.kalki.database.Postgres;

import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
import play.api.mvc.*;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.inject.Inject;

public class UmboxLookupController extends Controller {
    private final FormFactory formFactory;
    private final HttpExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public UmboxLookupController(FormFactory formFactory, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.updatingId = -1; //if the value is -1, it means there should be a new alertType
    }

    public CompletionStage<Result> getUmboxLookups() {
        return Postgres.findAllUmboxLookups().thenApplyAsync(umboxLookups -> {
            try {
                return ok(ow.writeValueAsString(umboxLookups));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, ec.current());
    }

    public Result editUmboxLookup() {
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

    public Result addOrUpdateUmboxLookup() {
        Form<UmboxLookup> umboxLookupForm = formFactory.form(UmboxLookup.class);
        Form<UmboxLookup> filledForm = umboxLookupForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(views.html.form.render(filledForm));
        } else {
            UmboxLookup ul = filledForm.get();
            if (ul.getUmboxImageId() == null) {  //adding multiple image to dag order relationships
                //convert imageId to dag order map JSON to java map
                String dagOrderJsonString = filledForm.field("imageIdDagOrderMap").getValue().get();
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> imageIdToDagOrderMap = new HashMap<String, String>();

                try {
                    imageIdToDagOrderMap = mapper.readValue(dagOrderJsonString, Map.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Integer stateId = ul.getStateId();
                Integer deviceTypeId = ul.getDeviceTypeId();

                //insert a umbox lookup for each relationship
                for (String umboxImageId : imageIdToDagOrderMap.keySet()) {
                    String dagOrder = imageIdToDagOrderMap.get(umboxImageId);
                    UmboxLookup newLookup = new UmboxLookup(-1, stateId, deviceTypeId, Integer.parseInt(umboxImageId), Integer.parseInt(dagOrder));
                    newLookup.insertOrUpdate();
                }
            } else {  //updating with only a single image and dag order
                ul.setId(this.updatingId);
                ul.insertOrUpdate();
            }
        }

        this.updatingId = -1;

        return redirect(routes.DBManagementController.dbManagementView());
    }


    public CompletionStage<Result> deleteUmboxLookup() {
        String id = formFactory.form().bindFromRequest().get("id");
        int idToInt;
        try {
            idToInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            idToInt = -1;
        }
        return Postgres.deleteUmboxLookup(idToInt).thenApplyAsync(isSuccess -> {
            return ok(isSuccess.toString());
        }, ec.current());
    }

    public Result clearUmboxLookupForm() {
        this.updatingId = -1;
        return ok();
    }
}