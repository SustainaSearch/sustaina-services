package com.sustainasearch.services.catalog.products

import play.api.libs.json.Json

case class CategoryApiModel(categoryType: String, names: Seq[NameApiModel])

object CategoryApiModel {
  implicit val format = Json.format[CategoryApiModel]
}