package com.sustainasearch.services.v1.sustainaindex

import com.sustainasearch.services.sustainaindex.SustainaIndexService
import com.sustainasearch.services.v1.sustainaindex.clothes.ClothesSustainaIndexRequestApiModel
import io.swagger.annotations._
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
@Api(value = "/SustainaIndex")
class SustainaIndexController @Inject()(components: ControllerComponents,
                                        sustainaIndexService: SustainaIndexService)(implicit ec: ExecutionContext) extends AbstractController(components) {

  @ApiOperation(
    httpMethod = "POST",
    value = "Calculate clothes SustainaIndex for item",
    produces = "application/json",
    response = classOf[SustainaIndexResponseApiModel]
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        dataType = "com.sustainasearch.services.v1.sustainaindex.clothes.ClothesSustainaIndexRequestApiModel",
        paramType = "body",
        required = true,
        allowMultiple = false
      )
    )
  )
  def calculateClothesSustainaIndex(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    val input = request
      .body
      .as[ClothesSustainaIndexRequestApiModel]
      .toInput
    for {
      sustainaIndex <- sustainaIndexService.calculateClothesSustainaIndex(input)
    } yield {
      Ok(Json.toJson(SustainaIndexResponseApiModel.from(sustainaIndex)))
    }

  }

}
