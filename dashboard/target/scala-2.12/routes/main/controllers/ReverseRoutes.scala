// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/crmowry/Projects/kalki-dashboard/dashboard/conf/routes
// @DATE:Fri Jul 20 15:14:35 EDT 2018

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers {

  // @LINE:34
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:34
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }

  // @LINE:9
  class ReverseUmboxController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:19
    def editUmboxImage(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "edit-umbox-submit")
    }
  
    // @LINE:9
    def umboxSetup(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "umbox-setup")
    }
  
    // @LINE:31
    def getUmboxImages(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "umbox-images")
    }
  
    // @LINE:18
    def addUmboxImage(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-umbox-submit")
    }
  
    // @LINE:20
    def deleteUmboxImage(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "delete-umbox-image")
    }
  
  }

  // @LINE:6
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:21
    def setupDatabase(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "setup-database")
    }
  
    // @LINE:23
    def listAllDatabases(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "list-all-databases")
    }
  
    // @LINE:24
    def initialize(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "initialize")
    }
  
    // @LINE:22
    def resetDatabase(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "reset-database")
    }
  
    // @LINE:6
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:7
  class ReverseDeviceController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:14
    def addGroup(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-group")
    }
  
    // @LINE:8
    def editDevicePage(id:String = ""): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "edit-device" + play.core.routing.queryString(List(if(id == "") None else Some(implicitly[play.api.mvc.QueryStringBindable[String]].unbind("id", id)))))
    }
  
    // @LINE:26
    def getDevice(id:String = ""): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "device" + play.core.routing.queryString(List(if(id == "") None else Some(implicitly[play.api.mvc.QueryStringBindable[String]].unbind("id", id)))))
    }
  
    // @LINE:16
    def addTag(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-tag")
    }
  
    // @LINE:28
    def getGroups(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "groups")
    }
  
    // @LINE:29
    def getTypes(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "types")
    }
  
    // @LINE:17
    def deleteDevice(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "delete-device")
    }
  
    // @LINE:7
    def addDevicePage(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "add-device")
    }
  
    // @LINE:11
    def deviceInfo(id:String = ""): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "info" + play.core.routing.queryString(List(if(id == "") None else Some(implicitly[play.api.mvc.QueryStringBindable[String]].unbind("id", id)))))
    }
  
    // @LINE:13
    def addOrEditDevice(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-or-edit-device")
    }
  
    // @LINE:30
    def getTags(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "tags")
    }
  
    // @LINE:27
    def getDevices(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "devices")
    }
  
    // @LINE:15
    def addType(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-type")
    }
  
  }

  // @LINE:10
  class ReverseFuncyController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def funcyView(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "funcy-view")
    }
  
  }


}
