package com.sustainasearch.services.catalog

import com.sustainasearch.services.catalog.products.{ProductQuery, ProductService}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CatalogService @Inject()(productService: ProductService)(implicit ec: ExecutionContext) {

  def query(catalogQuery: CatalogQuery): Future[CatalogQueryResponse] = {
    val productQuery = ProductQuery(catalogQuery)
      .withSortByDescendingSustainaIndex
      .withSortByNearestSpatialResult

    for {
      products <- productService.query(productQuery)
    } yield {
      CatalogQueryResponse(
        products
      )
    }
  }

}
