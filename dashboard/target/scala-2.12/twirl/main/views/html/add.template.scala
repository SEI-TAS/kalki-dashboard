
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

    <form method="post" action=""""),_display_(/*6.34*/routes/*6.40*/.DeviceController.submit),format.raw/*6.64*/("""">
        """),_display_(/*7.10*/helper/*7.16*/.CSRF.formField),format.raw/*7.31*/("""
        """),format.raw/*8.9*/("""<div class="form-group">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" placeholder="Enter ID">
        </div>
        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" id="name" placeholder="Enter Name">
        </div>
        <div class="form-group">
            <label for="type">Type</label>
            <select class="form-control" id="type">
                <option>Example Type 1</option>
                <option>Example Type 2</option>
                <option>Example Type 3</option>
            </select>
        </div>
        <div class="form-group">
            <label for="ip">IP Address</label>
            <input type="text" class="form-control" id="ip" placeholder="Enter IP">
        </div>
        <div class="form-group">
            <label for="historySize">History Size</label>
            <input type="text" class="form-control" id="historySize" placeholder="Enter Size">
        </div>
        <div class="form-group">
            <label for="samplingRate">Sampling Rate (seconds)</label>
            <input type="text" class="form-control" id="samplingRate" placeholder="Enter Rate">
        </div>
        Tags
        <div class="form-check form-check">
            <input class="form-check-input" type="checkbox" id="tag1" value="option1">
            <label class="form-check-label" for="tag1">Tag 1</label>
        </div>
        <div class="form-check form-check">
            <input class="form-check-input" type="checkbox" id="tag2" value="option2">
            <label class="form-check-label" for="tag2">Tag 2</label>
        </div>
        <div class="form-check form-check">
            <input class="form-check-input" type="checkbox" id="tag3" value="option3">
            <label class="form-check-label" for="tag3">Tag 3</label>
        </div>
        <div class="form-group">
            <label for="policyFile">Policy File</label>
            <input type="file" class="form-control-file" id="policyFile">
        </div>
        <div class="form-group">
            <label for="groupId">Group ID</label>
            <select class="form-control" id="groupId">
                <option>Example ID 1</option>
                <option>Example ID 2</option>
                <option>Example ID 3</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>

    """),format.raw/*64.100*/("""
    """),format.raw/*65.105*/("""
""")))}),format.raw/*66.2*/("""
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
                  DATE: Thu Jun 14 17:32:11 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/add.scala.html
                  HASH: 3a1fbda73e714f33f6dc020da64a173cffd1b911
                  MATRIX: 939->1|1035->4|1062->6|1088->24|1127->26|1158->31|1238->85|1252->91|1296->115|1334->127|1348->133|1383->148|1418->157|3928->2733|3962->2838|3994->2840
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|37->6|37->6|37->6|38->7|38->7|38->7|39->8|95->64|96->65|97->66
                  -- GENERATED --
              */
          