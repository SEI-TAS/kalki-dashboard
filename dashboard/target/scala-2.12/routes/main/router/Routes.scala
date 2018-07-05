// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/crmowry/Projects/kalki-dashboard/dashboard/conf/routes
// @DATE:Thu Jul 05 13:32:44 EDT 2018

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
  DeviceController_4: controllers.DeviceController,
  // @LINE:8
  UmboxController_1: controllers.UmboxController,
  // @LINE:9
  FuncyController_2: controllers.FuncyController,
  // @LINE:28
  Assets_3: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    HomeController_0: controllers.HomeController,
    // @LINE:7
    DeviceController_4: controllers.DeviceController,
    // @LINE:8
    UmboxController_1: controllers.UmboxController,
    // @LINE:9
    FuncyController_2: controllers.FuncyController,
    // @LINE:28
    Assets_3: controllers.Assets
  ) = this(errorHandler, HomeController_0, DeviceController_4, UmboxController_1, FuncyController_2, Assets_3, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_0, DeviceController_4, UmboxController_1, FuncyController_2, Assets_3, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-device""", """controllers.DeviceController.addDevicePage()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """umbox-setup""", """controllers.UmboxController.umboxSetup()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """funcy-view""", """controllers.FuncyController.funcyView()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """info""", """controllers.DeviceController.deviceInfo(id:Integer ?= -1)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-device-submit""", """controllers.DeviceController.addDevice()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-group-id""", """controllers.DeviceController.addGroupId()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-type""", """controllers.DeviceController.addType()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-tag""", """controllers.DeviceController.addTag()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """delete-device""", """controllers.DeviceController.deleteDevice()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """clean""", """controllers.HomeController.clean()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """log-umboxes""", """controllers.UmboxController.logUmboxes()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """log-devices""", """controllers.DeviceController.logDevices()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """device""", """controllers.DeviceController.getDevice(id:Integer ?= -1)"""),
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
  private[this] lazy val controllers_DeviceController_addDevicePage1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-device")))
  )
  private[this] lazy val controllers_DeviceController_addDevicePage1_invoker = createInvoker(
    DeviceController_4.addDevicePage(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "addDevicePage",
      Nil,
      "GET",
      this.prefix + """add-device""",
      """""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val controllers_UmboxController_umboxSetup2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("umbox-setup")))
  )
  private[this] lazy val controllers_UmboxController_umboxSetup2_invoker = createInvoker(
    UmboxController_1.umboxSetup(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UmboxController",
      "umboxSetup",
      Nil,
      "GET",
      this.prefix + """umbox-setup""",
      """""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_FuncyController_funcyView3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("funcy-view")))
  )
  private[this] lazy val controllers_FuncyController_funcyView3_invoker = createInvoker(
    FuncyController_2.funcyView(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.FuncyController",
      "funcyView",
      Nil,
      "GET",
      this.prefix + """funcy-view""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val controllers_DeviceController_deviceInfo4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("info")))
  )
  private[this] lazy val controllers_DeviceController_deviceInfo4_invoker = createInvoker(
    DeviceController_4.deviceInfo(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "deviceInfo",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """info""",
      """""",
      Seq()
    )
  )

  // @LINE:12
  private[this] lazy val controllers_DeviceController_addDevice5_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-device-submit")))
  )
  private[this] lazy val controllers_DeviceController_addDevice5_invoker = createInvoker(
    DeviceController_4.addDevice(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "addDevice",
      Nil,
      "POST",
      this.prefix + """add-device-submit""",
      """""",
      Seq()
    )
  )

  // @LINE:13
  private[this] lazy val controllers_DeviceController_addGroupId6_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-group-id")))
  )
  private[this] lazy val controllers_DeviceController_addGroupId6_invoker = createInvoker(
    DeviceController_4.addGroupId(),
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

  // @LINE:14
  private[this] lazy val controllers_DeviceController_addType7_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-type")))
  )
  private[this] lazy val controllers_DeviceController_addType7_invoker = createInvoker(
    DeviceController_4.addType(),
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

  // @LINE:15
  private[this] lazy val controllers_DeviceController_addTag8_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-tag")))
  )
  private[this] lazy val controllers_DeviceController_addTag8_invoker = createInvoker(
    DeviceController_4.addTag(),
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

  // @LINE:16
  private[this] lazy val controllers_DeviceController_deleteDevice9_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("delete-device")))
  )
  private[this] lazy val controllers_DeviceController_deleteDevice9_invoker = createInvoker(
    DeviceController_4.deleteDevice(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "deleteDevice",
      Nil,
      "POST",
      this.prefix + """delete-device""",
      """""",
      Seq()
    )
  )

  // @LINE:17
  private[this] lazy val controllers_HomeController_clean10_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("clean")))
  )
  private[this] lazy val controllers_HomeController_clean10_invoker = createInvoker(
    HomeController_0.clean(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "clean",
      Nil,
      "POST",
      this.prefix + """clean""",
      """""",
      Seq()
    )
  )

  // @LINE:18
  private[this] lazy val controllers_UmboxController_logUmboxes11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("log-umboxes")))
  )
  private[this] lazy val controllers_UmboxController_logUmboxes11_invoker = createInvoker(
    UmboxController_1.logUmboxes(),
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

  // @LINE:19
  private[this] lazy val controllers_DeviceController_logDevices12_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("log-devices")))
  )
  private[this] lazy val controllers_DeviceController_logDevices12_invoker = createInvoker(
    DeviceController_4.logDevices(),
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

  // @LINE:21
  private[this] lazy val controllers_DeviceController_getDevice13_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("device")))
  )
  private[this] lazy val controllers_DeviceController_getDevice13_invoker = createInvoker(
    DeviceController_4.getDevice(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "getDevice",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """device""",
      """""",
      Seq()
    )
  )

  // @LINE:22
  private[this] lazy val controllers_DeviceController_getDevices14_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("devices")))
  )
  private[this] lazy val controllers_DeviceController_getDevices14_invoker = createInvoker(
    DeviceController_4.getDevices(),
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

  // @LINE:23
  private[this] lazy val controllers_DeviceController_getGroupIds15_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("group-ids")))
  )
  private[this] lazy val controllers_DeviceController_getGroupIds15_invoker = createInvoker(
    DeviceController_4.getGroupIds(),
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

  // @LINE:24
  private[this] lazy val controllers_DeviceController_getTypes16_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("types")))
  )
  private[this] lazy val controllers_DeviceController_getTypes16_invoker = createInvoker(
    DeviceController_4.getTypes(),
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

  // @LINE:25
  private[this] lazy val controllers_DeviceController_getTags17_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("tags")))
  )
  private[this] lazy val controllers_DeviceController_getTags17_invoker = createInvoker(
    DeviceController_4.getTags(),
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

  // @LINE:28
  private[this] lazy val controllers_Assets_versioned18_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned18_invoker = createInvoker(
    Assets_3.versioned(fakeValue[String], fakeValue[Asset]),
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
    case controllers_DeviceController_addDevicePage1_route(params@_) =>
      call { 
        controllers_DeviceController_addDevicePage1_invoker.call(DeviceController_4.addDevicePage())
      }
  
    // @LINE:8
    case controllers_UmboxController_umboxSetup2_route(params@_) =>
      call { 
        controllers_UmboxController_umboxSetup2_invoker.call(UmboxController_1.umboxSetup())
      }
  
    // @LINE:9
    case controllers_FuncyController_funcyView3_route(params@_) =>
      call { 
        controllers_FuncyController_funcyView3_invoker.call(FuncyController_2.funcyView())
      }
  
    // @LINE:10
    case controllers_DeviceController_deviceInfo4_route(params@_) =>
      call(params.fromQuery[Integer]("id", Some(-1))) { (id) =>
        controllers_DeviceController_deviceInfo4_invoker.call(DeviceController_4.deviceInfo(id))
      }
  
    // @LINE:12
    case controllers_DeviceController_addDevice5_route(params@_) =>
      call { 
        controllers_DeviceController_addDevice5_invoker.call(DeviceController_4.addDevice())
      }
  
    // @LINE:13
    case controllers_DeviceController_addGroupId6_route(params@_) =>
      call { 
        controllers_DeviceController_addGroupId6_invoker.call(DeviceController_4.addGroupId())
      }
  
    // @LINE:14
    case controllers_DeviceController_addType7_route(params@_) =>
      call { 
        controllers_DeviceController_addType7_invoker.call(DeviceController_4.addType())
      }
  
    // @LINE:15
    case controllers_DeviceController_addTag8_route(params@_) =>
      call { 
        controllers_DeviceController_addTag8_invoker.call(DeviceController_4.addTag())
      }
  
    // @LINE:16
    case controllers_DeviceController_deleteDevice9_route(params@_) =>
      call { 
        controllers_DeviceController_deleteDevice9_invoker.call(DeviceController_4.deleteDevice())
      }
  
    // @LINE:17
    case controllers_HomeController_clean10_route(params@_) =>
      call { 
        controllers_HomeController_clean10_invoker.call(HomeController_0.clean())
      }
  
    // @LINE:18
    case controllers_UmboxController_logUmboxes11_route(params@_) =>
      call { 
        controllers_UmboxController_logUmboxes11_invoker.call(UmboxController_1.logUmboxes())
      }
  
    // @LINE:19
    case controllers_DeviceController_logDevices12_route(params@_) =>
      call { 
        controllers_DeviceController_logDevices12_invoker.call(DeviceController_4.logDevices())
      }
  
    // @LINE:21
    case controllers_DeviceController_getDevice13_route(params@_) =>
      call(params.fromQuery[Integer]("id", Some(-1))) { (id) =>
        controllers_DeviceController_getDevice13_invoker.call(DeviceController_4.getDevice(id))
      }
  
    // @LINE:22
    case controllers_DeviceController_getDevices14_route(params@_) =>
      call { 
        controllers_DeviceController_getDevices14_invoker.call(DeviceController_4.getDevices())
      }
  
    // @LINE:23
    case controllers_DeviceController_getGroupIds15_route(params@_) =>
      call { 
        controllers_DeviceController_getGroupIds15_invoker.call(DeviceController_4.getGroupIds())
      }
  
    // @LINE:24
    case controllers_DeviceController_getTypes16_route(params@_) =>
      call { 
        controllers_DeviceController_getTypes16_invoker.call(DeviceController_4.getTypes())
      }
  
    // @LINE:25
    case controllers_DeviceController_getTags17_route(params@_) =>
      call { 
        controllers_DeviceController_getTags17_invoker.call(DeviceController_4.getTags())
      }
  
    // @LINE:28
    case controllers_Assets_versioned18_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned18_invoker.call(Assets_3.versioned(path, file))
      }
  }
}
