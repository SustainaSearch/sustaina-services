package com.sustainasearch.services.catalog

import com.sustainasearch.services.catalog.products.SimpleProductQueryResponseApiModel
import play.api.libs.json.Json

case class CatalogQueryResponseApiModel(products: SimpleProductQueryResponseApiModel)

object CatalogQueryResponseApiModel {
  implicit val format = Json.format[CatalogQueryResponseApiModel]
}
