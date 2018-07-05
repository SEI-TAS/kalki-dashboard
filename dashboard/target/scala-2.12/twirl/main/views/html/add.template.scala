
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

    <form id="addDeviceForm" method="post" action=""""),_display_(/*6.53*/routes/*6.59*/.DeviceController.addDevice()),format.raw/*6.88*/("""">
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
                <input type="number" min="0" max="2147483647" class="form-control" id="historySize" name="historySize" placeholder="Enter Size" required>
                <div class="invalid-feedback">
                    Please enter history size.
                </div>
            </div>
        </div>

        <!-- Sampling Rate -->
        <div class="form-group row">
            <label for="samplingRate" class="col-2 col-form-label">Sampling Rate (seconds)</label>
            <div class="col-8">
                <input type="number" min="0" max="2147483647" class="form-control" id="samplingRate" name="samplingRate" placeholder="Enter Rate" required>
                <div class="invalid-feedback">
                    Please enter sampling rate.
                </div>
            </div>
        </div>

        <!-- Tags -->
        <div class="form-group row">
            <div class="col-2">
                <label>Tags</label>
            </div>
            <div class="col-8">
                <div id="tags" class="row">
                    """),format.raw/*117.44*/("""
                """),format.raw/*118.17*/("""</div>
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
                <form id="newGroupIdModalForm" class="form" method="post" action=""""),_display_(/*148.84*/routes/*148.90*/.DeviceController.addGroupId()),format.raw/*148.120*/("""">
                    """),format.raw/*149.49*/("""
                    """),format.raw/*150.21*/("""<div class="modal-header">
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
                <form id="newTypeModalForm" class="form" method="post" action=""""),_display_(/*172.81*/routes/*172.87*/.DeviceController.addType()),format.raw/*172.114*/("""">
                    """),format.raw/*173.49*/("""
                    """),format.raw/*174.21*/("""<div class="modal-header">
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
                <form id="newTagModalForm" class="form" method="post" action=""""),_display_(/*196.80*/routes/*196.86*/.DeviceController.addTag()),format.raw/*196.112*/("""">
                    """),format.raw/*197.49*/("""
                    """),format.raw/*198.21*/("""<div class="modal-header">
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
""")))}/*215.2*/ {_display_(Seq[Any](format.raw/*215.4*/("""
    """),format.raw/*216.5*/("""<script src=""""),_display_(/*216.19*/routes/*216.25*/.Assets.versioned("javascripts/add.js")),format.raw/*216.64*/("""" type="text/javascript"></script>
""")))}),format.raw/*217.2*/("""
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
                  DATE: Thu Jul 05 15:30:27 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/add.scala.html
                  HASH: 6ab383ea8d8f79ea6c98cbdd73f9d13dc378613a
                  MATRIX: 939->1|1035->4|1062->6|1088->24|1127->26|1158->31|1257->104|1271->110|1320->139|1358->178|1394->188|1769->558|1822->583|3731->2487|3784->2512|5995->4717|6041->4734|7230->5895|7246->5901|7299->5931|7351->5982|7401->6003|8673->7247|8689->7253|8739->7280|8791->7331|8841->7352|10089->8572|10105->8578|10154->8604|10206->8655|10256->8676|11164->9565|11204->9567|11237->9572|11279->9586|11295->9592|11356->9631|11423->9667
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|37->6|37->6|37->6|38->7|40->9|47->16|48->17|94->63|95->64|148->117|149->118|179->148|179->148|179->148|180->149|181->150|203->172|203->172|203->172|204->173|205->174|227->196|227->196|227->196|228->197|229->198|246->215|246->215|247->216|247->216|247->216|247->216|248->217
                  -- GENERATED --
              */
          