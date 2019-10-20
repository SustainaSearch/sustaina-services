package com.sustainasearch.services.catalog.products

import java.util.concurrent.ConcurrentHashMap

import com.sustainasearch.services.catalog.products.CategoryType.CategoryType
import javax.inject.Inject

import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, Future}

class InMemoryProductCategoryRepository @Inject()(implicit ec: ExecutionContext) extends ProductCategoryRepository {
  private val store = new ConcurrentHashMap[CategoryType, Category]().asScala

  override def findCategory(categoryType: CategoryType): Future[Option[Category]] = {
    Future {
      store.get(categoryType)
    }
  }

  override def add(category: Category): Future[Category] = {
    Future {
      store.put(category.categoryType, category)
      category
    }
  }

}
