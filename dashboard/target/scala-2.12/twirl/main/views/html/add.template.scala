
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

    <form id="addDeviceForm" class="form" method="post" action=""""),_display_(/*6.66*/routes/*6.72*/.DeviceController.submit()),format.raw/*6.98*/("""">
        """),format.raw/*7.37*/("""

        """),format.raw/*9.9*/("""<div class="form-group row">
                <!-- Type -->
            <div class="col">
                <div class="row">
                    <label for="type" class="col-4 col-form-label">Type</label>
                    <div class="col-6">
                        <select class="form-control" id="type" name="type">
                            """),format.raw/*16.52*/("""
                        """),format.raw/*17.25*/("""</select>
                    </div>
                        <!-- New Type Button -->
                    <div class="col-2">
                        <button id="newType" type="button" class="btn btn-light btn-block" data-toggle="modal" data-target="#newTypeModal">
                            New
                        </button>
                    </div>
                </div>
            </div>

            <!-- Copy From Existing Device -->
            <div class="form-group col">
                <div class="d-flex justify-content-end">
                    <button id="copyFromExisting" type="button" class="btn btn-light btn-block col-5">
                        Copy from Existing Device
                    </button>
                </div>
            </div>
        </div>

            <!-- Enter ID -->
        <div class="form-group row">
            <label for="id" class="col-2 col-form-label">ID</label>
            <div class="col-8">
                <input type="text" class="form-control" id="id" name="deviceId" placeholder="Enter ID">
            </div>
        </div>

        <div class="form-group row">
            <!-- Name -->
            <div class="col">
                <div class="row">
                    <label for="name" class="col-4 col-form-label">Name</label>
                    <div class="col-8">
                        <input type="text" class="form-control" id="name" name="name" placeholder="Enter Name">
                    </div>
                </div>
            </div>

            <!-- Group ID -->
            <div class="col">
                <div class="row d-flex justify-content-end">
                    <label for="groupId" class="col-2 col-form-label">Group ID</label>
                    <div class="col-6">
                        <select class="form-control" id="groupId" name="groupId">
                            """),format.raw/*63.52*/("""
                        """),format.raw/*64.25*/("""</select>
                    </div>
                    <!-- New Group Button -->
                    <div class="col-2">
                        <button id="newGroupIdButton" type="button" class="btn btn-light btn-block" data-toggle="modal" data-target="#newGroupIdModal">
                            New
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- IP Address -->
        <div class="form-group row">
            <label for="ipAddress" class="col-2 col-form-label">IP Address</label>
            <div class="col-8">
                <input type="text" class="form-control" id="ipAddress" name="ipAddress" placeholder="Enter IP">
            </div>
            <!-- Discover IP-->
            <div class="col-2">
                <button id="discoverIp" type="button" class="btn btn-light btn-block">Discover</button>
            </div>
        </div>

        <!-- History Size -->
        <div class="form-group row">
            <label for="historySize" class="col-2 col-form-label">History Size</label>
            <div class="col-8">
                <input type="number" min="0" max="2147483647" class="form-control" id="historySize" name="historySize" placeholder="Enter Size">
            </div>
        </div>

        <!-- Sampling Rate -->
        <div class="form-group row">
            <label for="samplingRate" class="col-2 col-form-label">Sampling Rate (seconds)</label>
            <div class="col-8">
                <input type="number" min="0" max="2147483647" class="form-control" id="samplingRate" name="samplingRate" placeholder="Enter Rate">
            </div>
        </div>

        <!-- Tags -->
        <div class="form-group row">
            <div class="col-2">
                <label>Tags</label>
            </div>
            <div class="col-8">
                <div id="tags" class="row">
                    """),format.raw/*111.44*/("""
                """),format.raw/*112.17*/("""</div>
            </div>
            <!-- New Tag Button -->
            <div class="col-2">
                <button id="newTag" type="button" class="btn btn-light btn-block" data-toggle="modal" data-target="#newTagModal">
                    New
                </button>
            </div>
        </div>

        <!-- Policy File -->
        <div class="form-group row">
            <label for="policyFile" class="col-2 col-form-label">Policy File</label>
            <div class="col-8">
                <input type="file" class="form-control-file" id="policyFile">
            </div>
        </div>

        <!-- Submit -->
        <div class="row">
            <div class="col-2">
                <button type="submit" class="btn btn-primary btn-block">Submit</button>
            </div>
        </div>
    </form>

    <!-- New Group Modal -->
    <div class="modal fade" id="newGroupIdModal" tabindex="-1" role="dialog" aria-labelledby="newGroupIdModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <form id="newGroupIdModalForm" class="form" method="post" action=""""),_display_(/*142.84*/routes/*142.90*/.DeviceController.addGroupId()),format.raw/*142.120*/("""">
                    """),format.raw/*143.49*/("""
                    """),format.raw/*144.21*/("""<div class="modal-header">
                        <h4 class="modal-title" id="newGroupIdModalLabel">New Group ID</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <input type="text" class="form-control" placeholder="Enter Group ID" name="groupId" id="newGroupIdInput">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary" id="saveNewGroupId">Save changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- New Type Modal -->
    <div class="modal fade" id="newTypeModal" tabindex="-1" role="dialog" aria-labelledby="newTypeModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <form id="newTypeModalForm" class="form" method="post" action=""""),_display_(/*166.81*/routes/*166.87*/.DeviceController.addType()),format.raw/*166.114*/("""">
                    """),format.raw/*167.49*/("""
                    """),format.raw/*168.21*/("""<div class="modal-header">
                        <h4 class="modal-title" id="newTypeModalLabel">New Type</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <input type="text" class="form-control" placeholder="Enter Type" name="type" id="newTypeInput">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary" id="saveNewType">Save changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- New Tag Modal -->
    <div class="modal fade" id="newTagModal" tabindex="-1" role="dialog" aria-labelledby="newTagModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <form id="newTagModalForm" class="form" method="post" action=""""),_display_(/*190.80*/routes/*190.86*/.DeviceController.addTag()),format.raw/*190.112*/("""">
                    """),format.raw/*191.49*/("""
                    """),format.raw/*192.21*/("""<div class="modal-header">
                        <h4 class="modal-title" id="newTagModalLabel">New Tag</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <input type="text" class="form-control" placeholder="Enter Tag" name="tag" id="newTagInput">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary" id="saveNewTag">Save changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
""")))}/*209.2*/ {_display_(Seq[Any](format.raw/*209.4*/("""
    """),format.raw/*210.5*/("""<script src=""""),_display_(/*210.19*/routes/*210.25*/.Assets.versioned("javascripts/add.js")),format.raw/*210.64*/("""" type="text/javascript"></script>
""")))}),format.raw/*211.2*/("""
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
                  DATE: Thu Jun 28 17:00:46 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/add.scala.html
                  HASH: 70c055c1027b649e658b155920df18c1a2897428
                  MATRIX: 939->1|1035->4|1062->6|1088->24|1127->26|1158->31|1270->117|1284->123|1330->149|1368->188|1404->198|1779->568|1832->593|3741->2497|3794->2522|5752->4474|5798->4491|6987->5652|7003->5658|7056->5688|7108->5739|7158->5760|8430->7004|8446->7010|8496->7037|8548->7088|8598->7109|9846->8329|9862->8335|9911->8361|9963->8412|10013->8433|10921->9322|10961->9324|10994->9329|11036->9343|11052->9349|11113->9388|11180->9424
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|37->6|37->6|37->6|38->7|40->9|47->16|48->17|94->63|95->64|142->111|143->112|173->142|173->142|173->142|174->143|175->144|197->166|197->166|197->166|198->167|199->168|221->190|221->190|221->190|222->191|223->192|240->209|240->209|241->210|241->210|241->210|241->210|242->211
                  -- GENERATED --
              */
          