package com.sustainasearch.services.catalog.products.categories

import com.sustainasearch.services.Name
import com.sustainasearch.services.LanguageCode.LanguageCode

trait ProductCategorySearchEngineFieldRegister {
  val IdField: String

  val CategoryNameField: String

  def categoryNameWithNameLanguageCodeField(name: Name): String

  def categoryNameWithLanguageCodeField(languageCode: LanguageCode): String

}
