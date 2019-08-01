package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine.{NearestSpatialResult, Query}
import org.apache.solr.common.params.{ModifiableSolrParams, SolrParams}

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object SolrParamsFactory {

  def createSolrParams(query: Query): SolrParams = {
    var queryParams = mutable.Map.empty[String, Array[String]]
    var sortParams = ListBuffer.empty[String]

    def addSortParams(): Unit = {
      query.sort.foreach { sort =>
        val order = SolrOrder(sort.order).value
        sortParams += s"${sort.field} $order"
      }
    }

    def addBoostFunctionParams(): Unit = {
      query
        .maybeBoostFunction
        .foreach {
          case NearestSpatialResult(spatialPoint, spatialField) =>
            queryParams += ("defType" -> Array("edismax"))
            queryParams += ("bf" -> Array("recip(geodist(),2,200,20)"))
            queryParams += ("pt" -> Array(s"${spatialPoint.latitude},${spatialPoint.longitude}"))
            queryParams += ("sfield" -> Array(spatialField))
            sortParams += s"score ${Descending.value}"
        }
    }

    val mainQuery = {
      if (query.fuzzy) s"${query.mainQuery}~" else query.mainQuery
    }
    queryParams += ("q" -> Array(mainQuery))
    queryParams += ("fq" -> query.filterQueries.toArray)
    queryParams += ("start" -> Array(query.start.toString))
    queryParams += ("rows" -> Array(query.rows.toString))

    if (query.sortByBoostFunctionResultFirst) {
      addBoostFunctionParams()
      addSortParams()
    } else {
      addSortParams()
      addBoostFunctionParams()
    }

    queryParams += ("sort" -> Array(sortParams.mkString(",")))

    new ModifiableSolrParams(queryParams.asJava)
  }

}
