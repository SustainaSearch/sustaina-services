package com.sustainasearch.services.catalog.products

import play.api.libs.json.Json

case class ProductContainerApiModel(id: String,
                                    product: ProductApiModel)

object ProductContainerApiModel {
  implicit val format = Json.format[ProductContainerApiModel]
}
