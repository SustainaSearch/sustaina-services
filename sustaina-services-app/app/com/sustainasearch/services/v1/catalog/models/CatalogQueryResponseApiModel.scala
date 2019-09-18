package com.sustainasearch.services.v1.catalog.models

import com.sustainasearch.services.v1.catalog.products.models.SimpleProductQueryResponseApiModel
import play.api.libs.json.Json

case class CatalogQueryResponseApiModel(products: SimpleProductQueryResponseApiModel)

object CatalogQueryResponseApiModel {
  implicit val format = Json.format[CatalogQueryResponseApiModel]
}
