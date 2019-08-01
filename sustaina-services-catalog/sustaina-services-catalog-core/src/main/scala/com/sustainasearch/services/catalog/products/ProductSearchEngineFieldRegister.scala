package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.catalog.products.LanguageCode.LanguageCode

trait ProductSearchEngineFieldRegister {
  val IdField: String

  val RepresentativePointField: String

  val FunctionalNameField: String

  val BrandNameField: String

  val CategoryTypeField: String

  val CategoryNameField: String

  val SustainaIndexField: String

  val BabyFoodIngredientStatementField: String

  val ClothesCompositionField: String

  def functionalNameWithNameLanguageCodeField(name: Name): String

  def brandNameWithNameLanguageCodeField(name: Name): String

  def categoryNameWithNameLanguageCodeField(name: Name): String

  def functionalNameWithLanguageCodeField(languageCode: LanguageCode): String

  def categoryNameWithLanguageCodeField(languageCode: LanguageCode): String

  def babyFoodIngredientStatemenWithLanguageCodeField(languageCode: LanguageCode): String

  def clothesCompositionWithLanguageCodeField(languageCode: LanguageCode): String
}
