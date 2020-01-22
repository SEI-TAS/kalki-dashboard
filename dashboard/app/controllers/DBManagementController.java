package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class DBManagementController extends Controller {

    public DBManagementController() {

    }

    public Result dbManagementOtherView(int insertId) {
        return ok(views.html.dbManagement.dbManagementScreen.render(insertId));
    }

    public Result dbManagementDeviceTypeView(int insertId) {
        return ok(views.html.dbManagement.deviceTypeManagement.render(insertId));
    }
}
