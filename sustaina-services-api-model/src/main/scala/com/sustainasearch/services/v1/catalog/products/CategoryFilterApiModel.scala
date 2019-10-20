package com.sustainasearch.services.v1.catalog.products

import com.sustainasearch.services.v1.NameApiModel
import play.api.libs.json.Json

case class CategoryFilterApiModel(filterType: String, names: Seq[NameApiModel])

object CategoryFilterApiModel {
  implicit val format = Json.format[CategoryFilterApiModel]
}
