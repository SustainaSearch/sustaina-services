package com.sustainasearch.services.v1.catalog.products.facets

import play.api.libs.json.Json

case class ProductFacetsApiModel(brands: Seq[BrandFacetApiModel] = Seq.empty,
                                 categories: Seq[CategoryFacetApiModel] = Seq.empty)

object ProductFacetsApiModel{
  implicit val format = Json.format[ProductFacetsApiModel]
}