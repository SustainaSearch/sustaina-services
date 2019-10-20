package com.sustainasearch.services.v1.catalog.products

import com.sustainasearch.services.catalog.products.{Category, CategoryFilter, CategoryFilterType, CategoryType}
import com.sustainasearch.services.v1.NameIsomorphism
import scalaz.Isomorphism.<=>

object CategoryIsomorphism {

  val category = new (Category <=> CategoryApiModel) {
    val to: Category => CategoryApiModel = { category =>
      CategoryApiModel(
        categoryType = category.categoryType.toString,
        names = category.names.map(NameIsomorphism.name.to),
        filters = category.filters.map(categoryFilter.to)
      )
    }
    val from: CategoryApiModel => Category = { category =>
      Category(
        categoryType = CategoryType.withName(category.categoryType),
        names = category.names.map(NameIsomorphism.name.from),
        filters = category.filters.map(categoryFilter.from)
      )
    }
  }

  val categoryFilter = new (CategoryFilter <=> CategoryFilterApiModel) {
    override def to: CategoryFilter => CategoryFilterApiModel = { filter =>
      CategoryFilterApiModel(
        filterType = filter.filterType.toString,
        names = filter.names.map(NameIsomorphism.name.to)
      )
    }
    override def from: CategoryFilterApiModel => CategoryFilter = { filter =>
      CategoryFilter(
        filterType = CategoryFilterType.withName(filter.filterType),
        names = filter.names.map(NameIsomorphism.name.from)
      )
    }
  }
}
