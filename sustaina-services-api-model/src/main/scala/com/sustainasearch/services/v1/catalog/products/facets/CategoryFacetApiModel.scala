package com.sustainasearch.services.v1.catalog.products.facets

import com.sustainasearch.services.v1.NameApiModel
import play.api.libs.json.Json

case class CategoryFacetApiModel(categoryType: String, names: Seq[NameApiModel], count: Long)

object CategoryFacetApiModel {
  implicit val format = Json.format[CategoryFacetApiModel]
}