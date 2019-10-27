package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.catalog.products.CategoryType.CategoryType

import scala.concurrent.Future

trait ProductCategoryRepository {
  
  def getAllCategories: Future[Seq[Category]]

  def getCategory(categoryType: CategoryType): Future[Option[Category]]

  def add(category: Category): Future[Category]

}
