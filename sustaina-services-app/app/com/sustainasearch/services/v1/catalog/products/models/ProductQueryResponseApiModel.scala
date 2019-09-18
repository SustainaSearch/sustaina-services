package com.sustainasearch.services.v1.catalog.products.models

import com.sustainasearch.services.v1.catalog.products.models.facets.ProductFacetsApiModel
import play.api.libs.json.Json

case class ProductQueryResponseApiModel(start: Long,
                                        numFound: Long,
                                        documents: Seq[ProductApiModel],
                                        facets: ProductFacetsApiModel)

object ProductQueryResponseApiModel {
  implicit val format = Json.format[ProductQueryResponseApiModel]
}