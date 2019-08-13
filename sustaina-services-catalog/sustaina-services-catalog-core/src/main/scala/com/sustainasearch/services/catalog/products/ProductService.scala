package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine._
import com.sustainasearch.services.catalog.products.CategoryType.CategoryType
import com.sustainasearch.services.catalog.products.facets.ProductFacets
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductService @Inject()(searchEngineFactory: ProductSearchEngineFactory, searchEngineFieldRegister: ProductSearchEngineFieldRegister)(implicit ec: ExecutionContext) {

  import searchEngineFieldRegister._

  private val searchEngine = searchEngineFactory.createSearchEngine(searchEngineFieldRegister)

  def query(productQuery: ProductQuery): Future[QueryResponse[Product, ProductFacets]] = {
    Future {
      val searchEngineQuery = productQuery
        .sort
        .foldLeft(
          Query(
            mainQuery = FreeTextQuery(productQuery.mainQuery),
            start = productQuery.start,
            rows = productQuery.rows,
            fuzzy = productQuery.fuzzy,
            maybeSpatialPoint = productQuery.maybeSpatialPoint,
            facetFields = productQuery.facets.map {
              case ProductFacet.Brand => BrandNameExactField
              case ProductFacet.Category => CategoryTypeField
            }
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

      // TODO: populate Category names already here or in CatalogService?
      searchEngine.query(searchEngineQuery)
    }
  }

  // TODO: move to ProductCategoryService to get the categories
  def findCategory(categoryType: CategoryType): Future[Option[Category]] = {
    // TODO: get Category names in another way, when there is no guarantee all names are available in the found product
    Future {
      val query = Query(
        mainQuery = AllDocumentsQuery,
        rows = 1
      ).withFilterQuery(
        SpecificFieldFilterQuery(
          fieldName = CategoryTypeField,
          fieldValue = categoryType.toString
        )
      )
      val response = searchEngine.query(query)
      response
        .documents
        .headOption
        .map(_.category)
    }
  }

  def add(product: Product): Future[Product] = {
    Future {
      searchEngine.add(product)
      product
    }
  }

}
