
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

object main extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[String,Html,play.twirl.api.HtmlFormat.Appendable] {

  /*
 * This template is called from the `index` template. This template
 * handles the rendering of the page header and body tags. It takes
 * two arguments, a `String` for the title of the page and an `Html`
 * object to insert into the body of the page.
 */
  def apply/*7.2*/(title: String)(content: Html):play.twirl.api.HtmlFormat.Appendable = {
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
    """),format.raw/*31.5*/("""</body>
</html>
"""))
      }
    }
  }

  def render(title:String,content:Html): play.twirl.api.HtmlFormat.Appendable = apply(title)(content)

  def f:((String) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title) => (content) => apply(title)(content)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Thu Jun 14 15:27:20 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/main.scala.html
                  HASH: 947aa2818bd464d6b000f34b60401dccad70e16b
                  MATRIX: 1206->260|1330->291|1357->292|1437->397|1473->406|1508->414|1534->419|1579->437|1596->445|1631->459|1667->468|1744->518|1759->524|1820->563|1882->687|1918->696|1993->744|2010->752|2040->761|2082->775|2150->816|2178->823|2219->836|2278->868|2295->876|2325->885|2363->896|2380->904|2411->914|2443->919
                  LINES: 33->7|38->8|39->9|42->12|43->13|43->13|43->13|44->14|44->14|44->14|45->15|45->15|45->15|45->15|48->19|49->20|50->21|50->21|50->21|52->23|53->24|53->24|54->25|57->28|57->28|57->28|59->30|59->30|59->30|60->31
                  -- GENERATED --
              */
          