package com.sustainasearch.searchengine

case class Query(mainQuery: String, fuzzy: Boolean = false, maybeSort: Option[Sort] = None) {
  def withAscendingSort(field: String): Query = copy(maybeSort = Some(Sort(field = field, order = Ascending)))

  def withDescendingSort(field: String): Query = copy(maybeSort = Some(Sort(field = field, order = Descending)))
}

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

