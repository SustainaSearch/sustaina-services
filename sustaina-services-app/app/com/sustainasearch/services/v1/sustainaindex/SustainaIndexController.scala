package com.sustainasearch.services.v1.sustainaindex

import com.sustainasearch.services.sustainaindex.SustainaIndexService
import com.sustainasearch.services.sustainaindex.clothes.SustainaIndexInput
import com.sustainasearch.services.v1.auth.AuthAction
import com.sustainasearch.services.v1.sustainaindex.clothes.{ItemApiModel, ItemConverter, SustainaIndexResponseApiModel}
import io.swagger.annotations._
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@Singleton
@Api(value = "/SustainaIndex")
class SustainaIndexController @Inject()(components: ControllerComponents,
                                        itemConverter: ItemConverter,
                                        sustainaIndexService: SustainaIndexService,
                                        authAction: AuthAction)(implicit ec: ExecutionContext) extends AbstractController(components) {

  @ApiOperation(
    httpMethod = "POST",
    value = "Calculate clothes SustainaIndex for item",
    produces = "application/json",
    response = classOf[SustainaIndexResponseApiModel]
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        dataType = "com.sustainasearch.services.v1.sustainaindex.clothes.ItemApiModel",
        paramType = "body",
        required = true,
        allowMultiple = false
      ),
      new ApiImplicitParam(
        name = "Authorization",
        value = "Bearer JWT-token",
        required = true,
        allowEmptyValue = false,
        paramType = "header",
        dataType = "String"
      )
    )
  )
  def calculateClothesSustainaIndex(): Action[JsValue] = authAction.async(parse.json) { implicit request =>
    Try(request.body.as[ItemApiModel]) match {
      case Success(requestBody) => itemConverter
        .convertToItem(requestBody)
        .flatMap {
          case Success(item) =>
            val input = SustainaIndexInput(request.tenant, item)
            for {
              triedSustainaIndex <- sustainaIndexService.calculateClothesSustainaIndex(input)
            } yield {
              triedSustainaIndex match {
                case Success(sustainaIndex) =>
                  val responseApiModel = SustainaIndexResponseApiModel.from(requestBody, sustainaIndex)
                  Ok(Json.toJson(responseApiModel))
                case Failure(t) => NotFound(t.getMessage)
              }
            }
          case Failure(t) => Future.successful(BadRequest(t.getMessage))
        }
      case Failure(t) => Future.successful(BadRequest(t.getMessage))
    }

  }


}
