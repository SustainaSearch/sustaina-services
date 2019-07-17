package com.sustainasearch.services.catalog

import com.sustainasearch.searchengine.QueryResponse
import com.sustainasearch.services.catalog.products.ProductContainer

case class CatalogQueryResponse(products: QueryResponse[ProductContainer]) {

}
