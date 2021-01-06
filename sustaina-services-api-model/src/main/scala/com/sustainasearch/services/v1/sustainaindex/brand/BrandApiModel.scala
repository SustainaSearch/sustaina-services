package com.sustainasearch.services.v1.sustainaindex.brand

import play.api.libs.json.Json

case class BrandApiModel(id: Int)

object BrandApiModel {
  implicit val format = Json.format[BrandApiModel]
}
