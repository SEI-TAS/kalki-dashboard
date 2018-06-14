
package views.html.partials

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

object scripts extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.1*/("""<script src=""""),_display_(/*1.15*/routes/*1.21*/.Assets.versioned("javascripts/libraries/jQuery.min.js")),format.raw/*1.77*/("""" type="text/javascript"></script>
<script src=""""),_display_(/*2.15*/routes/*2.21*/.Assets.versioned("javascripts/libraries/popper.min.js")),format.raw/*2.77*/("""" type="text/javascript"></script>
<script src=""""),_display_(/*3.15*/routes/*3.21*/.Assets.versioned("javascripts/libraries/bootstrap.js")),format.raw/*3.76*/("""" type="text/javascript"></script>
<script src=""""),_display_(/*4.15*/routes/*4.21*/.Assets.versioned("javascripts/footer.js")),format.raw/*4.63*/("""" type="text/javascript"></script>
<script src=""""),_display_(/*5.15*/routes/*5.21*/.Assets.versioned("javascripts/main.js")),format.raw/*5.61*/("""" type="text/javascript"></script>"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Thu Jun 14 15:16:47 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/partials/scripts.scala.html
                  HASH: 249d7be0abfa9e10a3ae7ecd8cb9d12420e88ab8
                  MATRIX: 1041->0|1081->14|1095->20|1171->76|1246->125|1260->131|1336->187|1411->236|1425->242|1500->297|1575->346|1589->352|1651->394|1726->443|1740->449|1800->489
                  LINES: 33->1|33->1|33->1|33->1|34->2|34->2|34->2|35->3|35->3|35->3|36->4|36->4|36->4|37->5|37->5|37->5
                  -- GENERATED --
              */
          