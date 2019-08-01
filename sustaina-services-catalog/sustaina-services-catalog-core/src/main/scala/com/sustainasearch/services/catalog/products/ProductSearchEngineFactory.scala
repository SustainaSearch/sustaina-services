package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine.SearchEngine

trait ProductSearchEngineFactory {

  def createSearchEngine(fieldRegister: ProductSearchEngineFieldRegister): SearchEngine[Product]
}
