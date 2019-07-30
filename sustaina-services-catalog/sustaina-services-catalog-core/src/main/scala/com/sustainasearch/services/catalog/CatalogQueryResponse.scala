package com.sustainasearch.services.catalog

import com.sustainasearch.searchengine.QueryResponse
import com.sustainasearch.services.catalog.products.Product

case class CatalogQueryResponse(products: QueryResponse[Product]) {

}
