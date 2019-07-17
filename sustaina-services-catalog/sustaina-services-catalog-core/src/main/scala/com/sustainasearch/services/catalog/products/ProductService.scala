package com.sustainasearch.services.catalog.products

import java.util.UUID

import com.sustainasearch.searchengine.{Query, QueryResponse}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductService @Inject()(searchEngineFactory: ProductSearchEngineFactory)(implicit ec: ExecutionContext) {
  private val searchEngine = searchEngineFactory.createSearchEngine

  def query(query: Query): Future[QueryResponse[ProductContainer]] = {
    Future(searchEngine.query(query))
  }

  def add(product: Product): Future[ProductContainer] = {
    Future {
      val productContainer = ProductContainer(
        id = UUID.randomUUID(),
        product = product
      )
      searchEngine.add(productContainer)
      productContainer
    }
  }
}
