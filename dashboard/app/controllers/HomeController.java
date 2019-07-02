package controllers;

import play.mvc.Controller;
import play.mvc.Result;


public class HomeController extends Controller {

    public HomeController() {
    }

    public Result index() {
        return ok(views.html.index.render());
    }
}
