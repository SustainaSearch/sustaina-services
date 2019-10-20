package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.Name
import com.sustainasearch.services.catalog.products.CategoryFilterType.CategoryFilterType

case class CategoryFilter(filterType: CategoryFilterType, names: Seq[Name])
