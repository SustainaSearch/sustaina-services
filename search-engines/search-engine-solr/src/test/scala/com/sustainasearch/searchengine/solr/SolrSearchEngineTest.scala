package com.sustainasearch.searchengine.solr

import java.nio.file.Paths

import com.sustainasearch.searchengine.Query
import com.sustainasearch.searchengine.solr.embedded.{EmbeddedSolrClientFactory, EmbeddedSolrConfig}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

class SolrSearchEngineTest extends WordSpec with Matchers with SolrTestFixture with BeforeAndAfterAll {
  private var underTest: SolrSearchEngine[SolrTestDocument] = _

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

    underTest = new SolrSearchEngine[SolrTestDocument](
      new EmbeddedSolrClientFactory(config),
      new SolrTestDocumentSolrInputDocumentConverter,
      new SolrTestDocumentSolrQueryResponseConverter
    )

    underTest.add(firstDoc, secondDoc)
  }

  "SolrSearchEngine" should {
    "find all documents" in {
      val response = underTest.query(Query(mainQuery = "*:*"))
      response.numFound shouldBe 2
      response.documents should contain only(firstDoc, secondDoc)
    }

    "find documents by specific field" in {
      val response1 = underTest.query(Query(mainQuery = "name:First"))
      response1.numFound shouldBe 1
      response1.documents.head shouldBe firstDoc

      val response2 = underTest.query(Query(mainQuery = "name:Second"))
      response2.numFound shouldBe 1
      response2.documents.head shouldBe secondDoc

      val response3 = underTest.query(Query(mainQuery = "name:doc"))
      response3.numFound shouldBe 2
      response3.documents should contain only(firstDoc, secondDoc)
    }

    "find documents with fuzzy search" in {
      val response = underTest.query(Query(mainQuery = "name:Secd~2"))
      response.numFound shouldBe 1
      response.documents.head shouldBe secondDoc
    }

    "sort documents" in {
      val response1 = underTest.query(Query(mainQuery = "name:doc").withAscendingSort("id"))
      response1.numFound shouldBe 2
      response1.documents should contain inOrder(firstDoc, secondDoc)

      val response2 = underTest.query(Query(mainQuery = "name:doc").withDescendingSort("id"))
      response2.numFound shouldBe 2
      response2.documents should contain inOrder(secondDoc, firstDoc)
    }

    "find documents by searching across multiple fields" in {
      val response1 = underTest.query(Query(mainQuery = "doc"))
      response1.numFound shouldBe 2
      response1.documents should contain only(firstDoc, secondDoc)

      // id field is not copied to the default search field
      val response2 = underTest.query(Query(mainQuery = firstDoc.id))
      response2.numFound shouldBe 0
    }
  }
}