package com.sustainasearch.services.catalog.products

import java.nio.file.Paths
import java.util.UUID

import com.sustainasearch.searchengine.SearchEngine
import com.sustainasearch.searchengine.solr.SolrSearchEngine
import com.sustainasearch.searchengine.solr.embedded.{EmbeddedSolrClientFactory, EmbeddedSolrConfig}
import com.sustainasearch.services.catalog.products.facets.ProductFacets

class EmbeddedSolrProductSearchEngineFactory extends ProductSearchEngineFactory {

  override def createSearchEngine(fieldRegister: ProductSearchEngineFieldRegister): SearchEngine[UUID, Product, ProductFacets] = {
    val solrXmlUri = getClass
      .getResource("/sustaina-solr-cores/solr.xml")
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

    new SolrSearchEngine[UUID, Product, ProductFacets](
      new EmbeddedSolrClientFactory(config),
      new ProductSolrIsomorphism(fieldRegister)
    )
  }
}
