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

  def queryProductCategory(categoryType: CategoryType, rawProductQuery: ProductQuery): Future[QueryResponse[Product, ProductFacets]] = {
    val productCategoryQuery = rawProductQuery
      .withFilterQuery(SpecificFieldFilterQuery(CategoryTypeField, categoryType))
      .withSortByDescendingSustainaIndex
      .withSortByNearestSpatialResult
    query(productCategoryQuery)
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
    val eventualCategories = productResponse.facets.categories.map { categoryFacet =>
      findCategory(categoryFacet.categoryType)
    }

    for {
      // TODO: use the ProductCategoryService to get the categories
      categories <- Future.sequence(eventualCategories)
    } yield {
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
      productResponse.copy(facets = facets)
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

  def filterQueriesForCategoryType(categoryType: CategoryType): Future[Seq[FilterQuery]] = {
    ???
  }

  def add(product: Product): Future[Product] = {
    Future {
      searchEngine.add(product)
      product
    }
  }

}
