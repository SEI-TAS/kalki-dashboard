
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

object footer extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.1*/("""<footer class="main-footer">
    <div class="container">
        <div class="row">
            <div class="col-12 col-md footer-info">
                <p>Carnegie Mellon University
                    <br>
                    Software Engineering Institute
                    <br>
                    4500 Fifth Avenue
                    <br>
                    Pittsburgh, PA 15213-2612
                    <br>
                    <a href="tel:+14122685800">412-268-5800</a>
                    <br>
                </p>
            </div><!--col-->
        </div>
        <div class="row">
            <div class="col-md footer-info">
                <ul class="list-unstyled list-inline">
                    <li class="list-inline-item">
                        <a href="https://www.sei.cmu.edu/locations/index.cfm" target=""><small>Office Locations</small>
                        </a>
                    </li>
                    <li class="list-inline-item">
                        <a href="https://www.sei.cmu.edu/legal/index.cfm" target=""><small>Legal Info</small>
                        </a>
                    </li>
                    <li class="list-inline-item">
                        <a href="https://www.sei.cmu.edu/index.cfm" target=""><small>www.sei.cmu.edu</small>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-md footer-info">
                <p><small class="copyright">©2018 Carnegie Mellon University</small></p>
            </div>
        </div>
    </div>
</footer>"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Thu Jul 05 16:42:51 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/partials/footer.scala.html
                  HASH: 39254a3a86a866ad9269ca6ff45c391a5aed7f44
                  MATRIX: 951->1|1047->4|1074->5
                  LINES: 28->1|33->2|34->3
                  -- GENERATED --
              */
          