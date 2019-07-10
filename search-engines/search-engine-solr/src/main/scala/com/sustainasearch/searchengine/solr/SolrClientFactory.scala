package com.sustainasearch.searchengine.solr

import org.apache.solr.client.solrj.SolrClient

trait SolrClientFactory {
  self: SolrConfig =>

  def createSolrClient: SolrClient
}
