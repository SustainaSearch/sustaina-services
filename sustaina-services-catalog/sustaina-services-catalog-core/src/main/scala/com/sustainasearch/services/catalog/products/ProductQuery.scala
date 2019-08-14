package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine.{FilterQuery, Query, SpatialPoint}
import com.sustainasearch.services.catalog.products.ProductSort.ProductSort
import com.sustainasearch.services.catalog.CatalogQuery
import com.sustainasearch.services.catalog.products.ProductFacet.ProductFacet

case class ProductQuery(mainQuery: String,
                        start: Long = 0,
                        rows: Long = 10,
                        filterQueries: Seq[FilterQuery] = Seq.empty,
                        fuzzy: Boolean = false,
                        maybeSpatialPoint: Option[SpatialPoint] = None,
                        sort: Seq[ProductSort] = Seq.empty,
                        facets: Seq[ProductFacet] = Seq.empty) {

  def withFilterQuery(filterQuery: FilterQuery): ProductQuery = copy(filterQueries = filterQueries :+ filterQuery)

  def withSortByDescendingSustainaIndex: ProductQuery = copy(sort = sort :+ ProductSort.DescendingSustainaIndex)

  def withSortByNearestSpatialResult: ProductQuery = copy(sort = sort :+ ProductSort.NearestSpatialResult)

  def withFacet(facet: ProductFacet): ProductQuery = copy(facets = facets :+ facet)
}

object ProductQuery {

  def apply(query: CatalogQuery): ProductQuery = {
    ProductQuery(
      mainQuery = query.mainQuery,
      start = query.start,
      rows = query.rows,
      fuzzy = query.fuzzy,
      maybeSpatialPoint = query.maybeSpatialPoint
    )
  }

}

object ProductSort extends Enumeration {
  type ProductSort = Value
  val DescendingSustainaIndex, NearestSpatialResult = Value
}

object ProductFacet extends Enumeration {
  type ProductFacet = Value
  val Brand, Category = Value
}
