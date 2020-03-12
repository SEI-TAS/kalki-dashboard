package controllers;

import edu.cmu.sei.kalki.db.models.*;
import edu.cmu.sei.kalki.db.notifications.AsyncNotificationStorage;

import models.DatabaseExecutionContext;
import play.libs.concurrent.HttpExecution;

import java.lang.OutOfMemoryError;
import java.lang.SecurityException;
import java.lang.InterruptedException;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;

import play.data.*;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class NotificationController extends Controller {

    private final FormFactory formFactory;
    private final DatabaseExecutionContext ec;
    private final ObjectWriter ow;
    private int updatingId;

    @Inject
    public NotificationController(FormFactory formFactory, DatabaseExecutionContext ec) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    public CompletionStage<Result> startListener() {
        return CompletableFuture.supplyAsync(() -> {
            AsyncNotificationStorage.startListener();
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> stopListener() {
        return CompletableFuture.supplyAsync(() -> {
            AsyncNotificationStorage.stopListener();
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getNewAlerts() {
        return CompletableFuture.supplyAsync(() -> {
            List<Alert> newAlerts = AsyncNotificationStorage.getNewAlerts();
            try {
                return ok(ow.writeValueAsString(newAlerts));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getNewStates() {
        return CompletableFuture.supplyAsync(() -> {
            List<DeviceSecurityState> newStates = AsyncNotificationStorage.getNewStates();
            try {
                return ok(ow.writeValueAsString(newStates));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }

    public CompletionStage<Result> getNewStatuses() {
        return CompletableFuture.supplyAsync(() -> {
            List<DeviceStatus> newStatuses = AsyncNotificationStorage.getNewStatuses();
            try {
                return ok(ow.writeValueAsString(newStatuses));
            } catch (JsonProcessingException e) {
            }
            return ok();
        }, HttpExecution.fromThread((java.util.concurrent.Executor) ec));
    }
}
