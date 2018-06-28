
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
    """),format.raw/*4.5*/("""<table class="table table-hover">
        <thead>
            <tr>
                <th>Device</th>
                <th>Security State</th>
                <th>Variables</th>
                <th>Latest Alert</th>
            </tr>
        </thead>
        <tbody id="dashboardTableBody"></tbody>
    </table>
    <button id="dropAllTables" class="btn btn-info" role="button">Drop Tables</button>
    <button id="logDevices" class="btn btn-info" role="button">Log Devices</button>
""")))}/*17.2*/ {_display_(Seq[Any](format.raw/*17.4*/("""
    """),format.raw/*18.5*/("""<script src=""""),_display_(/*18.19*/routes/*18.25*/.Assets.versioned("javascripts/index.js")),format.raw/*18.66*/("""" type="text/javascript"></script>
""")))}),format.raw/*19.2*/("""
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
                  DATE: Thu Jun 28 15:56:29 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/index.scala.html
                  HASH: 1e061d96d53e2e80a1b6d15a1dd99be07bf82e6d
                  MATRIX: 941->1|1037->4|1064->6|1089->23|1128->25|1159->30|1657->510|1696->512|1728->517|1769->531|1784->537|1846->578|1912->614
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|48->17|48->17|49->18|49->18|49->18|49->18|50->19
                  -- GENERATED --
              */
          