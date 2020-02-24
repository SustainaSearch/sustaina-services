package com.sustainasearch.services.v1.catalog.products

import com.sustainasearch.services.v1.{ImageApiModel, NameApiModel}
import com.sustainasearch.services.v1.catalog.products.clothes.ClothesApiModel
import com.sustainasearch.services.v1.catalog.products.food.BabyFoodApiModel
import play.api.libs.json.Json

case class ProductApiModel(id: String,
                           productActivity: ProductActivityApiModel,
                           functionalNames: Seq[NameApiModel],
                           brandName: NameApiModel,
                           category: CategoryApiModel,
                           images: Seq[ImageApiModel],
                           sustainaIndex: Double,
                           certifications: Seq[CertificationApiModel],
                           babyFood: Option[BabyFoodApiModel],
                           clothes: Option[ClothesApiModel]
                          )

object ProductApiModel {
  implicit val format = Json.format[ProductApiModel]
}

