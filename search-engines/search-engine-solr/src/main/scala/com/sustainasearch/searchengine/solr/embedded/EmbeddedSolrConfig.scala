package com.sustainasearch.searchengine.solr.embedded

import com.sustainasearch.searchengine.solr.SolrConfig

trait EmbeddedSolrConfig extends SolrConfig {
  val solrHomeDirectory: String
  val coreName: String
}
