
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
    """),format.raw/*4.5*/("""<h1>Welcome to the Kalki Dashboard!</h1>
    <a href=""""),_display_(/*5.15*/routes/*5.21*/.HomeController.clean()),format.raw/*5.44*/("""">
        Drop tables
    </a>
    <form method="get" action=""""),_display_(/*8.33*/routes/*8.39*/.HomeController.testDb),format.raw/*8.61*/("""">
        """),_display_(/*9.10*/helper/*9.16*/.CSRF.formField),format.raw/*9.31*/("""
        """),format.raw/*10.9*/("""<button>Test Database</button>
    </form>
""")))}),format.raw/*12.2*/("""
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
                  DATE: Wed Jun 13 16:51:44 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/index.scala.html
                  HASH: c146b9dfbdcd16a277a10a80945a187b071311d1
                  MATRIX: 941->1|1037->4|1064->6|1089->23|1128->25|1159->30|1240->85|1254->91|1297->114|1387->178|1401->184|1443->206|1481->218|1495->224|1530->239|1566->248|1640->292
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|36->5|36->5|36->5|39->8|39->8|39->8|40->9|40->9|40->9|41->10|43->12
                  -- GENERATED --
              */
          