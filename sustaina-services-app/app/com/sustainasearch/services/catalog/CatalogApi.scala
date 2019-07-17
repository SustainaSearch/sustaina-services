package com.sustainasearch.services.catalog

import com.sustainasearch.services.catalog.products.ProductsApi
import scalaz.Isomorphism.<=>

object CatalogApi {


  val queryResponse = new (CatalogQueryResponse <=> CatalogQueryResponseApiModel) {
    val to: CatalogQueryResponse => CatalogQueryResponseApiModel = { response =>
      CatalogQueryResponseApiModel(
        products = ProductsApi.queryResponse.to(response.products)
      )
    }
    val from: CatalogQueryResponseApiModel => CatalogQueryResponse = { response =>
      CatalogQueryResponse(
        products = ProductsApi.queryResponse.from(response.products)
      )
    }
  }


}
