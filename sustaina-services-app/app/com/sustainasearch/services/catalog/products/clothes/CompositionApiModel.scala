package com.sustainasearch.services.catalog.products.clothes

import play.api.libs.json.Json

case class CompositionApiModel(languageCode: String, unparsedComposition: String)

object CompositionApiModel {
  implicit val format = Json.format[CompositionApiModel]
}