package com.sustainasearch.services.catalog.products.food

import play.api.libs.json.Json

case class BabyFoodApiModel(ingredientStatements: Seq[IngredientStatementApiModel])

object BabyFoodApiModel {
  implicit val format = Json.format[BabyFoodApiModel]
}