package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine._

import scala.collection.JavaConverters._

class SolrSearchEngine[D, F](solrClientFactory: SolrClientFactory,
                          solrMorphism: SolrMorphism[D, F]) extends SearchEngine[D, F] {
  private val solrClient = solrClientFactory.createSolrClient

  override def query(query: Query): QueryResponse[D, F] = {
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
