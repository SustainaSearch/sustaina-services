package com.sustainasearch.services.v1.sustainaindex

import com.sustainasearch.services.sustainaindex.clothes.{Item, SustainaIndexInput}
import com.sustainasearch.services.sustainaindex.{SustainaIndexService, Tenant}
import com.sustainasearch.services.v1.auth.AuthAction
import com.sustainasearch.services.v1.sustainaindex.clothes.{ItemApiModel, ItemConverter}
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
    def calculateClothesSustainaIndex(tenant: Tenant, item: Item) = {
      val input = SustainaIndexInput(tenant, item)
      for {
        triedSustainaIndex <- sustainaIndexService.calculateClothesSustainaIndex(input)
      } yield {
        triedSustainaIndex match {
          case Success(sustainaIndex) => Ok(Json.toJson(SustainaIndexResponseApiModel.from(sustainaIndex)))
          case Failure(t) => NotFound(t.getMessage)
        }
      }
    }

    Try(request.body.as[ItemApiModel]) match {
      case Success(apiModel) => itemConverter
        .convertToItem(apiModel)
        .flatMap {
          case Success(item) => calculateClothesSustainaIndex(request.tenant, item)
          case Failure(t) => Future.successful(BadRequest(t.getMessage))
        }
      case Failure(t) => Future.successful(BadRequest(t.getMessage))
    }

  }


}
