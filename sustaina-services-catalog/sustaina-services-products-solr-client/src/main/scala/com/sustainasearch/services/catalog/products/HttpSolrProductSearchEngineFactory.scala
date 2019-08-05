package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine.SearchEngine
import com.sustainasearch.searchengine.solr.SolrSearchEngine
import com.sustainasearch.searchengine.solr.http.{HttpSolrClientFactory, HttpSolrConfig}
import com.sustainasearch.services.catalog.products.facets.ProductFacets

class HttpSolrProductSearchEngineFactory(config: HttpSolrConfig) extends ProductSearchEngineFactory {

  override def createSearchEngine(fieldRegister: ProductSearchEngineFieldRegister): SearchEngine[Product, ProductFacets] = {
    new SolrSearchEngine[Product, ProductFacets](
      new HttpSolrClientFactory(config),
      new ProductSolrMorphism(fieldRegister)
    )
  }

}
