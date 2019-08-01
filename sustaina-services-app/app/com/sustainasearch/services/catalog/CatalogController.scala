package com.sustainasearch.services.catalog

import com.sustainasearch.searchengine.SpatialPoint
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
  def query(@ApiParam(value = "Main query", required = true) q: String,
            @ApiParam(value = "Fuzzy query", required = false, defaultValue = "false") fuzzy: Boolean = false,
            @ApiParam(value = "Spatial point using the format \"lat,lon\"") sp: Option[String] = None) = Action.async { implicit request =>
    val query = CatalogQuery(
      mainQuery = q,
      fuzzy = fuzzy,
      maybeSpatialPoint = sp.map(SpatialPoint(_))
    )
    for {
      response <- catalogService.query(query)
    } yield {
      Ok(Json.toJson(CatalogApi.queryResponse.to(response)))
    }
  }

}
