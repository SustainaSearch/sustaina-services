package com.sustainasearch.services.v1.catalog.products.models.clothes

import play.api.libs.json.Json

case class CompositionApiModel(languageCode: String, unparsedComposition: String)

object CompositionApiModel {
  implicit val format = Json.format[CompositionApiModel]
}