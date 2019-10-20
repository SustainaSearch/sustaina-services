package com.sustainasearch.services.v1.catalog.products

import com.sustainasearch.searchengine.{AllDocumentsQuery, FreeTextQuery, MainQuery, SpatialPoint}
import com.sustainasearch.services.catalog.products.{CategoryType, _}
import com.sustainasearch.services.v1.FilterQueryJsonParser
import io.swagger.annotations._
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, Action, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
@Api(value = "/Products")
class ProductsController @Inject()(components: ControllerComponents,
                                   productService: ProductService)(implicit ec: ExecutionContext) extends AbstractController(components) {

  @ApiOperation(
    httpMethod = "GET",
    value = "Get a specific product",
    produces = "application/json",
    response = classOf[ProductContainerApiModel]
  )
  def getById(@ApiParam(value = "Category type", required = true) categoryType: String,
              @ApiParam(value = "Product ID", required = true) productId: String) = Action.async { implicit request =>
    val uuid = ProductsIsomorphism.productId.from(productId)

    for {
      maybeProduct <- productService.getById(uuid)
    } yield {
      maybeProduct.fold(NotFound(Json.toJson(s"No product with ID '$productId' was found"))) { productContainer =>
        Ok(Json.toJson(ProductsIsomorphism.productContainer.to(productContainer)))
      }
    }
  }

  @ApiOperation(
    httpMethod = "GET",
    value = "Queries a specific product category",
    produces = "application/json",
    response = classOf[ProductCategoryQueryResponseApiModel]
  )
  def query(@ApiParam(value = "Category type", required = true) categoryType: String,
            @ApiParam(value = "Main query", required = false) q: Option[String],
            @ApiParam(value = "Offset (i.e. number of documents) into the query’s result set to be displayed in the response", required = false, defaultValue = "0") start: Long = 0,
            @ApiParam(value = "Maximum number of documents returned at a time in response to a query", required = false, defaultValue = "10") rows: Long = 10,
            @ApiParam(value = "Fuzzy query", required = false, defaultValue = "false") fuzzy: Boolean = false,
            @ApiParam(value = "Spatial point using the format \"lat,lon\"") sp: Option[String] = None,
            @ApiParam(value = "Filter queries in JSON format, e.g. {\"text\":{\"fieldName\":\"field1\",\"fieldValue\":\"value1\"}}") fq: Seq[String]) = Action.async { implicit request =>
    val filterQueries = fq.map(FilterQueryJsonParser.fromJson)
    val query = ProductQuery(
      mainQuery = q.fold[MainQuery](AllDocumentsQuery)(mainQuery => FreeTextQuery(mainQuery)),
      filterQueries = filterQueries,
      start = start,
      rows = rows,
      fuzzy = fuzzy,
      maybeSpatialPoint = sp.map(SpatialPoint(_))
    )

    for {
      response <- productService.queryProductCategory(CategoryType.withName(categoryType), query)
    } yield {
      Ok(Json.toJson(ProductsIsomorphism.productCatalogQueryResponse.to(response)))
    }
  }

  @ApiOperation(
    httpMethod = "POST",
    value = "Adds a new Product. Only for test purposes - will be removed eventually.",
    produces = "application/json",
    response = classOf[ProductApiModel]
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        dataType = "com.sustainasearch.services.v1.catalog.products.ProductApiModel",
        paramType = "body",
        required = true,
        allowMultiple = false
      )
    )
  )
  def add(@ApiParam(value = "Category type", required = true) categoryType: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    val productRequest = request.body.as[ProductApiModel]
    require(productRequest.category.categoryType == categoryType)
    for {
      productResponse <- productService.add(ProductsIsomorphism.product.from(productRequest))
    } yield {
      Ok(Json.toJson(ProductsIsomorphism.product.to(productResponse)))
    }
  }

}
