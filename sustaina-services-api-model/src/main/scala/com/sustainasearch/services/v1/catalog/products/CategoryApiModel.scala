package com.sustainasearch.services.v1.catalog.products

import com.sustainasearch.services.v1.NameApiModel
import play.api.libs.json.Json

case class CategoryApiModel(categoryType: String, names: Seq[NameApiModel], filters: Seq[CategoryFilterApiModel])

object CategoryApiModel {
  implicit val format = Json.format[CategoryApiModel]
}