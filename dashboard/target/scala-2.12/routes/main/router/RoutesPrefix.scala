// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/crmowry/Projects/kalki-dashboard/dashboard/conf/routes
// @DATE:Mon Jun 04 15:57:58 EDT 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
