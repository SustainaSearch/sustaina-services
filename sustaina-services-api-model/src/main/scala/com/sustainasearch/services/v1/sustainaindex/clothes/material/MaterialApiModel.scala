package com.sustainasearch.services.v1.sustainaindex.clothes.material

import play.api.libs.json.Json

case class MaterialApiModel(id: Int,
                            percent: Float)

object MaterialApiModel {
  implicit val format = Json.format[MaterialApiModel]
}