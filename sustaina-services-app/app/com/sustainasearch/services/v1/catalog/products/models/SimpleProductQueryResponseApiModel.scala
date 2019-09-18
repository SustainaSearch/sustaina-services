package com.sustainasearch.services.v1.catalog.products.models

import com.sustainasearch.services.v1.catalog.products.models.facets.ProductFacetsApiModel
import play.api.libs.json.Json

case class SimpleProductQueryResponseApiModel(start: Long,
                                        numFound: Long,
                                        documents: Seq[SimpleProductApiModel],
                                        facets: ProductFacetsApiModel)

object SimpleProductQueryResponseApiModel {
  implicit val format = Json.format[SimpleProductQueryResponseApiModel]
}