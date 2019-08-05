package com.sustainasearch.services.catalog.products.facets

import play.api.libs.json.Json

case class BrandFacetApiModel(brandName: String, count: Long)

object BrandFacetApiModel{
  implicit val format = Json.format[BrandFacetApiModel]
}