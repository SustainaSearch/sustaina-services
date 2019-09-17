package com.sustainasearch.services.catalog.categories

import com.sustainasearch.searchengine.SearchEngine
import com.sustainasearch.searchengine.solr.SolrSearchEngine
import com.sustainasearch.searchengine.solr.http.{HttpSolrClientFactory, HttpSolrConfig}
import com.sustainasearch.services.catalog.Category
import com.sustainasearch.services.catalog.CategoryType.CategoryType

class HttpSolrProductCategorySearchEngineFactory(config: HttpSolrConfig) extends ProductCategorySearchEngineFactory {

  override def createSearchEngine(fieldRegister: ProductCategorySearchEngineFieldRegister): SearchEngine[CategoryType, Category, Option[Nothing]] = {
    new SolrSearchEngine[CategoryType, Category, Option[Nothing]](
      new HttpSolrClientFactory(config),
      new ProductCategorySolrIsomorphism(fieldRegister)
    )
  }

}
