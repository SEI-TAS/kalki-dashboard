
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
    <a href="/add-device" class="btn btn-primary" role="button">Add Device</a>
""")))}/*18.2*/ {_display_(Seq[Any](format.raw/*18.4*/("""
    """),format.raw/*19.5*/("""<script src=""""),_display_(/*19.19*/routes/*19.25*/.Assets.versioned("javascripts/index.js")),format.raw/*19.66*/("""" type="text/javascript"></script>
""")))}),format.raw/*20.2*/("""
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
                  DATE: Tue Jul 10 11:39:55 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/index.scala.html
                  HASH: 1daaf41fb43a40c8bdc2f8845959c91b5196f2f3
                  MATRIX: 941->1|1037->4|1064->6|1089->23|1128->25|1159->30|1605->458|1644->460|1676->465|1717->479|1732->485|1794->526|1860->562
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|49->18|49->18|50->19|50->19|50->19|50->19|51->20
                  -- GENERATED --
              */
          