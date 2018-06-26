
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

object main extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[String,Html,Html,play.twirl.api.HtmlFormat.Appendable] {

  /*
 * This template is called from the `index` template. This template
 * handles the rendering of the page header and body tags. It takes
 * two arguments, a `String` for the title of the page and an `Html`
 * object to insert into the body of the page.
 */
  def apply/*7.2*/(title: String)(content: Html)(scripts: Html):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*8.1*/("""
"""),format.raw/*9.1*/("""<!DOCTYPE html>
<html lang="en">
    <head>
        """),format.raw/*12.62*/("""
        """),format.raw/*13.9*/("""<title>"""),_display_(/*13.17*/title),format.raw/*13.22*/("""</title>
        """),_display_(/*14.10*/partials/*14.18*/.stylesheets()),format.raw/*14.32*/("""
        """),format.raw/*15.9*/("""<link rel="shortcut icon" type="image/png" href=""""),_display_(/*15.59*/routes/*15.65*/.Assets.versioned("images/favicon.png")),format.raw/*15.104*/("""">
    </head>
    <body>
        """),format.raw/*19.32*/("""
        """),format.raw/*20.9*/("""<div class="page-content-wrapper">
            """),_display_(/*21.14*/partials/*21.22*/.navbar()),format.raw/*21.31*/("""

            """),format.raw/*23.13*/("""<div class="container">
                """),_display_(/*24.18*/content),format.raw/*24.25*/("""
            """),format.raw/*25.13*/("""</div>
        </div>

        """),_display_(/*28.10*/partials/*28.18*/.footer()),format.raw/*28.27*/("""

        """),_display_(/*30.10*/partials/*30.18*/.scripts()),format.raw/*30.28*/("""
        """),_display_(/*31.10*/scripts),format.raw/*31.17*/("""
    """),format.raw/*32.5*/("""</body>
</html>
"""))
      }
    }
  }

  def render(title:String,content:Html,scripts:Html): play.twirl.api.HtmlFormat.Appendable = apply(title)(content)(scripts)

  def f:((String) => (Html) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title) => (content) => (scripts) => apply(title)(content)(scripts)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Thu Jun 21 10:58:47 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/main.scala.html
                  HASH: f7e62307a5cf6a7cca78dbc2c86df3427c0f9b3d
                  MATRIX: 1211->260|1350->306|1377->307|1457->412|1493->421|1528->429|1554->434|1599->452|1616->460|1651->474|1687->483|1764->533|1779->539|1840->578|1902->702|1938->711|2013->759|2030->767|2060->776|2102->790|2170->831|2198->838|2239->851|2298->883|2315->891|2345->900|2383->911|2400->919|2431->929|2468->939|2496->946|2528->951
                  LINES: 33->7|38->8|39->9|42->12|43->13|43->13|43->13|44->14|44->14|44->14|45->15|45->15|45->15|45->15|48->19|49->20|50->21|50->21|50->21|52->23|53->24|53->24|54->25|57->28|57->28|57->28|59->30|59->30|59->30|60->31|60->31|61->32
                  -- GENERATED --
              */
          