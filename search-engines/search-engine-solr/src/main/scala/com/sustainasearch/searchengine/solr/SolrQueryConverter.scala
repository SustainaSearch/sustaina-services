package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine.{Query, QueryConverter}
import org.apache.solr.common.params.SolrParams

class SolrQueryConverter extends QueryConverter[SolrParams] {

  override def convertFrom(query: Query): SolrParams = {
    ???
  }
}
