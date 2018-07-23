
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import play.data._
import play.core.j.PlayFormsMagicForJava._

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("Dashboard")/*3.19*/ {_display_(Seq[Any](format.raw/*3.21*/("""
    """),format.raw/*4.5*/("""<table class="table">
        <thead>
            <tr>
                <th>Device</th>
                <th>Security State</th>
                <th>Variables</th>
                <th>Latest Alert</th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody id="dashboardTableBody"></tbody>
    </table>
    <a href=""""),_display_(/*17.15*/routes/*17.21*/.DeviceController.addDevicePage()),format.raw/*17.54*/("""" class="btn btn-primary" role="button">Add Device</a>
    <button class="btn btn-info" id="initializeButton">Initialize Database</button>
    <button class="btn btn-info" id="setupDatabaseButton">Setup Database</button>
    <button class="btn btn-info" id="resetDatabaseButton">Reset Database</button>
    <button class="btn btn-info" id="listDatabasesButton">List All Databases</button>
""")))}/*22.2*/ {_display_(Seq[Any](format.raw/*22.4*/("""
    """),format.raw/*23.5*/("""<script src=""""),_display_(/*23.19*/routes/*23.25*/.Assets.versioned("javascripts/index.js")),format.raw/*23.66*/("""" type="text/javascript"></script>
""")))}),format.raw/*24.2*/("""
"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Fri Jul 20 15:13:06 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/index.scala.html
                  HASH: 1601f3d1261166cebc52ea53e512a68515e9614f
                  MATRIX: 941->1|1037->4|1064->6|1089->23|1128->25|1159->30|1548->392|1563->398|1617->431|2025->821|2064->823|2096->828|2137->842|2152->848|2214->889|2280->925
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|48->17|48->17|48->17|53->22|53->22|54->23|54->23|54->23|54->23|55->24
                  -- GENERATED --
              */
          