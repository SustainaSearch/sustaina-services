package com.sustainasearch.searchengine.solr.http

import com.sustainasearch.searchengine.solr.SolrClientFactory
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.impl.HttpSolrClient

class HttpSolrClientFactory(config: HttpSolrConfig) extends SolrClientFactory {

  override def createSolrClient: SolrClient = {
    new HttpSolrClient.Builder(config.baseSolrUrl).build()
  }

}
