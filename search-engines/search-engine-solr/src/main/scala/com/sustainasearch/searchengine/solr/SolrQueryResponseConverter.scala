package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine.{QueryResponse, QueryResponseConverter}
import org.apache.solr.client.solrj.response.{QueryResponse => SolrQueryResponse}

class SolrQueryResponseConverter[Document] extends QueryResponseConverter[Document, SolrQueryResponse] {

  override def convertFrom(searchEngineQueryResponse: SolrQueryResponse): QueryResponse[Document] = {
    ???
  }
}
