package com.sustainasearch.services.catalog.products

import java.util.UUID

import com.sustainasearch.searchengine.SearchEngine
import com.sustainasearch.services.catalog.products.facets.ProductFacets

trait ProductSearchEngineFactory {

  def createSearchEngine(fieldRegister: ProductSearchEngineFieldRegister): SearchEngine[UUID, Product, ProductFacets]

}
