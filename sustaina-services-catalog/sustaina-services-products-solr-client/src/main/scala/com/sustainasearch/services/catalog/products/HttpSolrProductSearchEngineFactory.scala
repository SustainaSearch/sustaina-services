package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine.SearchEngine
import com.sustainasearch.searchengine.solr.SolrSearchEngine
import com.sustainasearch.searchengine.solr.http.{HttpSolrClientFactory, HttpSolrConfig}

class HttpSolrProductSearchEngineFactory(config: HttpSolrConfig) extends ProductSearchEngineFactory {

  override def createSearchEngine(fieldRegister: ProductSearchEngineFieldRegister): SearchEngine[Product] = {
    new SolrSearchEngine[Product](
      new HttpSolrClientFactory(config),
      new ProductSolrMorphism(fieldRegister)
    )
  }

}
