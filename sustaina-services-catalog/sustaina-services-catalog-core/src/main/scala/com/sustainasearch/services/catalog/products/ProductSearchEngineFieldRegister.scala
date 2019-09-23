package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.Name
import com.sustainasearch.services.LanguageCode.LanguageCode

trait ProductSearchEngineFieldRegister {
  val IdField: String

  val ImageField: String

  val CountryCodeField: String

  val CountryNameField: String

  val CityNameField: String

  val RepresentativePointField: String

  val FunctionalNameField: String

  val BrandNameField: String

  val BrandNameExactField: String

  val CategoryTypeField: String

  val CategoryNameField: String

  val SustainaIndexField: String

  val BabyFoodIngredientStatementField: String

  val ClothesCompositionField: String

  def countryNameWithNameLanguageCodeField(name: Name): String

  def cityNameWithNameLanguageCodeField(name: Name): String

  def functionalNameWithNameLanguageCodeField(name: Name): String

  def brandNameWithNameLanguageCodeField(name: Name): String

  def categoryNameWithNameLanguageCodeField(name: Name): String

  def countryNameWithLanguageCodeField(languageCode: LanguageCode): String

  def cityNameWithLanguageCodeField(languageCode: LanguageCode): String

  def functionalNameWithLanguageCodeField(languageCode: LanguageCode): String

  def categoryNameWithLanguageCodeField(languageCode: LanguageCode): String

  def babyFoodIngredientStatemenWithLanguageCodeField(languageCode: LanguageCode): String

  def clothesCompositionWithLanguageCodeField(languageCode: LanguageCode): String
}
