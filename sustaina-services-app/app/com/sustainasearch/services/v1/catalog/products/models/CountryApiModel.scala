package com.sustainasearch.services.v1.catalog.products.models

import com.sustainasearch.services.v1.models.NameApiModel
import play.api.libs.json.Json

case class CountryApiModel(countryCode: String, names: Seq[NameApiModel])

object CountryApiModel {
  implicit val format = Json.format[CountryApiModel]
}
