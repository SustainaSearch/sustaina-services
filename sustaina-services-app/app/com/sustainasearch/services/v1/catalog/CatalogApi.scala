package com.sustainasearch.services.v1.catalog

import com.sustainasearch.services.catalog.CatalogQueryResponse
import com.sustainasearch.services.v1.catalog.products.ProductsApi
import scalaz.Isomorphism.<=>

object CatalogApi {


  val queryResponse = new (CatalogQueryResponse <=> CatalogQueryResponseApiModel) {
    val to: CatalogQueryResponse => CatalogQueryResponseApiModel = { response =>
      CatalogQueryResponseApiModel(
        products = ProductsApi.simpleProductQueryResponse.to(response.products)
      )
    }
    val from: CatalogQueryResponseApiModel => CatalogQueryResponse = { response =>
      CatalogQueryResponse(
        products = ProductsApi.simpleProductQueryResponse.from(response.products)
      )
    }
  }


}
