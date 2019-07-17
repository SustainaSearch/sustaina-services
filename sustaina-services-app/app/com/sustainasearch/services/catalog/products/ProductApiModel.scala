package com.sustainasearch.services.catalog.products

import play.api.libs.json.Json

case class ProductApiModel(name: String)

object ProductApiModel {
  implicit val format = Json.format[ProductApiModel]
}

