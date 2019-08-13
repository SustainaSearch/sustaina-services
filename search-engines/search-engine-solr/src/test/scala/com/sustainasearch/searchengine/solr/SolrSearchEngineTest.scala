package com.sustainasearch.searchengine.solr

import java.nio.file.Paths

import com.sustainasearch.searchengine.solr.embedded.{EmbeddedSolrClientFactory, EmbeddedSolrConfig}
import com.sustainasearch.searchengine.{AllDocumentsQuery, FreeTextQuery, Query}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

class SolrSearchEngineTest extends WordSpec with Matchers with SolrTestFixture with BeforeAndAfterAll {
  private var underTest: SolrSearchEngine[SolrTestDocument, Seq[SolrTestFacet]] = _

  override def beforeAll(): Unit = {
    val solrHome = Paths
      .get(
        getClass
          .getResource("/embedded-solr/solr.xml")
          .toURI
      )
      .getParent
      .toAbsolutePath
      .toString

    val config = EmbeddedSolrConfig(
      solrHomeDirectory = solrHome,
      coreName = "test_embedded"
    )

    underTest = new SolrSearchEngine[SolrTestDocument, Seq[SolrTestFacet]](
      new EmbeddedSolrClientFactory(config),
      TestSolrMorphism
    )

    underTest.add(firstDoc, secondDoc)
  }

  "SolrSearchEngine" should {
    "find all documents" in {
      val response = underTest.query(Query(mainQuery = AllDocumentsQuery))
      response.numFound shouldBe 2
      response.documents should contain only(firstDoc, secondDoc)
    }

    "find documents by specific field" in {
      val response1 = underTest.query(Query(mainQuery = FreeTextQuery("name:First")))
      response1.numFound shouldBe 1
      response1.documents.head shouldBe firstDoc

      val response2 = underTest.query(Query(mainQuery = FreeTextQuery("name:Second")))
      response2.numFound shouldBe 1
      response2.documents.head shouldBe secondDoc

      val response3 = underTest.query(Query(mainQuery = FreeTextQuery("name:doc")))
      response3.numFound shouldBe 2
      response3.documents should contain only(firstDoc, secondDoc)
    }

    "find documents with fuzzy search" in {
      val response1 = underTest.query(Query(mainQuery = FreeTextQuery("name:Secd"), fuzzy = true))
      response1.numFound shouldBe 1
      response1.documents.head shouldBe secondDoc

      // Finds the actual value as well
      val response2 = underTest.query(Query(mainQuery = FreeTextQuery("name:Second"), fuzzy = true))
      response2.numFound shouldBe 1
      response2.documents.head shouldBe secondDoc

      val response3 = underTest.query(Query(mainQuery = FreeTextQuery("name:Secd")))
      response3.numFound shouldBe 0
    }

    "sort documents" in {
      val response1 = underTest.query(Query(mainQuery = FreeTextQuery("name:doc")).withAscendingSort("id"))
      response1.numFound shouldBe 2
      response1.documents should contain inOrder(firstDoc, secondDoc)

      val response2 = underTest.query(Query(mainQuery = FreeTextQuery("name:doc")).withDescendingSort("id"))
      response2.numFound shouldBe 2
      response2.documents should contain inOrder(secondDoc, firstDoc)
    }

    "find documents by searching across multiple fields" in {
      val response1 = underTest.query(Query(mainQuery = FreeTextQuery("doc")))
      response1.numFound shouldBe 2
      response1.documents should contain only(firstDoc, secondDoc)

      // id field is not copied to the default search field
      val response2 = underTest.query(Query(mainQuery = FreeTextQuery(firstDoc.id)))
      response2.numFound shouldBe 0
    }
  }
}