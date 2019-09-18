package com.sustainasearch.services.v1.catalog

import com.sustainasearch.services.v1.catalog.products.SimpleProductQueryResponseApiModel
import play.api.libs.json.Json

case class CatalogQueryResponseApiModel(products: SimpleProductQueryResponseApiModel)

object CatalogQueryResponseApiModel {
  implicit val format = Json.format[CatalogQueryResponseApiModel]
}
