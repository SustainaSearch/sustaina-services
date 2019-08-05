package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.catalog.products.clothes.ClothesApiModel
import com.sustainasearch.services.catalog.products.food.BabyFoodApiModel
import play.api.libs.json.Json

case class ProductApiModel(id: String,
                           productActivity: ProductActivityApiModel,
                           functionalNames: Seq[NameApiModel],
                           brandName: NameApiModel,
                           category: CategoryApiModel,
                           sustainaIndex: Double,
                           babyFood: Option[BabyFoodApiModel],
                           clothes: Option[ClothesApiModel]
                          )

object ProductApiModel {
  implicit val format = Json.format[ProductApiModel]
}

