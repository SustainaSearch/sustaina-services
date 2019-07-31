package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine.{NearestResult, Query}
import org.apache.solr.common.params.{ModifiableSolrParams, SolrParams}

import scala.collection.JavaConverters._
import scala.collection.mutable.{ListBuffer, Map}

object SolrParamsFactory {

  def createSolrParams(query: Query): SolrParams = {
    val mainQuery = {
      if (query.fuzzy) s"${query.mainQuery}~" else query.mainQuery
    }

    var queryParams = Map.empty[String, Array[String]]
    queryParams += ("q" -> Array(mainQuery))
    queryParams += ("fq" -> query.filterQueries.toArray)

    var sortParams = ListBuffer.empty[String]
    query.sort.foreach { sort =>
      val order = SolrOrder(sort.order).value
      sortParams += s"${sort.field} $order"
    }

    query
      .maybeBoostFunction
      .foreach {
        case NearestResult(latitudeLongitude, spatialField) =>
          queryParams += ("defType" -> Array("edismax"))
          queryParams += ("bf" -> Array("recip(geodist(),2,200,20)"))
          queryParams += ("pt" -> Array(latitudeLongitude))
          queryParams += ("sfield" -> Array(spatialField))
          sortParams += s"score ${Descending.value}"
      }

    queryParams += ("sort" -> Array(sortParams.mkString(",")))

    new ModifiableSolrParams(queryParams.asJava)
  }

}
