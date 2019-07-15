package com.sustainasearch.searchengine.solr

import org.apache.solr.client.solrj.SolrClient

trait SolrClientFactory {

  def createSolrClient: SolrClient
}
