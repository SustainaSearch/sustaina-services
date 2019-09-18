package com.sustainasearch.services.v1.catalog.models

import com.sustainasearch.services.catalog.CatalogQueryResponse
import com.sustainasearch.services.v1.catalog.products.models.ProductsIsomorphism
import scalaz.Isomorphism.<=>

object CatalogIsomorphism {

  val queryResponse = new (CatalogQueryResponse <=> CatalogQueryResponseApiModel) {
    val to: CatalogQueryResponse => CatalogQueryResponseApiModel = { response =>
      CatalogQueryResponseApiModel(
        products = ProductsIsomorphism.simpleProductQueryResponse.to(response.products)
      )
    }
    val from: CatalogQueryResponseApiModel => CatalogQueryResponse = { response =>
      CatalogQueryResponse(
        products = ProductsIsomorphism.simpleProductQueryResponse.from(response.products)
      )
    }
  }

}
