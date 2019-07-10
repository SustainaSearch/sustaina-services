package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine._
import org.apache.solr.client.solrj.SolrClient

class SolrSearchEngine[D](solrClientFactory: SolrClientFactory, inputDocumentConverter: SolrInputDocumentConverter[D]) extends SearchEngine[D] {
  private val solrClient: SolrClient = solrClientFactory.createSolrClient
  private val queryConverter = new SolrQueryConverter
  private val queryResponseConverter = new SolrQueryResponseConverter[D]

  override def query(query: Query): QueryResponse[D] = {
    val solrQuery = queryConverter.convertFrom(query)
    val solrQueryResponse = solrClient.query(solrQuery)
    queryResponseConverter.convertFrom(solrQueryResponse)
  }

  override def add(document: D): Unit = {
    val solrInputDocument = inputDocumentConverter.convertFrom(document)
    solrClient.add(solrInputDocument)
  }

}
