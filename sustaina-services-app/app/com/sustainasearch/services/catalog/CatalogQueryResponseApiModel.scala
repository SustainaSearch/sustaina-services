package com.sustainasearch.services.catalog

import play.api.libs.json.Json

case class CatalogQueryResponseApiModel(value: String)

object CatalogQueryResponseApiModel {
  implicit val format = Json.format[CatalogQueryResponseApiModel]
}
