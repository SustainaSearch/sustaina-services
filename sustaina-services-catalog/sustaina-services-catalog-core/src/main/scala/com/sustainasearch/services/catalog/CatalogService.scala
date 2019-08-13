package com.sustainasearch.services.catalog

import com.sustainasearch.searchengine.QueryResponse
import com.sustainasearch.services.catalog.products.facets.ProductFacets
import com.sustainasearch.services.catalog.products.{ProductFacet, ProductQuery, ProductService, SimpleProduct}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CatalogService @Inject()(productService: ProductService)(implicit ec: ExecutionContext) {

  def query(catalogQuery: CatalogQuery): Future[CatalogQueryResponse] = {
    val productQuery = ProductQuery(catalogQuery)
      .withSortByDescendingSustainaIndex
      .withSortByNearestSpatialResult
      .withFacet(ProductFacet.Brand)
      .withFacet(ProductFacet.Category)

    for {
      productResponse <- productService.query(productQuery)
    } yield {
      CatalogQueryResponse(
        products = QueryResponse[SimpleProduct, ProductFacets] (
          start = productResponse.start,
          numFound = productResponse.numFound,
          documents = productResponse.documents.map(SimpleProduct(_)),
          facets = productResponse.facets
        )
      )
    }
  }

}
