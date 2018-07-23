
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
    """),_display_(/*5.6*/deviceForm/*5.16*/.render(id)),format.raw/*5.27*/("""
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
                  DATE: Thu Jul 19 16:19:57 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/edit.scala.html
                  HASH: 56d591f715a878346489f87104c6f39e21b53092
                  MATRIX: 947->1|1053->14|1080->16|1107->35|1146->37|1177->42|1228->68|1246->78|1277->89|1296->91|1334->93|1365->98|1405->112|1419->118|1485->164|1564->217|1578->223|1638->263|1700->298|1723->300|1765->313
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|36->5|36->5|36->5|37->6|37->6|38->7|38->7|38->7|38->7|39->8|39->8|39->8|39->8|39->8|40->9
                  -- GENERATED --
              */
          