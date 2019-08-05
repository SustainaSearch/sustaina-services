package com.sustainasearch.services.catalog.products

import play.api.libs.json.Json

case class CountryApiModel(countryCode: String, names: Seq[NameApiModel])

object CountryApiModel {
  implicit val format = Json.format[CountryApiModel]
}
