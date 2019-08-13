package com.sustainasearch.services.catalog.products

import play.api.libs.json.Json

case class SimpleProductApiModel(id: String,
                                 functionalNames: Seq[NameApiModel],
                                 brandName: NameApiModel,
                                 category: CategoryApiModel,
                                 sustainaIndex: Double
                                )

object SimpleProductApiModel {
  implicit val format = Json.format[SimpleProductApiModel]
}