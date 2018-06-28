// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/crmowry/Projects/kalki-dashboard/dashboard/conf/routes
// @DATE:Thu Jun 28 15:53:35 EDT 2018

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
  // @LINE:15
  UmboxController_3: controllers.UmboxController,
  // @LINE:24
  Assets_1: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    HomeController_0: controllers.HomeController,
    // @LINE:7
    DeviceController_2: controllers.DeviceController,
    // @LINE:15
    UmboxController_3: controllers.UmboxController,
    // @LINE:24
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
    ("""GET""", this.prefix, """controllers.HomeController.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-device""", """controllers.DeviceController.addDevice()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """info""", """controllers.DeviceController.deviceInfo(id:String ?= "Fake Device")"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-device-submit""", """controllers.DeviceController.submit()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-group-id""", """controllers.DeviceController.addGroupId()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-type""", """controllers.DeviceController.addType()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-tag""", """controllers.DeviceController.addTag()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """clean""", """controllers.DeviceController.clean()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """log-umboxes""", """controllers.UmboxController.logUmboxes()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """log-devices""", """controllers.DeviceController.logDevices()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """devices""", """controllers.DeviceController.getDevices()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """group-ids""", """controllers.DeviceController.getGroupIds()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """types""", """controllers.DeviceController.getTypes()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """tags""", """controllers.DeviceController.getTags()"""),
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
    HomeController_0.index(),
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
    DeviceController_2.addDevice(),
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
  private[this] lazy val controllers_DeviceController_deviceInfo2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("info")))
  )
  private[this] lazy val controllers_DeviceController_deviceInfo2_invoker = createInvoker(
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

  // @LINE:10
  private[this] lazy val controllers_DeviceController_submit3_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-device-submit")))
  )
  private[this] lazy val controllers_DeviceController_submit3_invoker = createInvoker(
    DeviceController_2.submit(),
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

  // @LINE:11
  private[this] lazy val controllers_DeviceController_addGroupId4_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-group-id")))
  )
  private[this] lazy val controllers_DeviceController_addGroupId4_invoker = createInvoker(
    DeviceController_2.addGroupId(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "addGroupId",
      Nil,
      "POST",
      this.prefix + """add-group-id""",
      """""",
      Seq()
    )
  )

  // @LINE:12
  private[this] lazy val controllers_DeviceController_addType5_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-type")))
  )
  private[this] lazy val controllers_DeviceController_addType5_invoker = createInvoker(
    DeviceController_2.addType(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "addType",
      Nil,
      "POST",
      this.prefix + """add-type""",
      """""",
      Seq()
    )
  )

  // @LINE:13
  private[this] lazy val controllers_DeviceController_addTag6_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-tag")))
  )
  private[this] lazy val controllers_DeviceController_addTag6_invoker = createInvoker(
    DeviceController_2.addTag(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "addTag",
      Nil,
      "POST",
      this.prefix + """add-tag""",
      """""",
      Seq()
    )
  )

  // @LINE:14
  private[this] lazy val controllers_DeviceController_clean7_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("clean")))
  )
  private[this] lazy val controllers_DeviceController_clean7_invoker = createInvoker(
    DeviceController_2.clean(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "clean",
      Nil,
      "POST",
      this.prefix + """clean""",
      """""",
      Seq()
    )
  )

  // @LINE:15
  private[this] lazy val controllers_UmboxController_logUmboxes8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("log-umboxes")))
  )
  private[this] lazy val controllers_UmboxController_logUmboxes8_invoker = createInvoker(
    UmboxController_3.logUmboxes(),
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

  // @LINE:16
  private[this] lazy val controllers_DeviceController_logDevices9_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("log-devices")))
  )
  private[this] lazy val controllers_DeviceController_logDevices9_invoker = createInvoker(
    DeviceController_2.logDevices(),
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

  // @LINE:18
  private[this] lazy val controllers_DeviceController_getDevices10_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("devices")))
  )
  private[this] lazy val controllers_DeviceController_getDevices10_invoker = createInvoker(
    DeviceController_2.getDevices(),
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

  // @LINE:19
  private[this] lazy val controllers_DeviceController_getGroupIds11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("group-ids")))
  )
  private[this] lazy val controllers_DeviceController_getGroupIds11_invoker = createInvoker(
    DeviceController_2.getGroupIds(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "getGroupIds",
      Nil,
      "GET",
      this.prefix + """group-ids""",
      """""",
      Seq()
    )
  )

  // @LINE:20
  private[this] lazy val controllers_DeviceController_getTypes12_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("types")))
  )
  private[this] lazy val controllers_DeviceController_getTypes12_invoker = createInvoker(
    DeviceController_2.getTypes(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "getTypes",
      Nil,
      "GET",
      this.prefix + """types""",
      """""",
      Seq()
    )
  )

  // @LINE:21
  private[this] lazy val controllers_DeviceController_getTags13_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("tags")))
  )
  private[this] lazy val controllers_DeviceController_getTags13_invoker = createInvoker(
    DeviceController_2.getTags(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "getTags",
      Nil,
      "GET",
      this.prefix + """tags""",
      """""",
      Seq()
    )
  )

  // @LINE:24
  private[this] lazy val controllers_Assets_versioned14_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned14_invoker = createInvoker(
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
        controllers_HomeController_index0_invoker.call(HomeController_0.index())
      }
  
    // @LINE:7
    case controllers_DeviceController_addDevice1_route(params@_) =>
      call { 
        controllers_DeviceController_addDevice1_invoker.call(DeviceController_2.addDevice())
      }
  
    // @LINE:8
    case controllers_DeviceController_deviceInfo2_route(params@_) =>
      call(params.fromQuery[String]("id", Some("Fake Device"))) { (id) =>
        controllers_DeviceController_deviceInfo2_invoker.call(DeviceController_2.deviceInfo(id))
      }
  
    // @LINE:10
    case controllers_DeviceController_submit3_route(params@_) =>
      call { 
        controllers_DeviceController_submit3_invoker.call(DeviceController_2.submit())
      }
  
    // @LINE:11
    case controllers_DeviceController_addGroupId4_route(params@_) =>
      call { 
        controllers_DeviceController_addGroupId4_invoker.call(DeviceController_2.addGroupId())
      }
  
    // @LINE:12
    case controllers_DeviceController_addType5_route(params@_) =>
      call { 
        controllers_DeviceController_addType5_invoker.call(DeviceController_2.addType())
      }
  
    // @LINE:13
    case controllers_DeviceController_addTag6_route(params@_) =>
      call { 
        controllers_DeviceController_addTag6_invoker.call(DeviceController_2.addTag())
      }
  
    // @LINE:14
    case controllers_DeviceController_clean7_route(params@_) =>
      call { 
        controllers_DeviceController_clean7_invoker.call(DeviceController_2.clean())
      }
  
    // @LINE:15
    case controllers_UmboxController_logUmboxes8_route(params@_) =>
      call { 
        controllers_UmboxController_logUmboxes8_invoker.call(UmboxController_3.logUmboxes())
      }
  
    // @LINE:16
    case controllers_DeviceController_logDevices9_route(params@_) =>
      call { 
        controllers_DeviceController_logDevices9_invoker.call(DeviceController_2.logDevices())
      }
  
    // @LINE:18
    case controllers_DeviceController_getDevices10_route(params@_) =>
      call { 
        controllers_DeviceController_getDevices10_invoker.call(DeviceController_2.getDevices())
      }
  
    // @LINE:19
    case controllers_DeviceController_getGroupIds11_route(params@_) =>
      call { 
        controllers_DeviceController_getGroupIds11_invoker.call(DeviceController_2.getGroupIds())
      }
  
    // @LINE:20
    case controllers_DeviceController_getTypes12_route(params@_) =>
      call { 
        controllers_DeviceController_getTypes12_invoker.call(DeviceController_2.getTypes())
      }
  
    // @LINE:21
    case controllers_DeviceController_getTags13_route(params@_) =>
      call { 
        controllers_DeviceController_getTags13_invoker.call(DeviceController_2.getTags())
      }
  
    // @LINE:24
    case controllers_Assets_versioned14_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned14_invoker.call(Assets_1.versioned(path, file))
      }
  }
}
