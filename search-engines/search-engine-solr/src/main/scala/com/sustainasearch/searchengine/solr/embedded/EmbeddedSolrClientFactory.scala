package com.sustainasearch.searchengine.solr.embedded

import com.sustainasearch.searchengine.solr.SolrClientFactory
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer
import org.apache.solr.core.CoreContainer

class EmbeddedSolrClientFactory(config: EmbeddedSolrConfig) extends SolrClientFactory {

  override def createSolrClient: SolrClient = {
    val container = new CoreContainer(config.solrHomeDirectory)
    container.load()
    new EmbeddedSolrServer(container, config.coreName)
  }

}
