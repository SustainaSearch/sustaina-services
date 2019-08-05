package com.sustainasearch.services.catalog

import com.sustainasearch.services.catalog.products.{ProductFacet, ProductQuery, ProductService}
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
      // TODO: use the ProductCategoryService to get the categories
      eventualCategories = productResponse.facets.categories.map { categoryFacet =>
        productService.findCategory(categoryFacet.categoryType)
      }
      categories <- Future.sequence(eventualCategories)
    } yield {
      // TODO: flatMap?
      val categoryNames = categories
        .flatten
        .map { category =>
          category.categoryType -> category.names
        }
        .toMap

      val categoryFacets = productResponse.facets.categories.map { categoryFacet =>
        categoryFacet.copy(names = categoryNames.getOrElse(categoryFacet.categoryType, Seq.empty))
      }
      val facets = productResponse.facets.copy(categories = categoryFacets)

      CatalogQueryResponse(
        productResponse.copy(facets = facets)
      )
    }
  }

}
