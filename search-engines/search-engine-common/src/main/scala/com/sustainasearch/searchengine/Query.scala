package com.sustainasearch.searchengine

case class Query(query: String, maybeSort: Option[Sort] = None)

case class Sort(field: String, order: Order)

sealed trait Order {
  def value: String
}

case object Ascending extends Order {
  val value = "asc"
}

case object Descending extends Order {
  val value = "desc"
}

case class UnknownOrder(value: String) extends Order

