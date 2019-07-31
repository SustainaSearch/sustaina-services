package com.sustainasearch.searchengine

import com.sustainasearch.searchengine.Order.Order

case class Query(mainQuery: String,
                 filterQueries: Seq[String] = Seq.empty,
                 fuzzy: Boolean = false,
                 sort: Seq[Sort] = Seq.empty,
                 maybeLatitudeLongitude: Option[String] = None,
                 maybeBoostFunction: Option[BoostFunction] = None) {

  def withFilterQuery(filterQuery: String): Query = copy(filterQueries = filterQueries :+ filterQuery)

  def withAscendingSort(field: String): Query = copy(sort = sort :+ Sort(field = field, order = Order.Ascending))

  def withDescendingSort(field: String): Query = copy(sort = sort :+ Sort(field = field, order = Order.Descending))

  def withBoostFunction(boostFunction: BoostFunction): Query = copy(maybeBoostFunction = Some(boostFunction))

  def withNearestResultBoostFunction(spatialField: String): Query = {
    require(maybeLatitudeLongitude.isDefined, "'maybeLatitudeLongitude' must be defined when NearestResult boost function should be used")
    withBoostFunction(
      NearestResult(
        latitudeLongitude = maybeLatitudeLongitude.get,
        spatialField)
    )
  }
}

case class Sort(field: String, order: Order)

object Order extends Enumeration {
  type Order = Value
  val Ascending, Descending = Value
}

trait BoostFunction

case class NearestResult(latitudeLongitude: String, spatialField: String) extends BoostFunction

