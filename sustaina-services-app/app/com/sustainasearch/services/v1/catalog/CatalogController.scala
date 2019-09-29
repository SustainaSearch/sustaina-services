package com.sustainasearch.services.v1.catalog

import com.sustainasearch.searchengine.SpatialPoint
import com.sustainasearch.services.catalog.{CatalogQuery, CatalogService}
import io.swagger.annotations.{Api, ApiOperation, ApiParam}
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
@Api(value = "/Catalog")
class CatalogController @Inject()(components: ControllerComponents,
                                  catalogService: CatalogService)(implicit ec: ExecutionContext) extends AbstractController(components) {

  @ApiOperation(
    httpMethod = "GET",
    value = "Queries the SustainaCatalog",
    produces = "application/json",
    response = classOf[CatalogQueryResponseApiModel]
  )
  def query(@ApiParam(value = "Main query", required = true) q: String,
            @ApiParam(value = "Rows per catalog category", required = false, defaultValue = "10") rows: Long = 10,
            @ApiParam(value = "Fuzzy query", required = false, defaultValue = "false") fuzzy: Boolean = false,
            @ApiParam(value = "Spatial point using the format \"lat,lon\"") sp: Option[String] = None) = Action.async { implicit request =>
    val query = CatalogQuery(
      mainQuery = q,
      rows = rows,
      fuzzy = fuzzy,
      maybeSpatialPoint = sp.map(SpatialPoint(_))
    )
    for {
      response <- catalogService.query(query)
    } yield {
      Ok(Json.toJson(CatalogIsomorphism.queryResponse.to(response)))
    }
  }

}
