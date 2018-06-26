
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
                        <input type="text" class="form-control" id="id" name="deviceId" placeholder="Enter ID">
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="d-flex justify-content-end">
                    <button id="copyFromExisting" type="button" class="btn btn-light btn-block col-5">
                        Copy from Existing Device
                    </button>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col">
                <div class="row">
                    <label for="name" class="col-4 col-form-label">Name</label>
                    <div class="col-8">
                        <input type="text" class="form-control" id="name" name="name" placeholder="Enter Name">
                    </div>
                </div>
            </div>
            <div class="form-group col">
                <div class="row d-flex justify-content-end">
                    <label for="groupId" class="col-2 col-form-label">Group ID</label>
                    <div class="col-6">
                        <select class="form-control" id="groupId" name="groupId">
                            """),format.raw/*39.52*/("""
                        """),format.raw/*40.25*/("""</select>
                    </div>
                    <div class="col-2">
                        <button id="newGroupId" type="button" class="btn btn-light btn-block">New</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group row">
            <label for="type" class="col-2 col-form-label">Type</label>
            <div class="col-8">
                <select class="form-control" id="type" name="type">
                    """),format.raw/*52.44*/("""
                """),format.raw/*53.17*/("""</select>
            </div>
            <div class="col-2">
                <button id="newType" type="button" class="btn btn-light btn-block">New</button>
            </div>
        </div>
        <div class="form-group row">
            <label for="ipAddress" class="col-2 col-form-label">IP Address</label>
            <div class="col-8">
                <input type="text" class="form-control" id="ipAddress" name="ipAddress" placeholder="Enter IP">
            </div>
            <div class="col-2">
                <button id="discoverIp" type="button" class="btn btn-light btn-block">Discover</button>
            </div>
        </div>
        <div class="form-group row">
            <label for="historySize" class="col-2 col-form-label">History Size</label>
            <div class="col-8">
                <input type="text" class="form-control" id="historySize" name="historySize" placeholder="Enter Size">
            </div>
        </div>
        <div class="form-group row">
            <label for="samplingRate" class="col-2 col-form-label">Sampling Rate (seconds)</label>
            <div class="col-8">
                <input type="text" class="form-control" id="samplingRate" name="samplingRate" placeholder="Enter Rate">
            </div>
        </div>
        <div class="row">
            <div class="col-2">
                <label>Tags</label>
            </div>
            <div class="col-8">
                <div id="tags" class="row">
                    """),format.raw/*86.44*/("""
                """),format.raw/*87.17*/("""</div>
            </div>
            <div class="col-2">
                <button id="newTag" type="button" class="btn btn-light btn-block">New</button>
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
""")))}/*105.2*/ {_display_(Seq[Any](format.raw/*105.4*/("""
    """),format.raw/*106.5*/("""<script src=""""),_display_(/*106.19*/routes/*106.25*/.Assets.versioned("javascripts/add.js")),format.raw/*106.64*/("""" type="text/javascript"></script>
""")))}),format.raw/*107.2*/("""
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
                  DATE: Tue Jun 26 11:30:08 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/add.scala.html
                  HASH: f2066a215b624cb5b9fbefcfc324ce2c9f40519f
                  MATRIX: 939->1|1035->4|1062->6|1088->24|1127->26|1158->31|1251->98|1265->104|1309->128|1347->140|1361->146|1396->161|1431->170|2917->1651|2970->1676|3495->2196|3540->2213|5051->3719|5096->3736|5761->4382|5801->4384|5834->4389|5876->4403|5892->4409|5953->4448|6020->4484
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|37->6|37->6|37->6|38->7|38->7|38->7|39->8|70->39|71->40|83->52|84->53|117->86|118->87|136->105|136->105|137->106|137->106|137->106|137->106|138->107
                  -- GENERATED --
              */
          