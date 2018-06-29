// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/crmowry/Projects/kalki-dashboard/dashboard/conf/routes
// @DATE:Fri Jun 29 16:54:59 EDT 2018

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseAssets Assets = new controllers.ReverseAssets(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseUmboxController UmboxController = new controllers.ReverseUmboxController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseHomeController HomeController = new controllers.ReverseHomeController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseDeviceController DeviceController = new controllers.ReverseDeviceController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseFuncyController FuncyController = new controllers.ReverseFuncyController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseAssets Assets = new controllers.javascript.ReverseAssets(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseUmboxController UmboxController = new controllers.javascript.ReverseUmboxController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseHomeController HomeController = new controllers.javascript.ReverseHomeController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseDeviceController DeviceController = new controllers.javascript.ReverseDeviceController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseFuncyController FuncyController = new controllers.javascript.ReverseFuncyController(RoutesPrefix.byNamePrefix());
  }

}
