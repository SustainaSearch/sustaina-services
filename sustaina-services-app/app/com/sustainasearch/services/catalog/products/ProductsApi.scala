package com.sustainasearch.services.catalog.products

import java.util.UUID

import com.sustainasearch.searchengine.QueryResponse
import scalaz.Isomorphism.<=>
import scalaz.

object ProductsApi {

  val queryResponse = new (QueryResponse[ProductContainer] <=> ProductQueryResponseApiModel) {
    val to: QueryResponse[ProductContainer] => ProductQueryResponseApiModel = { response =>
      ProductQueryResponseApiModel(
        numFound = response.numFound,
        documents = response
          .documents
          .map(productContainer.to)
      )
    }
    val from: ProductQueryResponseApiModel => QueryResponse[ProductContainer] = { response =>
      QueryResponse[ProductContainer](
        numFound = response.numFound,
        documents = response
          .documents
          .map(productContainer.from)
          .toList
      )
    }
  }

  val productContainer = new (ProductContainer <=> ProductContainerApiModel) {
    val to: ProductContainer => ProductContainerApiModel = { productContainer =>
      ProductContainerApiModel(
        id = productContainer.id.toString,
        product = product.to(productContainer.product)
      )
    }
    val from: ProductContainerApiModel => ProductContainer = { productContainer =>
      ProductContainer(
        id = UUID.fromString(productContainer.id),
        product = product.from(productContainer.product)
      )
    }
  }

  val product = new (Product <=> ProductApiModel) {
    val to: Product => ProductApiModel = { product =>
      ProductApiModel(
        name = product.name
      )
    }
    val from: ProductApiModel => Product = { product =>
      Product(
        name = product.name
      )
    }
  }

}
