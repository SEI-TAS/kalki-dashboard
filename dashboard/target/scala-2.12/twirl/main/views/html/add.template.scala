
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

    <form class="form" method="post" action=""""),_display_(/*6.47*/routes/*6.53*/.DeviceController.submit),format.raw/*6.77*/("""">
        """),_display_(/*7.10*/helper/*7.16*/.CSRF.formField),format.raw/*7.31*/("""
        """),format.raw/*8.9*/("""<div class="row">
            <div class="form-group col">
                <div class="row">
                    <label for="id" class="col-4 col-form-label">ID</label>
                    <div class="col-8">
                        <input type="text" class="form-control" id="id" placeholder="Enter ID">
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="d-flex justify-content-end">
                    <button type="button" class="btn btn-light btn-block col-5">Copy from Existing Device</button>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col">
                <div class="row">
                    <label for="name" class="col-4 col-form-label">Name</label>
                    <div class="col-8">
                        <input type="text" class="form-control" id="name" placeholder="Enter Name">
                    </div>
                </div>
            </div>
            <div class="form-group col">
                <div class="row d-flex justify-content-end">
                    <label for="groupId" class="col-2 col-form-label">Group ID</label>
                    <div class="col-6">
                        <select class="form-control" id="groupId">
                            <option>Example ID 1</option>
                            <option>Example ID 2</option>
                            <option>Example ID 3</option>
                        </select>
                    </div>
                    <div class="col-2">
                        <button type="button" class="btn btn-light btn-block">New</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group row">
            <label for="type" class="col-2 col-form-label">Type</label>
            <div class="col-8">
                <select class="form-control" id="type">
                    <option>Example Type 1</option>
                    <option>Example Type 2</option>
                    <option>Example Type 3</option>
                </select>
            </div>
            <div class="col-2">
                <button type="button" class="btn btn-light btn-block">New</button>
            </div>
        </div>
        <div class="form-group row">
            <label for="ip" class="col-2 col-form-label">IP Address</label>
            <div class="col-8">
                <input type="text" class="form-control" id="ip" placeholder="Enter IP">
            </div>
            <div class="col-2">
                <button type="button" class="btn btn-light btn-block">Discover</button>
            </div>
        </div>
        <div class="form-group row">
            <label for="historySize" class="col-2 col-form-label">History Size</label>
            <div class="col-8">
                <input type="text" class="form-control" id="historySize" placeholder="Enter Size">
            </div>
        </div>
        <div class="form-group row">
            <label for="samplingRate" class="col-2 col-form-label">Sampling Rate (seconds)</label>
            <div class="col-8">
                <input type="text" class="form-control" id="samplingRate" placeholder="Enter Rate">
            </div>
        </div>
        <div class="row">
            <div class="col-2">
                <label>Tags</label>
            </div>
            <div class="col-8">
                <div class="row">
                    <div class="form-check col-2">
                        <input class="form-check-input" type="checkbox" id="tag1" value="option1">
                        <label class="form-check-label" for="tag1">Tag 1</label>
                    </div>
                    <div class="form-check col-2">
                        <input class="form-check-input" type="checkbox" id="tag2" value="option2">
                        <label class="form-check-label" for="tag2">Tag 2</label>
                    </div>
                    <div class="form-check col-2">
                        <input class="form-check-input" type="checkbox" id="tag3" value="option3">
                        <label class="form-check-label" for="tag3">Tag 3</label>
                    </div>
                </div>
            </div>
            <div class="col-2">
                <button type="button" class="btn btn-light btn-block">New</button>
            </div>
        </div>
        <div class="form-group row">
            <label for="policyFile" class="col-2 col-form-label">Policy File</label>
            <div class="col-8">
                <input type="file" class="form-control-file" id="policyFile">
            </div>
        </div>
        <div class="row">
            <div class="col-2">
                <button type="submit" class="btn btn-primary btn-block">Submit</button>
            </div>
        </div>
    </form>

    """),format.raw/*119.100*/("""
    """),format.raw/*120.105*/("""
""")))}),format.raw/*121.2*/("""
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
                  DATE: Tue Jun 19 16:51:35 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/add.scala.html
                  HASH: 8a0fc2a9b705a257daa45ea9eaf30b8fa0ff78df
                  MATRIX: 939->1|1035->4|1062->6|1088->24|1127->26|1158->31|1251->98|1265->104|1309->128|1347->140|1361->146|1396->161|1431->170|6381->5185|6416->5290|6449->5292
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|37->6|37->6|37->6|38->7|38->7|38->7|39->8|150->119|151->120|152->121
                  -- GENERATED --
              */
          