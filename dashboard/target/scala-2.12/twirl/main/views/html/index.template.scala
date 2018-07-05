
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
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody id="dashboardTableBody"></tbody>
    </table>
    <a href="/add-device" class="btn btn-primary" role="button">Add Device</a>
    <button id="dropAllTables" class="btn btn-info" role="button">Drop Tables</button>
    <button id="logDevices" class="btn btn-info" role="button">Log Devices</button>
""")))}/*20.2*/ {_display_(Seq[Any](format.raw/*20.4*/("""
    """),format.raw/*21.5*/("""<script src=""""),_display_(/*21.19*/routes/*21.25*/.Assets.versioned("javascripts/index.js")),format.raw/*21.66*/("""" type="text/javascript"></script>
""")))}),format.raw/*22.2*/("""
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
                  DATE: Thu Jul 05 15:36:36 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/index.scala.html
                  HASH: ae82a2cf3f3c78e9e9bc5573ebe32e66ad534549
                  MATRIX: 941->1|1037->4|1064->6|1089->23|1128->25|1159->30|1788->641|1827->643|1859->648|1900->662|1915->668|1977->709|2043->745
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|51->20|51->20|52->21|52->21|52->21|52->21|53->22
                  -- GENERATED --
              */
          