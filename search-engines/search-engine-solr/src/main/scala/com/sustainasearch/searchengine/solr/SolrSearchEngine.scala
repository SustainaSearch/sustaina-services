package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine._
import scala.collection.JavaConverters._

class SolrSearchEngine[D] (solrClientFactory: SolrClientFactory,
                                    inputDocumentConverter: SolrInputDocumentConverter[D],
                                    queryResponseConverter: SolrQueryResponseConverter[D]) extends SearchEngine[D] {
  private val solrClient = solrClientFactory.createSolrClient

  override def query(query: Query): QueryResponse[D] = {
    val solrQuery = SolrQueryConverter.convertFrom(query)
    val solrQueryResponse = solrClient.query(solrQuery)
    queryResponseConverter.convertFrom(solrQueryResponse)
  }

  override def add(documents: D*): Unit = {
    val solrInputDocuments = documents.map(inputDocumentConverter.convertFrom(_))
    solrClient.add(solrInputDocuments.asJava)
    solrClient.commit()
  }

}
