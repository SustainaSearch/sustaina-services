package com.sustainasearch.services.catalog.products

import java.nio.file.Paths

import com.sustainasearch.searchengine.SearchEngine
import com.sustainasearch.searchengine.solr.SolrSearchEngine
import com.sustainasearch.searchengine.solr.embedded.{EmbeddedSolrClientFactory, EmbeddedSolrConfig}
import com.sustainasearch.services.catalog.products.facets.ProductFacets

class EmbeddedSolrProductSearchEngineFactory extends ProductSearchEngineFactory {

  override def createSearchEngine(fieldRegister: ProductSearchEngineFieldRegister): SearchEngine[Product, ProductFacets] = {
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

    new SolrSearchEngine[Product, ProductFacets](
      new EmbeddedSolrClientFactory(config),
      new ProductSolrMorphism(fieldRegister)
    )
  }
}
