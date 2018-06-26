
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
        <tbody id="dashboardTableBody">
        </tbody>
    </table>
    <a href=""""),_display_(/*16.15*/routes/*16.21*/.DeviceController.clean),format.raw/*16.44*/("""" class="btn btn-info" role="button">Drop Tables</a>
    <a href=""""),_display_(/*17.15*/routes/*17.21*/.DeviceController.logDevices),format.raw/*17.49*/("""" class="btn btn-info" role="button">Log Devices</a>
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
                  DATE: Tue Jun 26 11:22:18 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/index.scala.html
                  HASH: 7c04fdefe144be825654193a2d0906950301ea86
                  MATRIX: 941->1|1037->4|1064->6|1089->23|1128->25|1159->30|1517->361|1532->367|1576->390|1670->457|1685->463|1734->491|1806->545|1845->547|1877->552|1918->566|1933->572|1995->613|2061->649
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|47->16|47->16|47->16|48->17|48->17|48->17|49->18|49->18|50->19|50->19|50->19|50->19|51->20
                  -- GENERATED --
              */
          