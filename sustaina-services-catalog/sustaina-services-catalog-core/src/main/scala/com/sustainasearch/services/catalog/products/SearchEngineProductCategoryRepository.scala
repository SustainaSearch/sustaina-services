package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine.{AllDocumentsQuery, Query}
import com.sustainasearch.services.LanguageCode
import com.sustainasearch.services.LanguageCode.LanguageCode
import com.sustainasearch.services.catalog.products.CategoryType.CategoryType
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SearchEngineProductCategoryRepository @Inject()(searchEngineFactory: ProductCategorySearchEngineFactory, searchEngineFieldRegister: ProductCategorySearchEngineFieldRegister)(implicit ec: ExecutionContext) extends ProductCategoryRepository {

  import searchEngineFieldRegister._

  private val searchEngine = searchEngineFactory.createSearchEngine(searchEngineFieldRegister)

  override def findCategory(categoryType: CategoryType): Future[Option[Category]] = {
    Future {
      searchEngine.getById(categoryType)
    }
  }

//  override def findAllCategories(sortedInLanguage: LanguageCode = LanguageCode.English): Future[Seq[Category]] = {
//    Future {
//      val languageSpecificCategoryNameField = categoryNameWithLanguageCodeField(sortedInLanguage)
//      val query = Query(
//        mainQuery = AllDocumentsQuery,
//        rows = Integer.MAX_VALUE
//      ).withAscendingSort(languageSpecificCategoryNameField)
//      searchEngine.query(query).documents
//    }
//  }

  override def add(category: Category): Future[Category] = {
    Future {
      searchEngine.add(category)
      category
    }
  }

}