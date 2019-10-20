package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.Name
import com.sustainasearch.services.catalog.products.CategoryType.CategoryType
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductCategoryService @Inject()(repository: ProductCategoryRepository)(implicit ec: ExecutionContext) {
  // TODO: add cache

  def findCategory(categoryType: CategoryType): Future[Option[Category]] = repository.findCategory(categoryType)

  def categoryNames(categoryTypes: Seq[CategoryType]): Future[Map[CategoryType, Seq[Name]]] = {
    for {
      categories <- Future.sequence(categoryTypes.map(repository.findCategory))
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

  def add(category: Category): Future[Category] = {
    repository.add(category)
  }

}
