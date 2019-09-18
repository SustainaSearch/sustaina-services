package com.sustainasearch.services.v1.catalog.products.models.clothes

import play.api.libs.json.Json

case class ClothesApiModel(compositions: Seq[CompositionApiModel])

object ClothesApiModel {
  implicit val format = Json.format[ClothesApiModel]
}