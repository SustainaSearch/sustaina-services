package com.sustainasearch.services.catalog

import com.sustainasearch.searchengine.{NearestResult, Query}
import com.sustainasearch.services.catalog.products.ProductService
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CatalogService @Inject()(productService: ProductService)(implicit ec: ExecutionContext) {

  def query(query: Query): Future[CatalogQueryResponse] = {
    // TODO: find a way to decouple from the solr field names
    val productQuery = query
      .maybeLatitudeLongitude
      .fold(query)(_ => query.withNearestResultBoostFunction("representativePoint"))
      .withDescendingSort("sustainaIndex")

    for {
      products <- productService.query(productQuery)
    } yield {
      CatalogQueryResponse(
        products
      )
    }
  }

}
