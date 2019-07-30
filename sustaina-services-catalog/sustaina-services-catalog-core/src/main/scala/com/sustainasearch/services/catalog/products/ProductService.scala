package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine.{Query, QueryResponse}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductService @Inject()(searchEngineFactory: ProductSearchEngineFactory)(implicit ec: ExecutionContext) {
  private val searchEngine = searchEngineFactory.createSearchEngine

  def query(query: Query): Future[QueryResponse[Product]] = {
    Future(searchEngine.query(query))
  }

  def add(product: Product): Future[Product] = {
    Future {
      searchEngine.add(product)
      product
    }
  }
}
