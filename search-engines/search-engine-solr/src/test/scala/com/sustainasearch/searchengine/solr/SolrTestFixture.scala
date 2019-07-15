package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine.QueryResponse
import org.apache.solr.client.solrj.response.{QueryResponse => SolrQueryResponse}
import org.apache.solr.common.SolrInputDocument
import scala.collection.JavaConverters._

trait SolrTestFixture {

  case class SolrTestDocument(id: String, name: String)

  sealed class SolrTestDocumentSolrInputDocumentConverter extends SolrInputDocumentConverter[SolrTestDocument] {
    override def convertFrom(document: SolrTestDocument): SolrInputDocument = {
      val solrInputDocument = new SolrInputDocument()
      solrInputDocument.addField("id", document.id)
      solrInputDocument.addField("name", document.name)
      solrInputDocument
    }
  }

  sealed class SolrTestDocumentSolrQueryResponseConverter extends SolrQueryResponseConverter[SolrTestDocument] {
    override def convertFrom(searchEngineQueryResponse: SolrQueryResponse): QueryResponse[SolrTestDocument] = {
      val results = searchEngineQueryResponse.getResults
      val documents = results.asScala.map { document =>
        val id = document.getFirstValue("id").asInstanceOf[String]
        val name = document.getFirstValue("name").asInstanceOf[String]
        SolrTestDocument(id, name)
      }
      QueryResponse(
        numFound = results.getNumFound,
        documents.toList
      )
    }
  }

  val firstDoc = SolrTestDocument(id = "1", name = "First doc")
  val secondDoc = SolrTestDocument(id = "2", name = "Second doc")
}
