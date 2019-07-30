package com.sustainasearch.services.catalog.products.food

import play.api.libs.json.Json

case class IngredientStatementApiModel(languageCode: String, unparsedStatement: String)

object IngredientStatementApiModel {
  implicit val format = Json.format[IngredientStatementApiModel]
}