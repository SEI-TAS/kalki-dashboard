// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/crmowry/Projects/kalki-dashboard/dashboard/conf/routes
// @DATE:Thu Jul 05 13:32:44 EDT 2018

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers {

  // @LINE:28
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:28
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }

  // @LINE:8
  class ReverseUmboxController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:18
    def logUmboxes(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "log-umboxes")
    }
  
    // @LINE:8
    def umboxSetup(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "umbox-setup")
    }
  
  }

  // @LINE:6
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:17
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

  
    // @LINE:21
    def getDevice(id:Integer = -1): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "device" + play.core.routing.queryString(List(if(id == -1) None else Some(implicitly[play.api.mvc.QueryStringBindable[Integer]].unbind("id", id)))))
    }
  
    // @LINE:13
    def addGroupId(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-group-id")
    }
  
    // @LINE:10
    def deviceInfo(id:Integer = -1): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "info" + play.core.routing.queryString(List(if(id == -1) None else Some(implicitly[play.api.mvc.QueryStringBindable[Integer]].unbind("id", id)))))
    }
  
    // @LINE:19
    def logDevices(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "log-devices")
    }
  
    // @LINE:15
    def addTag(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-tag")
    }
  
    // @LINE:24
    def getTypes(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "types")
    }
  
    // @LINE:16
    def deleteDevice(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "delete-device")
    }
  
    // @LINE:7
    def addDevicePage(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "add-device")
    }
  
    // @LINE:12
    def addDevice(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-device-submit")
    }
  
    // @LINE:23
    def getGroupIds(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "group-ids")
    }
  
    // @LINE:25
    def getTags(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "tags")
    }
  
    // @LINE:22
    def getDevices(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "devices")
    }
  
    // @LINE:14
    def addType(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-type")
    }
  
  }

  // @LINE:9
  class ReverseFuncyController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:9
    def funcyView(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "funcy-view")
    }
  
  }


}
