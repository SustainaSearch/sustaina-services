package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine.QueryResponse
import org.apache.solr.client.solrj.response.{QueryResponse => SolrQueryResponse}
import org.apache.solr.common.SolrInputDocument

trait SolrMorphism[Document, Facets] {

  def toSolrInputDocument(document: Document): SolrInputDocument

  def fromSolrQueryResponse(response: SolrQueryResponse): QueryResponse[Document, Facets]

}
