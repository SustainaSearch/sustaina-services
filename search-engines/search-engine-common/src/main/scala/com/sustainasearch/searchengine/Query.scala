package com.sustainasearch.searchengine

import com.sustainasearch.searchengine.Order.Order

case class Query(mainQuery: String,
                 start: Long = 0,
                 rows: Long = 10,
                 filterQueries: Seq[String] = Seq.empty,
                 fuzzy: Boolean = false,
                 sort: Seq[Sort] = Seq.empty,
                 maybeSpatialPoint: Option[SpatialPoint] = None,
                 maybeBoostFunction: Option[BoostFunction] = None,
                 sortByBoostFunctionResultFirst: Boolean = false,
                 facetFields: Seq[String] = Seq.empty) {

  def withFilterQuery(filterQuery: String): Query = copy(filterQueries = filterQueries :+ filterQuery)

  def withAscendingSort(field: String): Query = copy(sort = sort :+ Sort(field = field, order = Order.Ascending))

  def withDescendingSort(field: String): Query = copy(sort = sort :+ Sort(field = field, order = Order.Descending))

  def withBoostFunction(boostFunction: BoostFunction): Query = copy(maybeBoostFunction = Some(boostFunction))

  def withNearestSpatialResultBoostFunction(spatialField: String): Query = {
    require(maybeSpatialPoint.isDefined, "'maybeSpatialPoint' must be defined when NearestResult boost function should be used")
    withBoostFunction(
      NearestSpatialResult(
        spatialPoint = maybeSpatialPoint.get,
        spatialField = spatialField
      )
    )
  }

  def withFacetField(facetField: String): Query = copy(facetFields = facetFields :+ facetField)
}

case class Sort(field: String, order: Order)

object Order extends Enumeration {
  type Order = Value
  val Ascending, Descending = Value
}

trait BoostFunction

case class NearestSpatialResult(spatialPoint: SpatialPoint, spatialField: String) extends BoostFunction

