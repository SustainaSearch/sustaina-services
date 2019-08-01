package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.catalog.products.LanguageCode.LanguageCode

/**
  * The field names must correspond with what is defined in the sustaina-products schema.xml file.
  */
object ProductSolrFieldRegister extends ProductSearchEngineFieldRegister {
  override val IdField: String = "id"

  override val RepresentativePointField: String = "representativePoint"

  override val FunctionalNameField: String = "functionalName"

  override val BrandNameField: String = "brandName"

  override val CategoryTypeField: String = "categoryType"

  override val CategoryNameField: String = "categoryName"

  override val SustainaIndexField: String = "sustainaIndex"

  override val BabyFoodIngredientStatementField: String = "babyFoodIngredientStatement"

  override val ClothesCompositionField: String = "clothesComposition"

  override def functionalNameWithNameLanguageCodeField(name: Name): String = s"$FunctionalNameField${nameLanguageCodeSuffix(name)}"

  override def brandNameWithNameLanguageCodeField(name: Name): String = s"$BrandNameField${nameLanguageCodeSuffix(name)}"

  override def categoryNameWithNameLanguageCodeField(name: Name): String = s"$CategoryNameField${nameLanguageCodeSuffix(name)}"

  override def functionalNameWithLanguageCodeField(languageCode: LanguageCode): String = s"${FunctionalNameField}${languageCodeSuffix(languageCode)}"

  override def categoryNameWithLanguageCodeField(languageCode: LanguageCode): String = s"${CategoryNameField}${languageCodeSuffix(languageCode)}"

  override def babyFoodIngredientStatemenWithLanguageCodeField(languageCode: LanguageCode): String = s"${BabyFoodIngredientStatementField}${languageCodeSuffix(languageCode)}"

  override def clothesCompositionWithLanguageCodeField(languageCode: LanguageCode): String = s"${ClothesCompositionField}${languageCodeSuffix(languageCode)}"

  private def languageCodeSuffix(languageCode: LanguageCode): String = s"_${languageCode.toString}"

  private def nameLanguageCodeSuffix(name: Name): String =
    name.languageCode.fold("")(languageCode => languageCodeSuffix(languageCode))
}