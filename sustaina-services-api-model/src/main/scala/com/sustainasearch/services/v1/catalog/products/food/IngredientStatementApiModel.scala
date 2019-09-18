package com.sustainasearch.services.v1.catalog.products.food

import play.api.libs.json.Json

case class IngredientStatementApiModel(languageCode: String, unparsedStatement: String)

object IngredientStatementApiModel {
  implicit val format = Json.format[IngredientStatementApiModel]
}