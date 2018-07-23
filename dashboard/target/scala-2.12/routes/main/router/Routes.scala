// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/crmowry/Projects/kalki-dashboard/dashboard/conf/routes
// @DATE:Fri Jul 20 15:14:35 EDT 2018

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
  // @LINE:34
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
    // @LINE:34
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
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-or-edit-device""", """controllers.DeviceController.addOrEditDevice()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-group""", """controllers.DeviceController.addGroup()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-type""", """controllers.DeviceController.addType()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-tag""", """controllers.DeviceController.addTag()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """delete-device""", """controllers.DeviceController.deleteDevice()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """add-umbox-submit""", """controllers.UmboxController.addUmboxImage()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """edit-umbox-submit""", """controllers.UmboxController.editUmboxImage()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """delete-umbox-image""", """controllers.UmboxController.deleteUmboxImage()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """setup-database""", """controllers.HomeController.setupDatabase()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """reset-database""", """controllers.HomeController.resetDatabase()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """list-all-databases""", """controllers.HomeController.listAllDatabases()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """initialize""", """controllers.HomeController.initialize()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """device""", """controllers.DeviceController.getDevice(id:String ?= "")"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """devices""", """controllers.DeviceController.getDevices()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """groups""", """controllers.DeviceController.getGroups()"""),
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
  private[this] lazy val controllers_DeviceController_addOrEditDevice6_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-or-edit-device")))
  )
  private[this] lazy val controllers_DeviceController_addOrEditDevice6_invoker = createInvoker(
    DeviceController_4.addOrEditDevice(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "addOrEditDevice",
      Nil,
      "POST",
      this.prefix + """add-or-edit-device""",
      """""",
      Seq()
    )
  )

  // @LINE:14
  private[this] lazy val controllers_DeviceController_addGroup7_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-group")))
  )
  private[this] lazy val controllers_DeviceController_addGroup7_invoker = createInvoker(
    DeviceController_4.addGroup(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "addGroup",
      Nil,
      "POST",
      this.prefix + """add-group""",
      """""",
      Seq()
    )
  )

  // @LINE:15
  private[this] lazy val controllers_DeviceController_addType8_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-type")))
  )
  private[this] lazy val controllers_DeviceController_addType8_invoker = createInvoker(
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

  // @LINE:16
  private[this] lazy val controllers_DeviceController_addTag9_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("add-tag")))
  )
  private[this] lazy val controllers_DeviceController_addTag9_invoker = createInvoker(
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

  // @LINE:17
  private[this] lazy val controllers_DeviceController_deleteDevice10_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("delete-device")))
  )
  private[this] lazy val controllers_DeviceController_deleteDevice10_invoker = createInvoker(
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
  private[this] lazy val controllers_UmboxController_deleteUmboxImage13_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("delete-umbox-image")))
  )
  private[this] lazy val controllers_UmboxController_deleteUmboxImage13_invoker = createInvoker(
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

  // @LINE:21
  private[this] lazy val controllers_HomeController_setupDatabase14_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("setup-database")))
  )
  private[this] lazy val controllers_HomeController_setupDatabase14_invoker = createInvoker(
    HomeController_0.setupDatabase(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "setupDatabase",
      Nil,
      "POST",
      this.prefix + """setup-database""",
      """""",
      Seq()
    )
  )

  // @LINE:22
  private[this] lazy val controllers_HomeController_resetDatabase15_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("reset-database")))
  )
  private[this] lazy val controllers_HomeController_resetDatabase15_invoker = createInvoker(
    HomeController_0.resetDatabase(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "resetDatabase",
      Nil,
      "POST",
      this.prefix + """reset-database""",
      """""",
      Seq()
    )
  )

  // @LINE:23
  private[this] lazy val controllers_HomeController_listAllDatabases16_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("list-all-databases")))
  )
  private[this] lazy val controllers_HomeController_listAllDatabases16_invoker = createInvoker(
    HomeController_0.listAllDatabases(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "listAllDatabases",
      Nil,
      "POST",
      this.prefix + """list-all-databases""",
      """""",
      Seq()
    )
  )

  // @LINE:24
  private[this] lazy val controllers_HomeController_initialize17_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("initialize")))
  )
  private[this] lazy val controllers_HomeController_initialize17_invoker = createInvoker(
    HomeController_0.initialize(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "initialize",
      Nil,
      "POST",
      this.prefix + """initialize""",
      """""",
      Seq()
    )
  )

  // @LINE:26
  private[this] lazy val controllers_DeviceController_getDevice18_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("device")))
  )
  private[this] lazy val controllers_DeviceController_getDevice18_invoker = createInvoker(
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

  // @LINE:27
  private[this] lazy val controllers_DeviceController_getDevices19_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("devices")))
  )
  private[this] lazy val controllers_DeviceController_getDevices19_invoker = createInvoker(
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

  // @LINE:28
  private[this] lazy val controllers_DeviceController_getGroups20_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("groups")))
  )
  private[this] lazy val controllers_DeviceController_getGroups20_invoker = createInvoker(
    DeviceController_4.getGroups(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.DeviceController",
      "getGroups",
      Nil,
      "GET",
      this.prefix + """groups""",
      """""",
      Seq()
    )
  )

  // @LINE:29
  private[this] lazy val controllers_DeviceController_getTypes21_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("types")))
  )
  private[this] lazy val controllers_DeviceController_getTypes21_invoker = createInvoker(
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

  // @LINE:30
  private[this] lazy val controllers_DeviceController_getTags22_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("tags")))
  )
  private[this] lazy val controllers_DeviceController_getTags22_invoker = createInvoker(
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

  // @LINE:31
  private[this] lazy val controllers_UmboxController_getUmboxImages23_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("umbox-images")))
  )
  private[this] lazy val controllers_UmboxController_getUmboxImages23_invoker = createInvoker(
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

  // @LINE:34
  private[this] lazy val controllers_Assets_versioned24_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned24_invoker = createInvoker(
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
    case controllers_DeviceController_addOrEditDevice6_route(params@_) =>
      call { 
        controllers_DeviceController_addOrEditDevice6_invoker.call(DeviceController_4.addOrEditDevice())
      }
  
    // @LINE:14
    case controllers_DeviceController_addGroup7_route(params@_) =>
      call { 
        controllers_DeviceController_addGroup7_invoker.call(DeviceController_4.addGroup())
      }
  
    // @LINE:15
    case controllers_DeviceController_addType8_route(params@_) =>
      call { 
        controllers_DeviceController_addType8_invoker.call(DeviceController_4.addType())
      }
  
    // @LINE:16
    case controllers_DeviceController_addTag9_route(params@_) =>
      call { 
        controllers_DeviceController_addTag9_invoker.call(DeviceController_4.addTag())
      }
  
    // @LINE:17
    case controllers_DeviceController_deleteDevice10_route(params@_) =>
      call { 
        controllers_DeviceController_deleteDevice10_invoker.call(DeviceController_4.deleteDevice())
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
    case controllers_UmboxController_deleteUmboxImage13_route(params@_) =>
      call { 
        controllers_UmboxController_deleteUmboxImage13_invoker.call(UmboxController_1.deleteUmboxImage())
      }
  
    // @LINE:21
    case controllers_HomeController_setupDatabase14_route(params@_) =>
      call { 
        controllers_HomeController_setupDatabase14_invoker.call(HomeController_0.setupDatabase())
      }
  
    // @LINE:22
    case controllers_HomeController_resetDatabase15_route(params@_) =>
      call { 
        controllers_HomeController_resetDatabase15_invoker.call(HomeController_0.resetDatabase())
      }
  
    // @LINE:23
    case controllers_HomeController_listAllDatabases16_route(params@_) =>
      call { 
        controllers_HomeController_listAllDatabases16_invoker.call(HomeController_0.listAllDatabases())
      }
  
    // @LINE:24
    case controllers_HomeController_initialize17_route(params@_) =>
      call { 
        controllers_HomeController_initialize17_invoker.call(HomeController_0.initialize())
      }
  
    // @LINE:26
    case controllers_DeviceController_getDevice18_route(params@_) =>
      call(params.fromQuery[String]("id", Some(""))) { (id) =>
        controllers_DeviceController_getDevice18_invoker.call(DeviceController_4.getDevice(id))
      }
  
    // @LINE:27
    case controllers_DeviceController_getDevices19_route(params@_) =>
      call { 
        controllers_DeviceController_getDevices19_invoker.call(DeviceController_4.getDevices())
      }
  
    // @LINE:28
    case controllers_DeviceController_getGroups20_route(params@_) =>
      call { 
        controllers_DeviceController_getGroups20_invoker.call(DeviceController_4.getGroups())
      }
  
    // @LINE:29
    case controllers_DeviceController_getTypes21_route(params@_) =>
      call { 
        controllers_DeviceController_getTypes21_invoker.call(DeviceController_4.getTypes())
      }
  
    // @LINE:30
    case controllers_DeviceController_getTags22_route(params@_) =>
      call { 
        controllers_DeviceController_getTags22_invoker.call(DeviceController_4.getTags())
      }
  
    // @LINE:31
    case controllers_UmboxController_getUmboxImages23_route(params@_) =>
      call { 
        controllers_UmboxController_getUmboxImages23_invoker.call(UmboxController_1.getUmboxImages())
      }
  
    // @LINE:34
    case controllers_Assets_versioned24_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned24_invoker.call(Assets_3.versioned(path, file))
      }
  }
}
