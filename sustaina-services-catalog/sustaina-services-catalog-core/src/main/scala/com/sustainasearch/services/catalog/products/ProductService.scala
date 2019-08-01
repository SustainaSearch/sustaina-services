package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine.{Query, QueryResponse}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductService @Inject()(searchEngineFactory: ProductSearchEngineFactory, searchEngineFieldRegister: ProductSearchEngineFieldRegister)(implicit ec: ExecutionContext) {

  import searchEngineFieldRegister._

  private val searchEngine = searchEngineFactory.createSearchEngine(searchEngineFieldRegister)

  def query(productQuery: ProductQuery): Future[QueryResponse[Product]] = {
    Future {
      val searchEngineQuery = productQuery
        .sort
        .foldLeft(
          Query(
            mainQuery = productQuery.mainQuery,
            start = productQuery.start,
            rows = productQuery.rows,
            fuzzy = productQuery.fuzzy,
            maybeSpatialPoint = productQuery.maybeSpatialPoint
          )
        ) { (prev, sort) =>
          sort match {
            case ProductSort.DescendingSustainaIndex => prev.withDescendingSort(SustainaIndexField)
            case ProductSort.NearestSpatialResult =>
              if (prev.maybeSpatialPoint.isDefined)
                prev.withNearestSpatialResultBoostFunction(RepresentativePointField)
              else
                prev
          }
        }

      searchEngine.query(searchEngineQuery)
    }
  }

  def add(product: Product): Future[Product] = {
    Future {
      searchEngine.add(product)
      product
    }
  }

}
