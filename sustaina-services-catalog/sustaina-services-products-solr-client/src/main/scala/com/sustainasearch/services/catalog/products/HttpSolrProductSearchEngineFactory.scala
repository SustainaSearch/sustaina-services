package com.sustainasearch.services.catalog.products

import java.util.UUID

import com.sustainasearch.searchengine.SearchEngine
import com.sustainasearch.searchengine.solr.SolrSearchEngine
import com.sustainasearch.searchengine.solr.http.{HttpSolrClientFactory, HttpSolrConfig}
import com.sustainasearch.services.catalog.products.facets.ProductFacets

class HttpSolrProductSearchEngineFactory(config: HttpSolrConfig) extends ProductSearchEngineFactory {

  override def createSearchEngine(fieldRegister: ProductSearchEngineFieldRegister): SearchEngine[UUID, Product, ProductFacets] = {
    new SolrSearchEngine[UUID, Product, ProductFacets](
      new HttpSolrClientFactory(config),
      new ProductSolrIsomorphism(fieldRegister)
    )
  }

}
