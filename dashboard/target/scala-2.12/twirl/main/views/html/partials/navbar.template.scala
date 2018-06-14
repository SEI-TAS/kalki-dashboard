
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

object navbar extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.1*/("""<header>
    <div class="roof">
        <div class="container d-flex flex-wrap justify-content-between">
            <div class="col-12 col-md-6 d-flex justify-content-between">
                <a class="cmu-wordmark" href="http://www.cmu.edu/">Carnegie Mellon University</a>
            </div>
            <div class="col-12 col-md-6 d-flex">
                <a class="sei-wordmark" href="http://www.sei.cmu.edu/"><h2>Software Engineering Institute</h2></a>
            </div>
        </div>
    </div>
</header>
<div class="main-header container d-flex">
    <div class="institute col-12 col-md-6">
        <h1>
            <a href="/">
                <span class="name">Kalki<span class="optional">Dashboard</span></span>
            </a>
        </h1>
    </div>

    <div class="menus col-12 col-md-6">
        <br>
        <nav class="d-md-block d-none" id="navigation" role="navigation">
            <ul class="nav nav-fill">
                <li class="nav-menu-item">
                    <a class="nav-link" href="/">
                        <span>Home</span>
                    </a>
                </li>
                <li class="nav-menu-item">
                    <a class="nav-link" href="/add-device">
                        <span>Add Device</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</div>
<div class="clearfix"></div>
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
                  DATE: Thu Jun 14 16:16:38 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/partials/navbar.scala.html
                  HASH: dc7093e33c04006278882eb05fdbfed4121f2a8d
                  MATRIX: 1040->0
                  LINES: 33->1
                  -- GENERATED --
              */
          