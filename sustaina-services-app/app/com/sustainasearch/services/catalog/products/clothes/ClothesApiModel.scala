package com.sustainasearch.services.catalog.products.clothes

import play.api.libs.json.Json

case class ClothesApiModel(compositions: Seq[CompositionApiModel])

object ClothesApiModel {
  implicit val format = Json.format[ClothesApiModel]
}