package com.sustainasearch.services.catalog.products

import io.swagger.annotations._
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext

@Singleton
@Api(value = "/products")
class ProductsController @Inject()(productService: ProductService)(implicit ec: ExecutionContext) extends Controller {

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
    val product = request.body.as[ProductApiModel]
    for {
      productContainer <- productService.add(ProductsApi.product.from(product))
    } yield {
      Ok(Json.toJson(ProductsApi.productContainer.to(productContainer)))
    }
  }

}
