package com.sustainasearch.services.v1.catalog.products.models

import com.sustainasearch.services.v1.catalog.models.CategoryApiModel
import com.sustainasearch.services.v1.catalog.products.models.clothes.ClothesApiModel
import com.sustainasearch.services.v1.catalog.products.models.food.BabyFoodApiModel
import com.sustainasearch.services.v1.models.NameApiModel
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

