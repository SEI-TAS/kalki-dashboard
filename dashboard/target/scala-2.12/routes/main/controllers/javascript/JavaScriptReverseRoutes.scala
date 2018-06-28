// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/crmowry/Projects/kalki-dashboard/dashboard/conf/routes
// @DATE:Thu Jun 28 15:53:35 EDT 2018

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers.javascript {

  // @LINE:15
  class ReverseUmboxController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:15
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

  
    // @LINE:11
    def addGroupId: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.addGroupId",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add-group-id"})
        }
      """
    )
  
    // @LINE:16
    def logDevices: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.logDevices",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "log-devices"})
        }
      """
    )
  
    // @LINE:13
    def addTag: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.addTag",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add-tag"})
        }
      """
    )
  
    // @LINE:10
    def submit: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.submit",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add-device-submit"})
        }
      """
    )
  
    // @LINE:20
    def getTypes: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getTypes",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "types"})
        }
      """
    )
  
    // @LINE:14
    def clean: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.clean",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "clean"})
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
  
    // @LINE:8
    def deviceInfo: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.deviceInfo",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "info" + _qS([(id0 == null ? null : (""" + implicitly[play.api.mvc.QueryStringBindable[String]].javascriptUnbind + """)("id", id0))])})
        }
      """
    )
  
    // @LINE:19
    def getGroupIds: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getGroupIds",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "group-ids"})
        }
      """
    )
  
    // @LINE:21
    def getTags: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getTags",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "tags"})
        }
      """
    )
  
    // @LINE:18
    def getDevices: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getDevices",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "devices"})
        }
      """
    )
  
    // @LINE:12
    def addType: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.addType",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add-type"})
        }
      """
    )
  
  }

  // @LINE:24
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:24
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
