package com.sustainasearch.services.catalog.products

import play.api.libs.json.Json

case class ProductQueryResponseApiModel(numFound: Long,
                                        documents: Seq[ProductApiModel])
object ProductQueryResponseApiModel {
  implicit val format = Json.format[ProductQueryResponseApiModel]
}