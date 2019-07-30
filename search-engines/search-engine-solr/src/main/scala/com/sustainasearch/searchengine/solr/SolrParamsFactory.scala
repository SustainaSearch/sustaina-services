package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine.Query
import org.apache.solr.common.params.{ModifiableSolrParams, SolrParams}

import scala.collection.JavaConverters._

object SolrParamsFactory {

  def createSolrParams(query: Query): SolrParams = {
    val mainQuery = {
      if (query.fuzzy) s"${query.mainQuery}~" else query.mainQuery
    }

    val filterQueries = Map("fq" -> query.filterQueries.toArray)

    val sortParam = query
      .maybeSort
      .fold(Map.empty[String, Array[String]])(sort => Map("sort" -> Array(s"${sort.field} ${sort.order.value}")))

    val queryParams = Map(
      "q" -> Array(mainQuery),
      "qt" -> Array("/select")
    ) ++ filterQueries ++ sortParam

    new ModifiableSolrParams(queryParams.asJava)
  }
}
