package com.sustainasearch.services.catalog.categories

import com.sustainasearch.searchengine.SearchEngine
import com.sustainasearch.services.catalog.Category
import com.sustainasearch.services.catalog.CategoryType.CategoryType

trait ProductCategorySearchEngineFactory {

  def createSearchEngine(fieldRegister: ProductCategorySearchEngineFieldRegister): SearchEngine[CategoryType, Category, Option[Nothing]]

}
