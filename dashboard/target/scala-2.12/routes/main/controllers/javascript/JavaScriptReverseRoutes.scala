// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/crmowry/Projects/kalki-dashboard/dashboard/conf/routes
// @DATE:Thu Jul 19 11:54:19 EDT 2018

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers.javascript {

  // @LINE:31
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:31
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }

  // @LINE:9
  class ReverseUmboxController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:18
    def editUmboxImage: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UmboxController.editUmboxImage",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "edit-umbox-submit"})
        }
      """
    )
  
    // @LINE:9
    def umboxSetup: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UmboxController.umboxSetup",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "umbox-setup"})
        }
      """
    )
  
    // @LINE:28
    def getUmboxImages: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UmboxController.getUmboxImages",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "umbox-images"})
        }
      """
    )
  
    // @LINE:17
    def addUmboxImage: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UmboxController.addUmboxImage",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add-umbox-submit"})
        }
      """
    )
  
    // @LINE:20
    def deleteUmboxImage: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UmboxController.deleteUmboxImage",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "delete-umbox-image"})
        }
      """
    )
  
  }

  // @LINE:6
  class ReverseHomeController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:21
    def clean: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.clean",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "clean"})
        }
      """
    )
  
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

  
    // @LINE:14
    def addGroup: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.addGroup",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add-group"})
        }
      """
    )
  
    // @LINE:8
    def editDevicePage: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.editDevicePage",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "edit-device" + _qS([(id0 == null ? null : (""" + implicitly[play.api.mvc.QueryStringBindable[String]].javascriptUnbind + """)("id", id0))])})
        }
      """
    )
  
    // @LINE:23
    def getDevice: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getDevice",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "device" + _qS([(id0 == null ? null : (""" + implicitly[play.api.mvc.QueryStringBindable[String]].javascriptUnbind + """)("id", id0))])})
        }
      """
    )
  
    // @LINE:16
    def addTag: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.addTag",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add-tag"})
        }
      """
    )
  
    // @LINE:25
    def getGroups: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getGroups",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "groups"})
        }
      """
    )
  
    // @LINE:26
    def getTypes: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getTypes",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "types"})
        }
      """
    )
  
    // @LINE:19
    def deleteDevice: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.deleteDevice",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "delete-device"})
        }
      """
    )
  
    // @LINE:7
    def addDevicePage: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.addDevicePage",
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
  
    // @LINE:13
    def addOrEditDevice: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.addOrEditDevice",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add-or-edit-device"})
        }
      """
    )
  
    // @LINE:27
    def getTags: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getTags",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "tags"})
        }
      """
    )
  
    // @LINE:24
    def getDevices: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getDevices",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "devices"})
        }
      """
    )
  
    // @LINE:15
    def addType: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.addType",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add-type"})
        }
      """
    )
  
  }

  // @LINE:10
  class ReverseFuncyController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def funcyView: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.FuncyController.funcyView",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "funcy-view"})
        }
      """
    )
  
  }


}
