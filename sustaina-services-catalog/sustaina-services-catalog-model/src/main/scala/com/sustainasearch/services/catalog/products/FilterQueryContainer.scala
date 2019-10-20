package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine.FilterQuery
import com.sustainasearch.services.Name

case class FilterQueryContainer(names: Seq[Name], filterQuery: FilterQuery)
