package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine.{Query, QueryConverter}
import org.apache.solr.common.params.{MapSolrParams, SolrParams}

import scala.collection.JavaConverters._

object SolrQueryConverter extends QueryConverter[SolrParams] {

  override def convertFrom(query: Query): SolrParams = {
    val sortParam = query
      .maybeSort
      .fold(Map.empty[String, String])(sort => Map("sort" -> s"${sort.field} ${sort.order.value}"))
    val mainQuery = {
      if (query.fuzzy) s"${query.mainQuery}~" else query.mainQuery
    }
    val queryParams = Map(
      "q" -> mainQuery,
      "qt" -> "/select"
    ) ++ sortParam
    new MapSolrParams(queryParams.asJava)
  }
}
