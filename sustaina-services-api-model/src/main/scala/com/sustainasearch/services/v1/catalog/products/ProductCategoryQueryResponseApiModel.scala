package com.sustainasearch.services.v1.catalog.products

import com.sustainasearch.services.v1.FilterQueryContainerApiModel
import play.api.libs.json.Json

case class ProductCategoryQueryResponseApiModel(productQueryResponse: ProductQueryResponseApiModel,
                                                filterQueries: Seq[FilterQueryContainerApiModel])

object ProductCategoryQueryResponseApiModel {
  implicit val format = Json.format[ProductCategoryQueryResponseApiModel]
}