package com.sustainasearch.services.v1.catalog.products

import play.api.libs.json.Json

case class ProductContainerApiModel(product: ProductApiModel)

object ProductContainerApiModel {
  implicit val format = Json.format[ProductContainerApiModel]
}