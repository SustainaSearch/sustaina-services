package com.sustainasearch.services.catalog.products.facets

import com.sustainasearch.services.catalog.products.CategoryType.CategoryType
import com.sustainasearch.services.catalog.products.Name

case class CategoryFacet(categoryType: CategoryType, count: Long, names: Seq[Name] = Seq.empty)
