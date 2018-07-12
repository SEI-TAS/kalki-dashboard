
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

object deviceForm extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[String,String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(action: String)(id: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.1*/("""<form id="addDeviceForm" method="post" action=""""),_display_(/*3.49*/action),format.raw/*3.55*/("""">
    """),format.raw/*4.33*/("""

    """),format.raw/*6.5*/("""<input type="hidden" name="id" value=""""),_display_(/*6.44*/id),format.raw/*6.46*/("""" />

    <div class="form-group row">
            <!-- Type -->
        <div class="col">
            <div class="row">
                <label for="type" class="col-4 col-form-label">Type</label>
                <div class="col-6">
                    <select class="form-control" id="type" name="type">
                            """),format.raw/*15.52*/("""
                    """),format.raw/*16.21*/("""</select>
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

        <!-- Name -->
    <div class="form-group row">
        <label for="name" class="col-2 col-form-label">Name</label>
        <div class="col-4">
            <input type="text" class="form-control" id="name" name="name" placeholder="Enter Name">
        </div>
    </div>

    <div class="form-group row">
            <!-- Description -->
        <div class="col">
            <div class="row">
                <label for="description" class="col-4 col-form-label">Description</label>
                <div class="col-8">
                    <input type="text" class="form-control" id="description" name="description" placeholder="Enter Description">
                </div>
            </div>
        </div>

            <!-- Group ID -->
        <div class="col">
            <div class="row d-flex justify-content-end">
                <label for="groupId" class="col-2 col-form-label">Group ID</label>
                <div class="col-6">
                    <select class="form-control" id="groupId" name="groupId">
                            """),format.raw/*62.52*/("""
                    """),format.raw/*63.21*/("""</select>
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
        <label for="samplingRate" class="col-2 col-form-label">Sampling Rate (milliseconds)</label>
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
                    """),format.raw/*116.44*/("""
            """),format.raw/*117.13*/("""</div>
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
            <input type="file" class="form-control-file" name="policyFile" id="policyFile">
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
            <form id="newGroupIdModalForm" class="form" method="post" action=""""),_display_(/*147.80*/routes/*147.86*/.DeviceController.addGroupId()),format.raw/*147.116*/("""">
                """),format.raw/*148.45*/("""
                """),format.raw/*149.17*/("""<div class="modal-header">
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
            <form id="newTypeModalForm" class="form" method="post" action=""""),_display_(/*171.77*/routes/*171.83*/.DeviceController.addType()),format.raw/*171.110*/("""">
                """),format.raw/*172.45*/("""
                """),format.raw/*173.17*/("""<div class="modal-header">
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
            <form id="newTagModalForm" class="form" method="post" action=""""),_display_(/*195.76*/routes/*195.82*/.DeviceController.addTag()),format.raw/*195.108*/("""">
                """),format.raw/*196.45*/("""
                """),format.raw/*197.17*/("""<div class="modal-header">
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
</div>"""))
      }
    }
  }

  def render(action:String,id:String): play.twirl.api.HtmlFormat.Appendable = apply(action)(id)

  def f:((String) => (String) => play.twirl.api.HtmlFormat.Appendable) = (action) => (id) => apply(action)(id)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Thu Jul 12 16:06:53 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/deviceForm.scala.html
                  HASH: 71d6261298812f57a7efcdc3546e183078376a62
                  MATRIX: 960->1|1082->30|1109->31|1183->79|1209->85|1243->120|1275->126|1340->165|1362->167|1723->523|1772->544|3579->2346|3628->2367|5688->4421|5730->4434|6849->5525|6865->5531|6918->5561|6966->5608|7012->5625|8204->6789|8220->6795|8270->6822|8318->6869|8364->6886|9532->8026|9548->8032|9597->8058|9645->8105|9691->8122
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|37->6|37->6|37->6|46->15|47->16|93->62|94->63|147->116|148->117|178->147|178->147|178->147|179->148|180->149|202->171|202->171|202->171|203->172|204->173|226->195|226->195|226->195|227->196|228->197
                  -- GENERATED --
              */
          