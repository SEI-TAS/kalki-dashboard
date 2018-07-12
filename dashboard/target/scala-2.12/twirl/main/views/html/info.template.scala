
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

object info extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(id: String):play.twirl.api.HtmlFormat.Appendable = {
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

  def render(id:String): play.twirl.api.HtmlFormat.Appendable = apply(id)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (id) => apply(id)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Thu Jul 05 16:49:59 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/info.scala.html
                  HASH: 1292196d7b3d7f5a875532b203eea275c477aa95
                  MATRIX: 947->1|1053->14|1080->16|1107->35|1146->37|1177->42|1524->371|1563->373|1595->378|1636->392|1651->398|1712->438|1775->473|1799->475|1842->488
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|43->12|43->12|44->13|44->13|44->13|44->13|44->13|44->13|45->14
                  -- GENERATED --
              */
          