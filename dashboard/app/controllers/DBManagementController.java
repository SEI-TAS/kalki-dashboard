package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class DBManagementController extends Controller {

    public DBManagementController() {

    }

    public Result dbManagementView() {
        return ok(views.html.dbManagement.dbManagementScreen.render());
    }
}