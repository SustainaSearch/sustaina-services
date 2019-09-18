package com.sustainasearch.services.v1.catalog.products.models

import play.api.libs.json.Json

case class RepresentativePointApiModel(latitude: Double, longitude: Double)

object RepresentativePointApiModel {
  implicit val format = Json.format[RepresentativePointApiModel]
}