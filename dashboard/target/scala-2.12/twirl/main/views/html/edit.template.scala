
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

object edit extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(id: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("Edit Device")/*3.21*/ {_display_(Seq[Any](format.raw/*3.23*/("""
    """),format.raw/*4.5*/("""<h4>Edit Device</h4>
    """),_display_(/*5.6*/deviceForm/*5.16*/.render("/edit-device-submit", id)),format.raw/*5.50*/("""
""")))}/*6.2*/ {_display_(Seq[Any](format.raw/*6.4*/("""
    """),format.raw/*7.5*/("""<script src=""""),_display_(/*7.19*/routes/*7.25*/.Assets.versioned("javascripts/deviceForm.js")),format.raw/*7.71*/("""" type="text/javascript"></script>
    <script src=""""),_display_(/*8.19*/routes/*8.25*/.Assets.versioned("javascripts/edit.js")),format.raw/*8.65*/("""" type="text/javascript" data-id=""""),_display_(/*8.100*/id),format.raw/*8.102*/(""""></script>
""")))}),format.raw/*9.2*/("""
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
                  DATE: Tue Jul 17 16:54:31 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/edit.scala.html
                  HASH: f6c810b4c235e3c13b7a1d44f9aba1851669fd74
                  MATRIX: 947->1|1053->14|1080->16|1107->35|1146->37|1177->42|1228->68|1246->78|1300->112|1319->114|1357->116|1388->121|1428->135|1442->141|1508->187|1587->240|1601->246|1661->286|1723->321|1746->323|1788->336
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|36->5|36->5|36->5|37->6|37->6|38->7|38->7|38->7|38->7|39->8|39->8|39->8|39->8|39->8|40->9
                  -- GENERATED --
              */
          