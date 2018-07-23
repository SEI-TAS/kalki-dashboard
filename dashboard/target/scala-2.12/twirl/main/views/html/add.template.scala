
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

object add extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("Add Device")/*3.20*/ {_display_(Seq[Any](format.raw/*3.22*/("""
    """),format.raw/*4.5*/("""<h4>Add Device</h4>
    """),_display_(/*5.6*/deviceForm/*5.16*/.render("-1")),format.raw/*5.29*/("""
""")))}/*6.2*/ {_display_(Seq[Any](format.raw/*6.4*/("""
    """),format.raw/*7.5*/("""<script src=""""),_display_(/*7.19*/routes/*7.25*/.Assets.versioned("javascripts/deviceForm.js")),format.raw/*7.71*/("""" type="text/javascript"></script>
    <script src=""""),_display_(/*8.19*/routes/*8.25*/.Assets.versioned("javascripts/add.js")),format.raw/*8.64*/("""" type="text/javascript"></script>
""")))}),format.raw/*9.2*/("""
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
                  DATE: Thu Jul 19 16:19:57 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/add.scala.html
                  HASH: b24f0fe6f403b754f144042f03de985e24bd3fe9
                  MATRIX: 939->1|1035->4|1062->6|1088->24|1127->26|1158->31|1208->56|1226->66|1259->79|1278->81|1316->83|1347->88|1387->102|1401->108|1467->154|1546->207|1560->213|1619->252|1684->288
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|36->5|36->5|36->5|37->6|37->6|38->7|38->7|38->7|38->7|39->8|39->8|39->8|40->9
                  -- GENERATED --
              */
          