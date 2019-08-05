package com.sustainasearch.services.catalog.products

import play.api.libs.json.Json

case class CityApiModel(names: Seq[NameApiModel])

object CityApiModel {
  implicit val format = Json.format[CityApiModel]
}