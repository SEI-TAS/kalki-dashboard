// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/crmowry/Projects/kalki-dashboard/dashboard/conf/routes
// @DATE:Thu Jun 21 17:45:27 EDT 2018

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  HomeController_0: controllers.HomeController,
  // @LINE:7
  DeviceController_2: controllers.DeviceController,
  // @LINE:10
  UmboxController_3: controllers.UmboxController,
  // @LINE:16
  Assets_1: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    HomeController_0: controllers.HomeController,
    // @LINE:7
    DeviceController_2: controllers.DeviceController,
    // @LINE:10
    UmboxController_3: controllers.UmboxController,
    // @LINE:16
    Assets_1: controllers.Assets
  ) = this(errorHandler, HomeController_0, DeviceController_2, UmboxController_3, Assets_1, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_0, DeviceController_2, UmboxController_3, Assets_1, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-device""", """controllers.DeviceController.addDevice"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-device-submit""", """controllers.DeviceController.submit"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """clean""", """controllers.DeviceController.clean"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """log-umboxes""", """controllers.UmboxController.logUmboxes"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """log-devices""", """controllers.DeviceController.logDevices"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """devices""", """controllers.DeviceController.getDevices"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """info""", """controllers.DeviceController.deviceInfo(id:String ?= "Fake Device")"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index0_invoker = createInvoker(
    HomeController_0.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ An example controller showing a sample home page""",
      Seq()
    )
  )

  // @LINE:7
  private[this] lazy val controllers_DeviceController_addDevice1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-device")))
  )
  private[this] lazy val controllers_DeviceController_addDevice1_invoker = createInvoker(
    DeviceController_2.addDevice,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "addDevice",
      Nil,
      "GET",
      this.prefix + """add-device""",
      """""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val controllers_DeviceController_submit2_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-device-submit")))
  )
  private[this] lazy val controllers_DeviceController_submit2_invoker = createInvoker(
    DeviceController_2.submit,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "submit",
      Nil,
      "POST",
      this.prefix + """add-device-submit""",
      """""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_DeviceController_clean3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("clean")))
  )
  private[this] lazy val controllers_DeviceController_clean3_invoker = createInvoker(
    DeviceController_2.clean,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "clean",
      Nil,
      "GET",
      this.prefix + """clean""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val controllers_UmboxController_logUmboxes4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("log-umboxes")))
  )
  private[this] lazy val controllers_UmboxController_logUmboxes4_invoker = createInvoker(
    UmboxController_3.logUmboxes,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UmboxController",
      "logUmboxes",
      Nil,
      "GET",
      this.prefix + """log-umboxes""",
      """""",
      Seq()
    )
  )

  // @LINE:11
  private[this] lazy val controllers_DeviceController_logDevices5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("log-devices")))
  )
  private[this] lazy val controllers_DeviceController_logDevices5_invoker = createInvoker(
    DeviceController_2.logDevices,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "logDevices",
      Nil,
      "GET",
      this.prefix + """log-devices""",
      """""",
      Seq()
    )
  )

  // @LINE:12
  private[this] lazy val controllers_DeviceController_getDevices6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("devices")))
  )
  private[this] lazy val controllers_DeviceController_getDevices6_invoker = createInvoker(
    DeviceController_2.getDevices,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "getDevices",
      Nil,
      "GET",
      this.prefix + """devices""",
      """""",
      Seq()
    )
  )

  // @LINE:13
  private[this] lazy val controllers_DeviceController_deviceInfo7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("info")))
  )
  private[this] lazy val controllers_DeviceController_deviceInfo7_invoker = createInvoker(
    DeviceController_2.deviceInfo(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "deviceInfo",
      Seq(classOf[String]),
      "GET",
      this.prefix + """info""",
      """""",
      Seq()
    )
  )

  // @LINE:16
  private[this] lazy val controllers_Assets_versioned8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned8_invoker = createInvoker(
    Assets_1.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_0.index)
      }
  
    // @LINE:7
    case controllers_DeviceController_addDevice1_route(params@_) =>
      call { 
        controllers_DeviceController_addDevice1_invoker.call(DeviceController_2.addDevice)
      }
  
    // @LINE:8
    case controllers_DeviceController_submit2_route(params@_) =>
      call { 
        controllers_DeviceController_submit2_invoker.call(DeviceController_2.submit)
      }
  
    // @LINE:9
    case controllers_DeviceController_clean3_route(params@_) =>
      call { 
        controllers_DeviceController_clean3_invoker.call(DeviceController_2.clean)
      }
  
    // @LINE:10
    case controllers_UmboxController_logUmboxes4_route(params@_) =>
      call { 
        controllers_UmboxController_logUmboxes4_invoker.call(UmboxController_3.logUmboxes)
      }
  
    // @LINE:11
    case controllers_DeviceController_logDevices5_route(params@_) =>
      call { 
        controllers_DeviceController_logDevices5_invoker.call(DeviceController_2.logDevices)
      }
  
    // @LINE:12
    case controllers_DeviceController_getDevices6_route(params@_) =>
      call { 
        controllers_DeviceController_getDevices6_invoker.call(DeviceController_2.getDevices)
      }
  
    // @LINE:13
    case controllers_DeviceController_deviceInfo7_route(params@_) =>
      call(params.fromQuery[String]("id", Some("Fake Device"))) { (id) =>
        controllers_DeviceController_deviceInfo7_invoker.call(DeviceController_2.deviceInfo(id))
      }
  
    // @LINE:16
    case controllers_Assets_versioned8_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned8_invoker.call(Assets_1.versioned(path, file))
      }
  }
}
