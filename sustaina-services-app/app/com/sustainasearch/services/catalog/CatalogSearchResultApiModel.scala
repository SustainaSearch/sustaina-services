package com.sustainasearch.services.catalog

import play.api.libs.json.Json

case class CatalogSearchResultApiModel(value: String)

object CatalogSearchResultApiModel {
  implicit val format = Json.format[CatalogSearchResultApiModel]
}
