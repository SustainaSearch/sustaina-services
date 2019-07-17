package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine._

import scala.collection.JavaConverters._

class SolrSearchEngine[D](solrClientFactory: SolrClientFactory,
                          solrMorphism: SolrMorphism[D]) extends SearchEngine[D] {
  private val solrClient = solrClientFactory.createSolrClient

  override def query(query: Query): QueryResponse[D] = {
    val solrParams = SolrParamsFactory.createSolrParams(query)
    val solrQueryResponse = solrClient.query(solrParams)
    solrMorphism.fromSolrQueryResponse(solrQueryResponse)
  }

  override def add(documents: D*): Unit = {
    val solrInputDocuments = documents
      .map(solrMorphism.toSolrInputDocument)
      .asJava
    solrClient.add(solrInputDocuments)
    solrClient.commit()
  }

}
