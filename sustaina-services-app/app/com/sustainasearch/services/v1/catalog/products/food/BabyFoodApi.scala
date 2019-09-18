package com.sustainasearch.services.v1.catalog.products.food

import com.sustainasearch.services.LanguageCode
import com.sustainasearch.services.catalog.products.food.{BabyFood, IngredientStatement}
import scalaz.Isomorphism.<=>

object BabyFoodApi {

  val babyFood = new (BabyFood <=> BabyFoodApiModel) {
    val to: BabyFood => BabyFoodApiModel = { babyFood =>
      BabyFoodApiModel(
        ingredientStatements = babyFood.ingredientStatements.map(ingredientStatement.to)
      )
    }
    val from: BabyFoodApiModel => BabyFood = { babyFood =>
      BabyFood(
        ingredientStatements = babyFood.ingredientStatements.map(ingredientStatement.from)
      )
    }
  }

  val ingredientStatement = new (IngredientStatement <=> IngredientStatementApiModel) {
    val to: IngredientStatement => IngredientStatementApiModel = { statement =>
      IngredientStatementApiModel(
        unparsedStatement = statement.unparsedStatement,
        languageCode = statement.languageCode.toString
      )
    }
    val from: IngredientStatementApiModel => IngredientStatement = { statement =>
      IngredientStatement(
        unparsedStatement = statement.unparsedStatement,
        languageCode = LanguageCode.withName(statement.languageCode)
      )
    }
  }

}
