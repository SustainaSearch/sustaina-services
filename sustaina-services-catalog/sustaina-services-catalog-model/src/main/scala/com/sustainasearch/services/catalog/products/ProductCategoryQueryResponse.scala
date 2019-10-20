package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine.QueryResponse
import com.sustainasearch.services.catalog.products.facets.ProductFacets

case class ProductCategoryQueryResponse(productQueryResponse: QueryResponse[Product, ProductFacets],
                                        filterQueries: Seq[FilterQueryContainer])
