package com.sustainasearch.services.catalog

import com.sustainasearch.searchengine.Query
import com.sustainasearch.services.catalog.products.ProductService
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CatalogService @Inject()(productService: ProductService)(implicit ec: ExecutionContext) {

  def query(query: Query): Future[CatalogQueryResponse] = {
    for {
      products <- productService.query(query.withDescendingSort("sustainaIndex"))
    } yield {
      CatalogQueryResponse(
        products
      )
    }
  }

}
