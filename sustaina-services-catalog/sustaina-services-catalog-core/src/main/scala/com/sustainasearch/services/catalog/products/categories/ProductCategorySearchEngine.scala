package com.sustainasearch.services.catalog.products.categories

import com.sustainasearch.searchengine.SearchEngine
import com.sustainasearch.services.catalog.Category
import com.sustainasearch.services.catalog.CategoryType.CategoryType

trait ProductCategorySearchEngine extends SearchEngine[CategoryType, Category, Option[Nothing]]
