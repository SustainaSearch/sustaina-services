package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine.SpatialPoint
import io.swagger.annotations._
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext

@Singleton
@Api(value = "/products")
class ProductsController @Inject()(productService: ProductService)(implicit ec: ExecutionContext) extends Controller {

  @ApiOperation(
    httpMethod = "GET",
    value = "Queries the product section of the SustainaCatalog",
    produces = "application/json",
    response = classOf[ProductQueryResponseApiModel]
  )
  def query(@ApiParam(value = "Main query", required = true) q: String,
            @ApiParam(value = "Offset (i.e. number of documents) into the query’s result set to be displayed in the response", required = false, defaultValue = "0") start: Long = 0,
            @ApiParam(value = "Maximum number of documents returned at a time in response to a query", required = false, defaultValue = "10") rows: Long = 10,
            @ApiParam(value = "Fuzzy query", required = false, defaultValue = "false") fuzzy: Boolean = false,
            @ApiParam(value = "Spatial point using the format \"lat,lon\"") sp: Option[String] = None) = Action.async { implicit request =>
    val query = ProductQuery(
      mainQuery = q,
      start = start,
      rows = rows,
      fuzzy = fuzzy,
      maybeSpatialPoint = sp.map(SpatialPoint(_))
    )
      .withSortByDescendingSustainaIndex
      .withSortByNearestSpatialResult
    for {
      response <- productService.query(query)
    } yield {
      Ok(Json.toJson(ProductsApi.productQueryResponse.to(response)))
    }
  }

  @ApiOperation(
    httpMethod = "POST",
    value = "Adds a new Product",
    produces = "application/json",
    response = classOf[ProductContainerApiModel]
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        dataType = "com.sustainasearch.services.catalog.products.ProductApiModel",
        paramType = "body",
        required = true,
        allowMultiple = false
      )
    )
  )
  def add(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    val productRequest = request.body.as[ProductApiModel]
    for {
      productResponse <- productService.add(ProductsApi.product.from(productRequest))
    } yield {
      Ok(Json.toJson(ProductsApi.product.to(productResponse)))
    }
  }

}
