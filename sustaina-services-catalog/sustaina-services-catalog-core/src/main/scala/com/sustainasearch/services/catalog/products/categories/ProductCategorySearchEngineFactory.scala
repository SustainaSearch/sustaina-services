package com.sustainasearch.services.catalog.products.categories

import com.sustainasearch.searchengine.SearchEngine
import com.sustainasearch.services.catalog.products.Category
import com.sustainasearch.services.catalog.products.CategoryType.CategoryType

trait ProductCategorySearchEngineFactory {

  def createSearchEngine(fieldRegister: ProductCategorySearchEngineFieldRegister): SearchEngine[CategoryType, Category, Option[Nothing]]

}
