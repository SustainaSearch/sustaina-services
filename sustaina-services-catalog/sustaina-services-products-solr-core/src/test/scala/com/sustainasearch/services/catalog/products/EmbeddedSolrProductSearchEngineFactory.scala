package com.sustainasearch.services.catalog.products

import java.nio.file.Paths

import com.sustainasearch.searchengine.SearchEngine
import com.sustainasearch.searchengine.solr.SolrSearchEngine
import com.sustainasearch.searchengine.solr.embedded.{EmbeddedSolrClientFactory, EmbeddedSolrConfig}

class EmbeddedSolrProductSearchEngineFactory extends ProductSearchEngineFactory {
  override def createSearchEngine: SearchEngine[ProductContainer] = {
    val solrXmlUri = getClass
      .getResource("/sustaina-products/solr.xml")
      .toURI
    val solrHome = Paths
      .get(solrXmlUri)
      .getParent
      .toAbsolutePath
      .toString

    val config = EmbeddedSolrConfig(
      solrHomeDirectory = solrHome,
      coreName = "sustaina-products"
    )

    new SolrSearchEngine[ProductContainer](
      new EmbeddedSolrClientFactory(config),
      ProductsSolrMorphism
    )
  }
}