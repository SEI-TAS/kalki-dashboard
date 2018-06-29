
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

object info extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[Integer,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(id: Integer):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("Device Info")/*3.21*/ {_display_(Seq[Any](format.raw/*3.23*/("""
    """),format.raw/*4.5*/("""<h4>Device Info</h4>
    <h6 id="name">Name: </h6>
    <h6 id="type">Type: </h6>
    <h6 id="securityState">Security State: </h6>
    <h6 id="policyFile">Policy File: </h6>
    <h6 id="alertHistory">Alert History: </h6>
    <h6 id="variables">Variables: </h6>
    <h6 id="securityStateFlowgraph">Security State Flowgraph: </h6>
""")))}/*12.2*/ {_display_(Seq[Any](format.raw/*12.4*/("""
    """),format.raw/*13.5*/("""<script src=""""),_display_(/*13.19*/routes/*13.25*/.Assets.versioned("javascripts/info.js")),format.raw/*13.65*/("""" type="text/javascript" data-id=""""),_display_(/*13.100*/id),format.raw/*13.102*/(""""></script>
""")))}),format.raw/*14.2*/("""
"""))
      }
    }
  }

  def render(id:Integer): play.twirl.api.HtmlFormat.Appendable = apply(id)

  def f:((Integer) => play.twirl.api.HtmlFormat.Appendable) = (id) => apply(id)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Fri Jun 29 16:56:24 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/info.scala.html
                  HASH: f7e0bcf9920381c9a75a697d2581e140bad7af5c
                  MATRIX: 948->1|1055->15|1082->17|1109->36|1148->38|1179->43|1526->372|1565->374|1597->379|1638->393|1653->399|1714->439|1777->474|1801->476|1844->489
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|43->12|43->12|44->13|44->13|44->13|44->13|44->13|44->13|45->14
                  -- GENERATED --
              */
          