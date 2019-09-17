package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine.{AllDocumentsQuery, FreeTextQuery, MainQuery, SpatialPoint}
import com.sustainasearch.services.catalog.CategoryType
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
    value = "Queries a specific product category",
    produces = "application/json",
    response = classOf[ProductQueryResponseApiModel]
  )
  def query(@ApiParam(value = "Category type", required = true) categoryType: String,
            @ApiParam(value = "Main query", required = false) q: Option[String],
            @ApiParam(value = "Offset (i.e. number of documents) into the query’s result set to be displayed in the response", required = false, defaultValue = "0") start: Long = 0,
            @ApiParam(value = "Maximum number of documents returned at a time in response to a query", required = false, defaultValue = "10") rows: Long = 10,
            @ApiParam(value = "Fuzzy query", required = false, defaultValue = "false") fuzzy: Boolean = false,
            @ApiParam(value = "Spatial point using the format \"lat,lon\"") sp: Option[String] = None) = Action.async { implicit request =>
    val query = ProductQuery(
      mainQuery = q.fold[MainQuery](AllDocumentsQuery)(mainQuery => FreeTextQuery(mainQuery)),
      start = start,
      rows = rows,
      fuzzy = fuzzy,
      maybeSpatialPoint = sp.map(SpatialPoint(_))
    )

    for {
      response <- productService.queryProductCategory(CategoryType.withName(categoryType), query)
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
