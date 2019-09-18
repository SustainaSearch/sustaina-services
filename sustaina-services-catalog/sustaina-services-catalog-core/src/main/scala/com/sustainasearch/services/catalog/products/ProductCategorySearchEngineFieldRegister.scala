package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.LanguageCode.LanguageCode
import com.sustainasearch.services.Name

trait ProductCategorySearchEngineFieldRegister {
  val IdField: String

  val CategoryNameField: String

  def categoryNameWithNameLanguageCodeField(name: Name): String

  def categoryNameWithLanguageCodeField(languageCode: LanguageCode): String

}
