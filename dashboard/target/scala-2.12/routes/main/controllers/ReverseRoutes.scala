// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/crmowry/Projects/kalki-dashboard/dashboard/conf/routes
// @DATE:Thu Jul 12 15:45:55 EDT 2018

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers {

  // @LINE:32
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:32
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
  
    // @LINE:29
    def getUmboxImages(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "umbox-images")
    }
  
    // @LINE:18
    def addUmboxImage(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-umbox-submit")
    }
  
    // @LINE:21
    def deleteUmboxImage(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "delete-umbox-image")
    }
  
  }

  // @LINE:6
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:22
    def clean(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "clean")
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

  
    // @LINE:8
    def editDevicePage(id:String = ""): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "edit-device" + play.core.routing.queryString(List(if(id == "") None else Some(implicitly[play.api.mvc.QueryStringBindable[String]].unbind("id", id)))))
    }
  
    // @LINE:24
    def getDevice(id:String = ""): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "device" + play.core.routing.queryString(List(if(id == "") None else Some(implicitly[play.api.mvc.QueryStringBindable[String]].unbind("id", id)))))
    }
  
    // @LINE:15
    def addGroupId(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-group-id")
    }
  
    // @LINE:17
    def addTag(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-tag")
    }
  
    // @LINE:27
    def getTypes(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "types")
    }
  
    // @LINE:20
    def deleteDevice(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "delete-device")
    }
  
    // @LINE:7
    def addDevicePage(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "add-device")
    }
  
    // @LINE:14
    def editDevice(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "edit-device-submit")
    }
  
    // @LINE:13
    def addDevice(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-device-submit")
    }
  
    // @LINE:11
    def deviceInfo(id:String = ""): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "info" + play.core.routing.queryString(List(if(id == "") None else Some(implicitly[play.api.mvc.QueryStringBindable[String]].unbind("id", id)))))
    }
  
    // @LINE:26
    def getGroupIds(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "group-ids")
    }
  
    // @LINE:28
    def getTags(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "tags")
    }
  
    // @LINE:25
    def getDevices(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "devices")
    }
  
    // @LINE:16
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
