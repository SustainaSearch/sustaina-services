package com.sustainasearch.services.catalog

import com.sustainasearch.services.Name
import com.sustainasearch.services.catalog.CategoryType.CategoryType

case class Category(categoryType: CategoryType, names: Seq[Name])
