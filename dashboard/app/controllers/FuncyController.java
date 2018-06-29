package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class FuncyController extends Controller {

    public Result funcyView() {
        return ok(views.html.funcy.render());
    }

}