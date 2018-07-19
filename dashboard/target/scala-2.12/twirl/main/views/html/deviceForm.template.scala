
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
"""),format.raw/*3.1*/("""<form id="addDeviceForm" method="post" action=""""),_display_(/*3.49*/routes/*3.55*/.DeviceController.addOrEditDevice()),format.raw/*3.90*/("""">
    """),format.raw/*4.33*/("""

    """),format.raw/*6.5*/("""<input type="hidden" name="id" value=""""),_display_(/*6.44*/id),format.raw/*6.46*/("""" />

    <div class="form-group row">
            """),format.raw/*9.23*/("""
        """),format.raw/*10.9*/("""<div class="col">
            <div class="row">
                <label for="type" class="col-4 col-form-label">Type</label>
                <div class="col-6">
                    <select class="form-control" id="type" name="type">
                            """),format.raw/*15.52*/("""
                    """),format.raw/*16.21*/("""</select>
                </div>
                    """),format.raw/*18.42*/("""
                """),format.raw/*19.17*/("""<div class="col-2">
                    <button id="newType" type="button" class="btn btn-light btn-block" data-toggle="modal" data-target="#newTypeModal">
                            New
                    </button>
                </div>
            </div>
        </div>

            """),format.raw/*27.44*/("""
        """),format.raw/*28.9*/("""<div class="form-group col">
            <div class="d-flex justify-content-end">
                <button id="copyFromExisting" type="button" class="btn btn-light btn-block col-5">
                        Copy from Existing Device
                </button>
            </div>
        </div>
    </div>

        """),format.raw/*37.19*/("""
    """),format.raw/*38.5*/("""<div class="form-group row">
        <label for="name" class="col-2 col-form-label">Name</label>
        <div class="col-4">
            <input type="text" class="form-control" id="name" name="name" placeholder="Enter Name">
        </div>
    </div>

    <div class="form-group row">
            """),format.raw/*46.30*/("""
        """),format.raw/*47.9*/("""<div class="col">
            <div class="row">
                <label for="description" class="col-4 col-form-label">Description</label>
                <div class="col-8">
                    <input type="text" class="form-control" id="description" name="description" placeholder="Enter Description">
                </div>
            </div>
        </div>

            """),format.raw/*56.24*/("""
        """),format.raw/*57.9*/("""<div class="col">
            <div class="row d-flex justify-content-end">
                <label for="group" class="col-2 col-form-label">Group</label>
                <div class="col-6">
                    <select class="form-control" id="group" name="group">
                            """),format.raw/*62.52*/("""
                    """),format.raw/*63.21*/("""</select>
                </div>
                    """),format.raw/*65.43*/("""
                """),format.raw/*66.17*/("""<div class="col-2">
                    <button id="newGroupIdButton" type="button" class="btn btn-light btn-block" data-toggle="modal" data-target="#newGroupModal">
                            New
                    </button>
                </div>
            </div>
        </div>
    </div>

        """),format.raw/*75.25*/("""
    """),format.raw/*76.5*/("""<div class="form-group row">
        <label for="ipAddress" class="col-2 col-form-label">IP Address</label>
        <div class="col-8">
            <input type="text" class="form-control" id="ipAddress" name="ip" placeholder="Enter IP">
        </div>
            """),format.raw/*81.29*/("""
        """),format.raw/*82.9*/("""<div class="col-2">
            <button id="discoverIp" type="button" class="btn btn-light btn-block">Discover</button>
        </div>
    </div>

        """),format.raw/*87.27*/("""
    """),format.raw/*88.5*/("""<div class="form-group row">
        <label for="historySize" class="col-2 col-form-label">History Size</label>
        <div class="col-8">
            <input type="number" min="0" max="2147483647" class="form-control" id="historySize" name="historySize" placeholder="Enter Size" required>
            <div class="invalid-feedback">
                    Please enter history size.
            </div>
        </div>
    </div>

        """),format.raw/*98.28*/("""
    """),format.raw/*99.5*/("""<div class="form-group row">
        <label for="samplingRate" class="col-2 col-form-label">Sampling Rate (milliseconds)</label>
        <div class="col-8">
            <input type="number" min="0" max="2147483647" class="form-control" id="samplingRate" name="samplingRate" placeholder="Enter Rate" required>
            <div class="invalid-feedback">
                    Please enter sampling rate.
            </div>
        </div>
    </div>

        """),format.raw/*109.19*/("""
    """),format.raw/*110.5*/("""<div class="form-group row">
        <div class="col-2">
            <label>Tags</label>
        </div>
        <div class="col-8">
            <div id="tags" class="row">
                    """),format.raw/*116.44*/("""
            """),format.raw/*117.13*/("""</div>
        </div>
            """),format.raw/*119.33*/("""
        """),format.raw/*120.9*/("""<div class="col-2">
            <button id="newTag" type="button" class="btn btn-light btn-block" data-toggle="modal" data-target="#newTagModal">
                    New
            </button>
        </div>
    </div>

        """),format.raw/*127.26*/("""
    """),format.raw/*128.5*/("""<div class="form-group row">
        <label for="policyFile" class="col-2 col-form-label">Policy File</label>
        <div class="col-8">
            <input type="file" class="form-control-file" name="policyFile" id="policyFile">
        </div>
    </div>

        """),format.raw/*135.21*/("""
    """),format.raw/*136.5*/("""<div class="row">
        <div class="col-2">
            <button type="submit" class="btn btn-primary btn-block">Submit</button>
        </div>
    </div>
</form>

    """),format.raw/*143.26*/("""
"""),format.raw/*144.1*/("""<div class="modal fade" id="newGroupModal" tabindex="-1" role="dialog" aria-labelledby="newGroupModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="newGroupModalForm" class="form" method="post" action=""""),_display_(/*147.78*/routes/*147.84*/.DeviceController.addGroup()),format.raw/*147.112*/("""">
                """),format.raw/*148.45*/("""
                """),format.raw/*149.17*/("""<div class="modal-header">
                    <h4 class="modal-title" id="newGroupModalLabel">New Group</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <input type="text" class="form-control" placeholder="Enter Group" name="group" id="newGroupInput">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" id="saveNewGroup">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>

    """),format.raw/*167.25*/("""
"""),format.raw/*168.1*/("""<div class="modal fade" id="newTypeModal" tabindex="-1" role="dialog" aria-labelledby="newTypeModalLabel" aria-hidden="true">
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

    """),format.raw/*191.24*/("""
"""),format.raw/*192.1*/("""<div class="modal fade" id="newTagModal" tabindex="-1" role="dialog" aria-labelledby="newTagModalLabel" aria-hidden="true">
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
                  DATE: Thu Jul 19 11:54:20 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/deviceForm.scala.html
                  HASH: ea8005f13409e1c5347e82c004c25718fba0859d
                  MATRIX: 960->1|1082->30|1109->31|1183->79|1197->85|1252->120|1286->155|1318->161|1383->200|1405->202|1483->263|1519->272|1807->555|1856->576|1937->650|1982->667|2298->986|2334->995|2673->1316|2705->1321|3030->1635|3066->1644|3467->2028|3503->2037|3822->2351|3871->2372|3952->2447|3997->2464|4330->2785|4362->2790|4654->3070|4690->3079|4873->3252|4905->3257|5367->3710|5399->3715|5882->4179|5915->4184|6136->4399|6178->4412|6241->4466|6278->4475|6534->4719|6567->4724|6861->5001|6894->5006|7092->5196|7121->5197|7437->5485|7453->5491|7504->5519|7552->5566|7598->5583|8468->6444|8497->6445|8810->6730|8826->6736|8876->6763|8924->6810|8970->6827|9834->7681|9863->7682|10173->7964|10189->7970|10238->7996|10286->8043|10332->8060
                  LINES: 28->1|33->2|34->3|34->3|34->3|34->3|35->4|37->6|37->6|37->6|40->9|41->10|46->15|47->16|49->18|50->19|58->27|59->28|68->37|69->38|77->46|78->47|87->56|88->57|93->62|94->63|96->65|97->66|106->75|107->76|112->81|113->82|118->87|119->88|129->98|130->99|140->109|141->110|147->116|148->117|150->119|151->120|158->127|159->128|166->135|167->136|174->143|175->144|178->147|178->147|178->147|179->148|180->149|198->167|199->168|202->171|202->171|202->171|203->172|204->173|222->191|223->192|226->195|226->195|226->195|227->196|228->197
                  -- GENERATED --
              */
          