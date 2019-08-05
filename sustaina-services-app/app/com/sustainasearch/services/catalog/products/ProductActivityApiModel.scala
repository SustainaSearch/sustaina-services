package com.sustainasearch.services.catalog.products

import play.api.libs.json.Json

case class ProductActivityApiModel(country: CountryApiModel,
                                   city: Option[CityApiModel],
                                   representativePoint: RepresentativePointApiModel)

object ProductActivityApiModel {
  implicit val format = Json.format[ProductActivityApiModel]
}