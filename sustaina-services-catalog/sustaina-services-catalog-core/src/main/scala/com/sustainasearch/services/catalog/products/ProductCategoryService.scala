package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine.{AllDocumentsQuery, Query}
import com.sustainasearch.services.LanguageCode.LanguageCode
import com.sustainasearch.services.catalog.products.CategoryType.CategoryType
import com.sustainasearch.services.{LanguageCode, Name}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductCategoryService @Inject()(searchEngineFactory: ProductCategorySearchEngineFactory, searchEngineFieldRegister: ProductCategorySearchEngineFieldRegister)(implicit ec: ExecutionContext) {

  import searchEngineFieldRegister._

  private val searchEngine = searchEngineFactory.createSearchEngine(searchEngineFieldRegister)

  def categoryNames(categoryTypes: Seq[CategoryType]): Future[Map[CategoryType, Seq[Name]]] = {
    for {
      categories <- Future.sequence(categoryTypes.map(findCategory))
    } yield {
      categories
        .flatMap { maybeCategory =>
          maybeCategory.map { category =>
            category.categoryType -> category.names
          }
        }
        .toMap
    }
  }

  def findCategory(categoryType: CategoryType): Future[Option[Category]] = {
    Future {
      searchEngine.getById(categoryType)
    }
  }

  def findAllCategories(sortedInLanguage: LanguageCode = LanguageCode.English): Future[Seq[Category]] = {
    Future {
      val languageSpecificCategoryNameField = categoryNameWithLanguageCodeField(sortedInLanguage)
      val query = Query(
        mainQuery = AllDocumentsQuery,
        rows = Integer.MAX_VALUE
      ).withAscendingSort(languageSpecificCategoryNameField)
      searchEngine.query(query).documents
    }
  }

  def add(category: Category): Future[Category] = {
    Future {
      searchEngine.add(category)
      category
    }
  }

}
