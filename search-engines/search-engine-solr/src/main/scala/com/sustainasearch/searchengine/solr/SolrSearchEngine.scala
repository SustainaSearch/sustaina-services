package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine._

import scala.collection.JavaConverters._

class SolrSearchEngine[Id, Document, Facets](solrClientFactory: SolrClientFactory,
                                             solrIsomorphism: SolrIsomorphism[Document, Facets]) extends SearchEngine[Id, Document, Facets] {
  private val solrClient = solrClientFactory.createSolrClient

  override def query(query: Query): QueryResponse[Document, Facets] = {
    val solrParams = SolrParamsFactory.createSolrParams(query)
    val solrQueryResponse = solrClient.query(solrParams)
    solrIsomorphism.fromSolrQueryResponse(solrQueryResponse)
  }

  override def add(documents: Document*): Unit = {
    val solrInputDocuments = documents
      .map(solrIsomorphism.toSolrInputDocument)
      .asJava
    solrClient.add(solrInputDocuments)
    solrClient.commit()
  }

  override def getById(id: Id): Option[Document] = {
    val solrDocument = solrClient.getById(id.toString)
    if (solrDocument == null) None
    else Some(solrIsomorphism.fromSolrDocument(solrDocument))
  }
}
