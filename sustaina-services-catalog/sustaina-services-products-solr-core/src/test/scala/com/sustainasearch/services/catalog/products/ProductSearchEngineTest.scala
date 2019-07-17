package com.sustainasearch.services.catalog.products

import java.util.UUID

import com.sustainasearch.searchengine.Query
import org.scalatest.{Matchers, WordSpec}

// TODO: improve test to verify Solr config for ProductContainers
class ProductSearchEngineTest extends WordSpec with Matchers {

  classOf[EmbeddedSolrProductSearchEngineFactory].getSimpleName should {
    "create an embedded Solr SearchEngine for ProductContainers" in {
      val searchEngineFactory = new EmbeddedSolrProductSearchEngineFactory
      val underTest = searchEngineFactory.createSearchEngine
      val productContainer = ProductContainer(UUID.fromString("00000000-0000-0000-0000-000000000000"), Product(name = "productName1"))
      underTest.add(productContainer)
      val response = underTest.query(Query(mainQuery = "*:*"))
      response.numFound shouldBe 1
      response.documents.head shouldBe productContainer
    }
  }

}
