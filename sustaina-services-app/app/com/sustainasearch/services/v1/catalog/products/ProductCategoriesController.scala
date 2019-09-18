package com.sustainasearch.services.v1.catalog.products

import com.sustainasearch.services.catalog.products.ProductCategoryService
import io.swagger.annotations._
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext

@Singleton
@Api(value = "/product-categories")
class ProductCategoriesController @Inject()(productCategoryService: ProductCategoryService)(implicit ec: ExecutionContext) extends Controller {

  @ApiOperation(
    httpMethod = "POST",
    value = "Adds a new Product Category. Only for test purposes - will be removed eventually.",
    produces = "application/CategoryApiModel",
    response = classOf[CategoryApiModel]
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        dataType = "com.sustainasearch.services.v1.catalog.products.CategoryApiModel",
        paramType = "body",
        required = true,
        allowMultiple = false
      )
    )
  )
  def add(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    val categoryRequest = request.body.as[CategoryApiModel]
    for {
      productCategoryResponse <- productCategoryService.add(CategoryIsomorphism.category.from(categoryRequest))
    } yield {
      Ok(Json.toJson(CategoryIsomorphism.category.to(productCategoryResponse)))
    }
  }

}
