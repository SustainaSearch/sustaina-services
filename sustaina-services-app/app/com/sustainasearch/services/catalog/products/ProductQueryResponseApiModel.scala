package com.sustainasearch.services.catalog.products

import play.api.libs.json.Json

case class ProductQueryResponseApiModel(numFound: Long,
                                        documents: Seq[ProductContainerApiModel])
object ProductQueryResponseApiModel {
  implicit val format = Json.format[ProductQueryResponseApiModel]
}