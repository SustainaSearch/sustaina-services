package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.Name
import com.sustainasearch.services.catalog.products.CategoryType.CategoryType

case class Category(categoryType: CategoryType, names: Seq[Name])
