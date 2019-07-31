package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine.Order
import com.sustainasearch.searchengine.Order.Order

sealed trait SolrOrder {
  def value: String
}

object SolrOrder {

  def apply(order: Order): SolrOrder = {
    order match {
      case Order.Ascending => Ascending
      case Order.Descending => Descending
    }
  }

}

case object Ascending extends SolrOrder {
  val value = "asc"
}

case object Descending extends SolrOrder {
  val value = "desc"
}
