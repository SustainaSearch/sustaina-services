package com.sustainasearch.services.v1.catalog.products

import com.sustainasearch.services.v1.NameApiModel
import com.sustainasearch.services.v1.catalog.CategoryApiModel
import com.sustainasearch.services.v1.catalog.products.clothes.ClothesApiModel
import com.sustainasearch.services.v1.catalog.products.food.BabyFoodApiModel
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

