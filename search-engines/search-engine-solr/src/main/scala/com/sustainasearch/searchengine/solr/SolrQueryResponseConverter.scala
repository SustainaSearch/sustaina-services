package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine.QueryResponseConverter
import org.apache.solr.client.solrj.response.{QueryResponse => SolrQueryResponse}

trait SolrQueryResponseConverter[Document] extends QueryResponseConverter[Document, SolrQueryResponse]
