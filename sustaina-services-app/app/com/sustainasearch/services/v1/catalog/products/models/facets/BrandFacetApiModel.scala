package com.sustainasearch.services.v1.catalog.products.models.facets

import play.api.libs.json.Json

case class BrandFacetApiModel(brandName: String, count: Long)

object BrandFacetApiModel{
  implicit val format = Json.format[BrandFacetApiModel]
}