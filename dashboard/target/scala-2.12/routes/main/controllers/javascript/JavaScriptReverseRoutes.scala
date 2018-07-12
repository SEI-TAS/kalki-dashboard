// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/crmowry/Projects/kalki-dashboard/dashboard/conf/routes
// @DATE:Thu Jul 12 15:45:55 EDT 2018

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers.javascript {

  // @LINE:32
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:32
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

  
    // @LINE:19
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
  
    // @LINE:29
    def getUmboxImages: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UmboxController.getUmboxImages",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "umbox-images"})
        }
      """
    )
  
    // @LINE:18
    def addUmboxImage: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.UmboxController.addUmboxImage",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add-umbox-submit"})
        }
      """
    )
  
    // @LINE:21
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

  
    // @LINE:22
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

  
    // @LINE:8
    def editDevicePage: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.editDevicePage",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "edit-device" + _qS([(id0 == null ? null : (""" + implicitly[play.api.mvc.QueryStringBindable[String]].javascriptUnbind + """)("id", id0))])})
        }
      """
    )
  
    // @LINE:24
    def getDevice: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getDevice",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "device" + _qS([(id0 == null ? null : (""" + implicitly[play.api.mvc.QueryStringBindable[String]].javascriptUnbind + """)("id", id0))])})
        }
      """
    )
  
    // @LINE:15
    def addGroupId: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.addGroupId",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add-group-id"})
        }
      """
    )
  
    // @LINE:17
    def addTag: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.addTag",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add-tag"})
        }
      """
    )
  
    // @LINE:27
    def getTypes: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getTypes",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "types"})
        }
      """
    )
  
    // @LINE:20
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
  
    // @LINE:14
    def editDevice: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.editDevice",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "edit-device-submit"})
        }
      """
    )
  
    // @LINE:13
    def addDevice: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.addDevice",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "add-device-submit"})
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
  
    // @LINE:26
    def getGroupIds: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getGroupIds",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "group-ids"})
        }
      """
    )
  
    // @LINE:28
    def getTags: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getTags",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "tags"})
        }
      """
    )
  
    // @LINE:25
    def getDevices: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.DeviceController.getDevices",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "devices"})
        }
      """
    )
  
    // @LINE:16
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
