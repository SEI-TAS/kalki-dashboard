// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/crmowry/Projects/kalki-dashboard/dashboard/conf/routes
// @DATE:Wed Jun 27 11:11:15 EDT 2018

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers.javascript {

  // @LINE:10
  class ReverseUmboxController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def logUmboxes: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UmboxController.logUmboxes",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "log-umboxes"})
        }
      """
    )
  
  }

  // @LINE:6
  class ReverseHomeController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
  }

  // @LINE:7
  class ReverseDeviceController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:12
    def logDevices: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.logDevices",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "log-devices"})
        }
      """
    )
  
    // @LINE:8
    def submit: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.submit",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add-device-submit"})
        }
      """
    )
  
    // @LINE:15
    def getTypes: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getTypes",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "types"})
        }
      """
    )
  
    // @LINE:9
    def clean: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.clean",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "clean"})
        }
      """
    )
  
    // @LINE:7
    def addDevice: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.addDevice",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "add-device"})
        }
      """
    )
  
    // @LINE:11
    def deviceInfo: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.deviceInfo",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "info" + _qS([(id0 == null ? null : (""" + implicitly[play.api.mvc.QueryStringBindable[String]].javascriptUnbind + """)("id", id0))])})
        }
      """
    )
  
    // @LINE:14
    def getGroupIds: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getGroupIds",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "group-ids"})
        }
      """
    )
  
    // @LINE:16
    def getTags: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getTags",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "tags"})
        }
      """
    )
  
    // @LINE:13
    def getDevices: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getDevices",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "devices"})
        }
      """
    )
  
  }

  // @LINE:19
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:19
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }


}
