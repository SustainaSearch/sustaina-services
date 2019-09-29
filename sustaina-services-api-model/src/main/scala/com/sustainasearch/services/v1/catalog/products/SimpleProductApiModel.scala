package com.sustainasearch.services.v1.catalog.products

import com.sustainasearch.services.v1.NameApiModel
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