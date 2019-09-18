package com.sustainasearch.services.v1.catalog.products

import com.sustainasearch.services.v1.NameApiModel
import play.api.libs.json.Json

case class CityApiModel(names: Seq[NameApiModel])

object CityApiModel {
  implicit val format = Json.format[CityApiModel]
}