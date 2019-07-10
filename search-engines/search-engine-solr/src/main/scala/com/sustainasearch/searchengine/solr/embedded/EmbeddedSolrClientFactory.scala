package com.sustainasearch.searchengine.solr.embedded

import com.sustainasearch.searchengine.solr.SolrClientFactory
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer
import org.apache.solr.core.CoreContainer

trait EmbeddedSolrClientFactory extends SolrClientFactory {
  self: EmbeddedSolrConfig =>

  override def createSolrClient: SolrClient = {
//    val container = new CoreContainer(solrHomeDirectory.)
//    container.load()
//    new EmbeddedSolrServer(container, coreName)
    ???
  }
}
