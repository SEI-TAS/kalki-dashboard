
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
    <h4>Create new umbox</h4>
    <form method="post" action=""""),_display_(/*6.34*/routes/*6.40*/.HomeController.addUmbox),format.raw/*6.64*/("""">
        """),_display_(/*7.10*/helper/*7.16*/.CSRF.formField),format.raw/*7.31*/("""
        """),format.raw/*8.9*/("""Umbox id:<br />
        <input type="text" name="umboxId" /><br />
        Umbox name:<br />
        <input type="text" name="umboxName" /><br />
        Device:<br />
        <input type="text" name="device" /><br />
        Time started:<br />
        <input type="number" name="startedAt" /><br />
        <input type="submit" value="Submit" />
    </form>
    <a href=""""),_display_(/*18.15*/routes/*18.21*/.HomeController.clean),format.raw/*18.42*/("""">Drop tables</a>
    <a href=""""),_display_(/*19.15*/routes/*19.21*/.HomeController.logUmboxes),format.raw/*19.47*/("""">Log umboxes</a>
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
                  DATE: Wed Jun 13 18:52:55 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/index.scala.html
                  HASH: b77b702006ee3e70f16a29941ace9762f8d58157
                  MATRIX: 941->1|1037->4|1064->6|1089->23|1128->25|1159->30|1289->134|1303->140|1347->164|1385->176|1399->182|1434->197|1469->206|1870->580|1885->586|1927->607|1986->639|2001->645|2048->671|2097->690
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|37->6|37->6|37->6|38->7|38->7|38->7|39->8|49->18|49->18|49->18|50->19|50->19|50->19|51->20
                  -- GENERATED --
              */
          