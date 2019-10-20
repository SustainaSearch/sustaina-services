package com.sustainasearch.services.catalog.products

import java.util.UUID

import com.sustainasearch.searchengine
import com.sustainasearch.searchengine._
import com.sustainasearch.services.catalog.products.CategoryType.CategoryType
import com.sustainasearch.services.catalog.products.facets.ProductFacets
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductService @Inject()(searchEngineFactory: ProductSearchEngineFactory,
                               searchEngineFieldRegister: ProductSearchEngineFieldRegister,
                               productCategoryService: ProductCategoryService)(implicit ec: ExecutionContext) {

  import searchEngineFieldRegister._

  private val searchEngine = searchEngineFactory.createSearchEngine(searchEngineFieldRegister)

  def getById(id: UUID): Future[Option[ProductContainer]] = Future {
    searchEngine.getById(id).map { product =>
      ProductContainer(
        product
      )
    }
  }

  def queryProductCategory(categoryType: CategoryType, productQuery: ProductQuery): Future[ProductCategoryQueryResponse] = {
    val productCategoryQuery = productQuery
      .withFilterQuery(TextFilterQuery(CategoryTypeField, categoryType))
      .withSortByDescendingSustainaIndex
      .withSortByNearestSpatialResult

    for {
      productQueryResponse <- query(productCategoryQuery)
      maybeCategory <- productCategoryService.findCategory(categoryType)
    } yield {
      ProductCategoryQueryResponse(
        productQueryResponse = productQueryResponse,
        filterQueries = maybeCategory.fold(Seq.empty[FilterQueryContainer]) { category =>
          category
            .filters
            .flatMap { categoryFilter =>
              categoryFilter.filterType match {
                case CategoryFilterType.HasCertification => Seq(
                  FilterQueryContainer(categoryFilter.names, BooleanFilterQuery("TODO", true)),
                )
                case CategoryFilterType.ZeroToThreeMonths => Seq(
                  FilterQueryContainer(categoryFilter.names, searchengine.RangeFilterQuery("TODO", from = 0, to = 3)),
                )
              }
            }
        }
      )
    }
  }

  def query(productQuery: ProductQuery): Future[QueryResponse[Product, ProductFacets]] = {
    val searchEngineQuery = productQuery
      .sort
      .foldLeft(
        Query(
          mainQuery = productQuery.mainQuery,
          start = productQuery.start,
          rows = productQuery.rows,
          filterQueries = productQuery.filterQueries,
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
    val productResponse = searchEngine.query(searchEngineQuery)
    val categoryTypes = productResponse.facets.categories.map(_.categoryType)

    for {
      categoryNames <- productCategoryService.categoryNames(categoryTypes)
    } yield {
      val categoryFacets = productResponse.facets.categories.map { categoryFacet =>
        categoryFacet.copy(names = categoryNames.getOrElse(categoryFacet.categoryType, Seq.empty))
      }
      val facets = productResponse.facets.copy(categories = categoryFacets)
      productResponse.copy(facets = facets)
    }
  }

  def add(product: Product): Future[Product] = {
    Future {
      searchEngine.add(product)
      product
    }
  }

}
