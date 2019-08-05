package com.sustainasearch.services.catalog.products.facets

import com.sustainasearch.services.catalog.products.NameApiModel
import play.api.libs.json.Json

case class CategoryFacetApiModel(categoryType: String, names: Seq[NameApiModel], count: Long)

object CategoryFacetApiModel {
  implicit val format = Json.format[CategoryFacetApiModel]
}