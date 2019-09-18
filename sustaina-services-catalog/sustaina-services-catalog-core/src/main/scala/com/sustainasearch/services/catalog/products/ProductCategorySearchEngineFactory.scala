package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine.SearchEngine
import com.sustainasearch.services.catalog.products.CategoryType.CategoryType

trait ProductCategorySearchEngineFactory {

  def createSearchEngine(fieldRegister: ProductCategorySearchEngineFieldRegister): SearchEngine[CategoryType, Category, Option[Nothing]]

}
