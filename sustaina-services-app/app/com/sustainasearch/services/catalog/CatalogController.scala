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
  def query(@ApiParam(value = "Main query") q: String,
            @ApiParam(value = "Fuzzy query", required = false, defaultValue = "false") fuzzy: Boolean = false) = Action.async { implicit request =>
    val query = Query(
      mainQuery = q,
      fuzzy = fuzzy
    )
    for {
      response <- catalogService.query(query)
    } yield {
      Ok(Json.toJson(CatalogApi.queryResponse.to(response)))
    }
  }

}
