package com.sustainasearch.services.catalog.products.facets

import com.sustainasearch.services.Name
import com.sustainasearch.services.catalog.CategoryType.CategoryType

case class CategoryFacet(categoryType: CategoryType, count: Long, names: Seq[Name] = Seq.empty)
