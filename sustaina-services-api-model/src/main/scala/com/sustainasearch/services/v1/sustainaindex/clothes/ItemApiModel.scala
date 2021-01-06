package com.sustainasearch.services.v1.sustainaindex.clothes

import com.sustainasearch.services.v1.sustainaindex.brand.BrandApiModel
import com.sustainasearch.services.v1.sustainaindex.clothes.material.MaterialApiModel
import play.api.libs.json.Json

case class ItemApiModel(id: String,
                        countryCode: Option[String],
                        certifications: Option[Seq[Int]],
                        materials: Option[Seq[MaterialApiModel]],
                        garmentWeight: Option[Float],
                        brand: Option[BrandApiModel])

object ItemApiModel {
  implicit val format = Json.format[ItemApiModel]
}