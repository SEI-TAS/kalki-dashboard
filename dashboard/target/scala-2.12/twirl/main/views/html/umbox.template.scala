
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

object umbox extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("Umbox Setup")/*3.21*/ {_display_(Seq[Any](format.raw/*3.23*/("""
    """),format.raw/*4.5*/("""<table class="table">
        <thead>
            <tr>
                <th>Name</th>
                <th>File (Directory)</th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody id="umboxImageTableBody"></tbody>
    </table>
    <button class="btn btn-primary" role="button" data-toggle="modal" data-target="#addUmboxImageModal">Add &mu;mbox image</button>

        <!-- New Umbox Image Modal -->
    <div class="modal fade" id="addUmboxImageModal" tabindex="-1" role="dialog" aria-labelledby="addUmboxImageModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <form id="addUmboxImageModalForm" class="form" method="post" action=""""),_display_(/*21.87*/routes/*21.93*/.UmboxController.addUmboxImage()),format.raw/*21.125*/("""">
                    """),format.raw/*22.49*/("""
                    """),format.raw/*23.21*/("""<div class="modal-header">
                        <h4 class="modal-title" id="addUmboxImageModalLabel">Add &mu;mbox image</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                            <!-- Name -->
                        <div class="form-group row">
                            <label for="addUmboxImageName" class="col-2 col-form-label">Name</label>
                            <div class="col-10">
                                <input type="text" class="form-control" id="addUmboxImageName" name="name" placeholder="Enter Name">
                            </div>
                        </div>

                            <!-- File -->
                        <div class="form-group row">
                            <label for="addUmboxImagePath" class="col-2 col-form-label">File Path</label>
                            <div class="col-10">
                                <input type="text" class="form-control" id="addUmboxImagePath" name="path" placeholder="Enter File Path">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

        <!-- Edit Umbox Image Modal -->
    <div class="modal fade" id="editUmboxImageModal" tabindex="-1" role="dialog" aria-labelledby="editUmboxImageModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <form id="editUmboxImageModalForm" class="form" method="post" action=""""),_display_(/*60.88*/routes/*60.94*/.UmboxController.editUmboxImage()),format.raw/*60.127*/("""">
                    """),format.raw/*61.49*/("""
                    """),format.raw/*62.21*/("""<input type="hidden" id="editUmboxImageId" name="id" />
                    <div class="modal-header">
                        <h4 class="modal-title" id="editUmboxImageModalLabel">Edit &mu;mbox image</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                            <!-- Name -->
                        <div class="form-group row">
                            <label for="editUmboxImageName" class="col-2 col-form-label">Name</label>
                            <div class="col-10">
                                <input type="text" class="form-control" id="editUmboxImageName" name="name" placeholder="Enter Name">
                            </div>
                        </div>

                            <!-- File -->
                        <div class="form-group row">
                            <label for="editUmboxImagePath" class="col-2 col-form-label">File Path</label>
                            <div class="col-10">
                                <input type="text" class="form-control" id="editUmboxImagePath" name="path" placeholder="Enter File Path">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
""")))}/*95.2*/ {_display_(Seq[Any](format.raw/*95.4*/("""
    """),format.raw/*96.5*/("""<script src=""""),_display_(/*96.19*/routes/*96.25*/.Assets.versioned("javascripts/umbox.js")),format.raw/*96.66*/("""" type="text/javascript"></script>
""")))}),format.raw/*97.2*/("""
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
                  DATE: Thu Jul 12 17:06:25 EDT 2018
                  SOURCE: /Users/crmowry/Projects/kalki-dashboard/dashboard/app/views/umbox.scala.html
                  HASH: 8c198261c5825496d5f0307c7fd52c8c6c301310
                  MATRIX: 941->1|1037->4|1064->6|1091->25|1130->27|1161->32|1955->799|1970->805|2024->837|2075->888|2124->909|4187->2945|4202->2951|4257->2984|4308->3035|4357->3056|6131->4812|6170->4814|6202->4819|6243->4833|6258->4839|6320->4880|6386->4916
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|52->21|52->21|52->21|53->22|54->23|91->60|91->60|91->60|92->61|93->62|126->95|126->95|127->96|127->96|127->96|127->96|128->97
                  -- GENERATED --
              */
          