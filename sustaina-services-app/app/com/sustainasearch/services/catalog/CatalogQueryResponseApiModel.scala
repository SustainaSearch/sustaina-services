package com.sustainasearch.services.catalog

import com.sustainasearch.services.catalog.products.ProductQueryResponseApiModel
import play.api.libs.json.Json

case class CatalogQueryResponseApiModel(products: ProductQueryResponseApiModel)

object CatalogQueryResponseApiModel {
  implicit val format = Json.format[CatalogQueryResponseApiModel]
}
