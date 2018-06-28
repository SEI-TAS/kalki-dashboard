// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/crmowry/Projects/kalki-dashboard/dashboard/conf/routes
// @DATE:Thu Jun 28 15:53:35 EDT 2018

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers {

  // @LINE:15
  class ReverseUmboxController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:15
    def logUmboxes(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "log-umboxes")
    }
  
  }

  // @LINE:6
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
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

  
    // @LINE:11
    def addGroupId(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-group-id")
    }
  
    // @LINE:16
    def logDevices(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "log-devices")
    }
  
    // @LINE:13
    def addTag(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-tag")
    }
  
    // @LINE:10
    def submit(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-device-submit")
    }
  
    // @LINE:20
    def getTypes(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "types")
    }
  
    // @LINE:14
    def clean(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "clean")
    }
  
    // @LINE:7
    def addDevice(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "add-device")
    }
  
    // @LINE:8
    def deviceInfo(id:String = "Fake Device"): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "info" + play.core.routing.queryString(List(if(id == "Fake Device") None else Some(implicitly[play.api.mvc.QueryStringBindable[String]].unbind("id", id)))))
    }
  
    // @LINE:19
    def getGroupIds(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "group-ids")
    }
  
    // @LINE:21
    def getTags(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "tags")
    }
  
    // @LINE:18
    def getDevices(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "devices")
    }
  
    // @LINE:12
    def addType(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "add-type")
    }
  
  }

  // @LINE:24
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:24
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }


}
