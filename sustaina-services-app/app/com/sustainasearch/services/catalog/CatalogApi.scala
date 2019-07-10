package com.sustainasearch.services.catalog

import play.api.libs.json.{JsValue, Json}

object CatalogApi {

  def toJson(result: CatalogSearchResult): JsValue =
    Json.toJson(CatalogSearchResultApiModel(result.value))
}
