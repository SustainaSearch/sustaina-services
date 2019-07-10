package com.sustainasearch.services.catalog

import play.api.libs.json.{JsValue, Json}

object CatalogApi {

  def toJson(result: CatalogQueryResponse): JsValue =
    Json.toJson(CatalogQueryResponseApiModel(result.value))
}
