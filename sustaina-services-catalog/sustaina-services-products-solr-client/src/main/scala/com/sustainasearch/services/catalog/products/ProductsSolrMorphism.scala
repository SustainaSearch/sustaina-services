package com.sustainasearch.services.catalog.products

import java.util.UUID

import com.sustainasearch.searchengine.QueryResponse
import com.sustainasearch.searchengine.solr.SolrMorphism
import org.apache.solr.client.solrj.response.{QueryResponse => SolrQueryResponse}
import org.apache.solr.common.SolrInputDocument

import scala.collection.JavaConverters._

object ProductsSolrMorphism extends SolrMorphism[ProductContainer] {

  override def toSolrInputDocument(productContainer: ProductContainer): SolrInputDocument = {
    val solrInputDocument = new SolrInputDocument()
    solrInputDocument.addField("id", productContainer.id.toString)
    solrInputDocument.addField("name", productContainer.product.name)
    solrInputDocument
  }

  override def fromSolrQueryResponse(response: SolrQueryResponse): QueryResponse[ProductContainer] = {
    val results = response.getResults
    val documents = results.asScala.map { document =>
      val id = document.getFirstValue("id").asInstanceOf[String]
      val name = document.getFirstValue("name").asInstanceOf[String]
      val product = Product(name = name)
      ProductContainer(UUID.fromString(id), product)
    }
    QueryResponse(
      numFound = results.getNumFound,
      documents.toList
    )
  }

}
