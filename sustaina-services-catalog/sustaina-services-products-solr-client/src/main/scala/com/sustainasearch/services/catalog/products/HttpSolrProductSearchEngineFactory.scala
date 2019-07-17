package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine.SearchEngine
import com.sustainasearch.searchengine.solr.SolrSearchEngine
import com.sustainasearch.searchengine.solr.http.{HttpSolrClientFactory, HttpSolrConfig}

class HttpSolrProductSearchEngineFactory(config: HttpSolrConfig) extends ProductSearchEngineFactory {

  override def createSearchEngine: SearchEngine[ProductContainer] = {
    new SolrSearchEngine[ProductContainer](
      new HttpSolrClientFactory(config),
      ProductsSolrMorphism
    )
  }

}
