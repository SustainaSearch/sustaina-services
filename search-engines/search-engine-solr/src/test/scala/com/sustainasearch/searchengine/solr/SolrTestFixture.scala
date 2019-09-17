package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine.QueryResponse
import org.apache.solr.client.solrj.response.{QueryResponse => SolrQueryResponse}
import org.apache.solr.common.{SolrDocument, SolrInputDocument}

import scala.collection.JavaConverters._

trait SolrTestFixture {

  case class SolrTestDocument(id: String, name: String)

  case class SolrTestFacet()

  object TestSolrIsomorphism extends SolrIsomorphism[SolrTestDocument, Seq[SolrTestFacet]] {
    override def toSolrInputDocument(document: SolrTestDocument): SolrInputDocument = {
      val solrInputDocument = new SolrInputDocument()
      solrInputDocument.addField("id", document.id)
      solrInputDocument.addField("name", document.name)
      solrInputDocument
    }

    override def fromSolrQueryResponse(response: SolrQueryResponse): QueryResponse[SolrTestDocument, Seq[SolrTestFacet]] = {
      val results = response.getResults
      val documents = results
        .asScala
        .map(fromSolrDocument)
        .toList
      QueryResponse(
        start = 0,
        numFound = results.getNumFound,
        documents = documents,
        facets = Seq.empty[SolrTestFacet]
      )
    }

    override def fromSolrDocument(document: SolrDocument): SolrTestDocument = {
      val id = document.getFirstValue("id").asInstanceOf[String]
      val name = document.getFirstValue("name").asInstanceOf[String]
      SolrTestDocument(id, name)
    }
  }

  val firstDoc = SolrTestDocument(id = "1", name = "First doc")
  val secondDoc = SolrTestDocument(id = "2", name = "Second doc")
}
