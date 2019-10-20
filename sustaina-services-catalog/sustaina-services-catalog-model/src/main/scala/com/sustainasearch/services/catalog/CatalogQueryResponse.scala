package com.sustainasearch.services.catalog

import com.sustainasearch.searchengine.QueryResponse
import com.sustainasearch.services.catalog.products.SimpleProduct
import com.sustainasearch.services.catalog.products.facets.ProductFacets

case class CatalogQueryResponse(products: QueryResponse[SimpleProduct, ProductFacets])
