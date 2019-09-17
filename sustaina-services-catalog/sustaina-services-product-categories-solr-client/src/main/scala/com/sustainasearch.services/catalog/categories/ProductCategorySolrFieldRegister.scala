package com.sustainasearch.services.catalog.categories

import com.sustainasearch.services.LanguageCode.LanguageCode
import com.sustainasearch.services.Name

/**
  * The field names must correspond with what is defined in the sustaina-product-categories schema.xml file.
  */
object ProductCategorySolrFieldRegister extends ProductCategorySearchEngineFieldRegister {
  override val IdField: String = "id"

  override val CategoryNameField: String = "categoryName"

  override def categoryNameWithNameLanguageCodeField(name: Name): String = s"$CategoryNameField${nameLanguageCodeSuffix(name)}"

  override def categoryNameWithLanguageCodeField(languageCode: LanguageCode): String = s"$CategoryNameField${languageCodeSuffix(languageCode)}"

  private def languageCodeSuffix(languageCode: LanguageCode): String = s"_${languageCode.toString}"

  private def nameLanguageCodeSuffix(name: Name): String =
    name.languageCode.fold("")(languageCode => languageCodeSuffix(languageCode))

}
