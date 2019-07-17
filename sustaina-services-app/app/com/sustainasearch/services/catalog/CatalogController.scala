package com.sustainasearch.services.catalog

import com.sustainasearch.searchengine.Query
import io.swagger.annotations.{Api, ApiOperation, ApiParam}
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext

@Singleton
@Api(value = "/catalog")
class CatalogController @Inject()(catalogService: CatalogService)(implicit ec: ExecutionContext) extends Controller {

  @ApiOperation(
    httpMethod = "GET",
    value = "Queries the SustainaCatalog",
    produces = "application/json",
    response = classOf[CatalogQueryResponseApiModel]
  )
  def query(@ApiParam(value = "Main query") q: String) = Action.async { implicit request =>
    for {
      response <- catalogService.query(Query(q))
    } yield {
      Ok(Json.toJson(CatalogApi.queryResponse.to(response)))
    }
  }

}
