// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/crmowry/Projects/kalki-dashboard/dashboard/conf/routes
// @DATE:Thu Jul 12 15:45:55 EDT 2018

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
  // @LINE:9
  UmboxController_1: controllers.UmboxController,
  // @LINE:10
  FuncyController_2: controllers.FuncyController,
  // @LINE:32
  Assets_3: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    HomeController_0: controllers.HomeController,
    // @LINE:7
    DeviceController_4: controllers.DeviceController,
    // @LINE:9
    UmboxController_1: controllers.UmboxController,
    // @LINE:10
    FuncyController_2: controllers.FuncyController,
    // @LINE:32
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
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """edit-device""", """controllers.DeviceController.editDevicePage(id:String ?= "")"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """umbox-setup""", """controllers.UmboxController.umboxSetup()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """funcy-view""", """controllers.FuncyController.funcyView()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """info""", """controllers.DeviceController.deviceInfo(id:String ?= "")"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-device-submit""", """controllers.DeviceController.addDevice()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """edit-device-submit""", """controllers.DeviceController.editDevice()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-group-id""", """controllers.DeviceController.addGroupId()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-type""", """controllers.DeviceController.addType()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-tag""", """controllers.DeviceController.addTag()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-umbox-submit""", """controllers.UmboxController.addUmboxImage()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """edit-umbox-submit""", """controllers.UmboxController.editUmboxImage()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """delete-device""", """controllers.DeviceController.deleteDevice()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """delete-umbox-image""", """controllers.UmboxController.deleteUmboxImage()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """clean""", """controllers.HomeController.clean()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """device""", """controllers.DeviceController.getDevice(id:String ?= "")"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """devices""", """controllers.DeviceController.getDevices()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """group-ids""", """controllers.DeviceController.getGroupIds()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """types""", """controllers.DeviceController.getTypes()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """tags""", """controllers.DeviceController.getTags()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """umbox-images""", """controllers.UmboxController.getUmboxImages()"""),
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
  private[this] lazy val controllers_DeviceController_editDevicePage2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("edit-device")))
  )
  private[this] lazy val controllers_DeviceController_editDevicePage2_invoker = createInvoker(
    DeviceController_4.editDevicePage(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "editDevicePage",
      Seq(classOf[String]),
      "GET",
      this.prefix + """edit-device""",
      """""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_UmboxController_umboxSetup3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("umbox-setup")))
  )
  private[this] lazy val controllers_UmboxController_umboxSetup3_invoker = createInvoker(
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

  // @LINE:10
  private[this] lazy val controllers_FuncyController_funcyView4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("funcy-view")))
  )
  private[this] lazy val controllers_FuncyController_funcyView4_invoker = createInvoker(
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

  // @LINE:11
  private[this] lazy val controllers_DeviceController_deviceInfo5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("info")))
  )
  private[this] lazy val controllers_DeviceController_deviceInfo5_invoker = createInvoker(
    DeviceController_4.deviceInfo(fakeValue[String]),
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

  // @LINE:13
  private[this] lazy val controllers_DeviceController_addDevice6_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-device-submit")))
  )
  private[this] lazy val controllers_DeviceController_addDevice6_invoker = createInvoker(
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

  // @LINE:14
  private[this] lazy val controllers_DeviceController_editDevice7_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("edit-device-submit")))
  )
  private[this] lazy val controllers_DeviceController_editDevice7_invoker = createInvoker(
    DeviceController_4.editDevice(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "editDevice",
      Nil,
      "POST",
      this.prefix + """edit-device-submit""",
      """""",
      Seq()
    )
  )

  // @LINE:15
  private[this] lazy val controllers_DeviceController_addGroupId8_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-group-id")))
  )
  private[this] lazy val controllers_DeviceController_addGroupId8_invoker = createInvoker(
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

  // @LINE:16
  private[this] lazy val controllers_DeviceController_addType9_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-type")))
  )
  private[this] lazy val controllers_DeviceController_addType9_invoker = createInvoker(
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

  // @LINE:17
  private[this] lazy val controllers_DeviceController_addTag10_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-tag")))
  )
  private[this] lazy val controllers_DeviceController_addTag10_invoker = createInvoker(
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

  // @LINE:18
  private[this] lazy val controllers_UmboxController_addUmboxImage11_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-umbox-submit")))
  )
  private[this] lazy val controllers_UmboxController_addUmboxImage11_invoker = createInvoker(
    UmboxController_1.addUmboxImage(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UmboxController",
      "addUmboxImage",
      Nil,
      "POST",
      this.prefix + """add-umbox-submit""",
      """""",
      Seq()
    )
  )

  // @LINE:19
  private[this] lazy val controllers_UmboxController_editUmboxImage12_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("edit-umbox-submit")))
  )
  private[this] lazy val controllers_UmboxController_editUmboxImage12_invoker = createInvoker(
    UmboxController_1.editUmboxImage(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UmboxController",
      "editUmboxImage",
      Nil,
      "POST",
      this.prefix + """edit-umbox-submit""",
      """""",
      Seq()
    )
  )

  // @LINE:20
  private[this] lazy val controllers_DeviceController_deleteDevice13_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("delete-device")))
  )
  private[this] lazy val controllers_DeviceController_deleteDevice13_invoker = createInvoker(
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

  // @LINE:21
  private[this] lazy val controllers_UmboxController_deleteUmboxImage14_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("delete-umbox-image")))
  )
  private[this] lazy val controllers_UmboxController_deleteUmboxImage14_invoker = createInvoker(
    UmboxController_1.deleteUmboxImage(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UmboxController",
      "deleteUmboxImage",
      Nil,
      "POST",
      this.prefix + """delete-umbox-image""",
      """""",
      Seq()
    )
  )

  // @LINE:22
  private[this] lazy val controllers_HomeController_clean15_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("clean")))
  )
  private[this] lazy val controllers_HomeController_clean15_invoker = createInvoker(
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

  // @LINE:24
  private[this] lazy val controllers_DeviceController_getDevice16_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("device")))
  )
  private[this] lazy val controllers_DeviceController_getDevice16_invoker = createInvoker(
    DeviceController_4.getDevice(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "getDevice",
      Seq(classOf[String]),
      "GET",
      this.prefix + """device""",
      """""",
      Seq()
    )
  )

  // @LINE:25
  private[this] lazy val controllers_DeviceController_getDevices17_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("devices")))
  )
  private[this] lazy val controllers_DeviceController_getDevices17_invoker = createInvoker(
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

  // @LINE:26
  private[this] lazy val controllers_DeviceController_getGroupIds18_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("group-ids")))
  )
  private[this] lazy val controllers_DeviceController_getGroupIds18_invoker = createInvoker(
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

  // @LINE:27
  private[this] lazy val controllers_DeviceController_getTypes19_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("types")))
  )
  private[this] lazy val controllers_DeviceController_getTypes19_invoker = createInvoker(
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

  // @LINE:28
  private[this] lazy val controllers_DeviceController_getTags20_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("tags")))
  )
  private[this] lazy val controllers_DeviceController_getTags20_invoker = createInvoker(
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

  // @LINE:29
  private[this] lazy val controllers_UmboxController_getUmboxImages21_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("umbox-images")))
  )
  private[this] lazy val controllers_UmboxController_getUmboxImages21_invoker = createInvoker(
    UmboxController_1.getUmboxImages(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UmboxController",
      "getUmboxImages",
      Nil,
      "GET",
      this.prefix + """umbox-images""",
      """""",
      Seq()
    )
  )

  // @LINE:32
  private[this] lazy val controllers_Assets_versioned22_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned22_invoker = createInvoker(
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
    case controllers_DeviceController_editDevicePage2_route(params@_) =>
      call(params.fromQuery[String]("id", Some(""))) { (id) =>
        controllers_DeviceController_editDevicePage2_invoker.call(DeviceController_4.editDevicePage(id))
      }
  
    // @LINE:9
    case controllers_UmboxController_umboxSetup3_route(params@_) =>
      call { 
        controllers_UmboxController_umboxSetup3_invoker.call(UmboxController_1.umboxSetup())
      }
  
    // @LINE:10
    case controllers_FuncyController_funcyView4_route(params@_) =>
      call { 
        controllers_FuncyController_funcyView4_invoker.call(FuncyController_2.funcyView())
      }
  
    // @LINE:11
    case controllers_DeviceController_deviceInfo5_route(params@_) =>
      call(params.fromQuery[String]("id", Some(""))) { (id) =>
        controllers_DeviceController_deviceInfo5_invoker.call(DeviceController_4.deviceInfo(id))
      }
  
    // @LINE:13
    case controllers_DeviceController_addDevice6_route(params@_) =>
      call { 
        controllers_DeviceController_addDevice6_invoker.call(DeviceController_4.addDevice())
      }
  
    // @LINE:14
    case controllers_DeviceController_editDevice7_route(params@_) =>
      call { 
        controllers_DeviceController_editDevice7_invoker.call(DeviceController_4.editDevice())
      }
  
    // @LINE:15
    case controllers_DeviceController_addGroupId8_route(params@_) =>
      call { 
        controllers_DeviceController_addGroupId8_invoker.call(DeviceController_4.addGroupId())
      }
  
    // @LINE:16
    case controllers_DeviceController_addType9_route(params@_) =>
      call { 
        controllers_DeviceController_addType9_invoker.call(DeviceController_4.addType())
      }
  
    // @LINE:17
    case controllers_DeviceController_addTag10_route(params@_) =>
      call { 
        controllers_DeviceController_addTag10_invoker.call(DeviceController_4.addTag())
      }
  
    // @LINE:18
    case controllers_UmboxController_addUmboxImage11_route(params@_) =>
      call { 
        controllers_UmboxController_addUmboxImage11_invoker.call(UmboxController_1.addUmboxImage())
      }
  
    // @LINE:19
    case controllers_UmboxController_editUmboxImage12_route(params@_) =>
      call { 
        controllers_UmboxController_editUmboxImage12_invoker.call(UmboxController_1.editUmboxImage())
      }
  
    // @LINE:20
    case controllers_DeviceController_deleteDevice13_route(params@_) =>
      call { 
        controllers_DeviceController_deleteDevice13_invoker.call(DeviceController_4.deleteDevice())
      }
  
    // @LINE:21
    case controllers_UmboxController_deleteUmboxImage14_route(params@_) =>
      call { 
        controllers_UmboxController_deleteUmboxImage14_invoker.call(UmboxController_1.deleteUmboxImage())
      }
  
    // @LINE:22
    case controllers_HomeController_clean15_route(params@_) =>
      call { 
        controllers_HomeController_clean15_invoker.call(HomeController_0.clean())
      }
  
    // @LINE:24
    case controllers_DeviceController_getDevice16_route(params@_) =>
      call(params.fromQuery[String]("id", Some(""))) { (id) =>
        controllers_DeviceController_getDevice16_invoker.call(DeviceController_4.getDevice(id))
      }
  
    // @LINE:25
    case controllers_DeviceController_getDevices17_route(params@_) =>
      call { 
        controllers_DeviceController_getDevices17_invoker.call(DeviceController_4.getDevices())
      }
  
    // @LINE:26
    case controllers_DeviceController_getGroupIds18_route(params@_) =>
      call { 
        controllers_DeviceController_getGroupIds18_invoker.call(DeviceController_4.getGroupIds())
      }
  
    // @LINE:27
    case controllers_DeviceController_getTypes19_route(params@_) =>
      call { 
        controllers_DeviceController_getTypes19_invoker.call(DeviceController_4.getTypes())
      }
  
    // @LINE:28
    case controllers_DeviceController_getTags20_route(params@_) =>
      call { 
        controllers_DeviceController_getTags20_invoker.call(DeviceController_4.getTags())
      }
  
    // @LINE:29
    case controllers_UmboxController_getUmboxImages21_route(params@_) =>
      call { 
        controllers_UmboxController_getUmboxImages21_invoker.call(UmboxController_1.getUmboxImages())
      }
  
    // @LINE:32
    case controllers_Assets_versioned22_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned22_invoker.call(Assets_3.versioned(path, file))
      }
  }
}
