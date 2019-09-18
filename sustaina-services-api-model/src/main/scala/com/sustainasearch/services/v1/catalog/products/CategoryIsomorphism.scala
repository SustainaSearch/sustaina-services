package com.sustainasearch.services.v1.catalog.products

import com.sustainasearch.services.catalog.products.{Category, CategoryType}
import com.sustainasearch.services.v1.NameIsomorphism
import scalaz.Isomorphism.<=>

object CategoryIsomorphism {

  val category = new (Category <=> CategoryApiModel) {
    val to: Category => CategoryApiModel = { category =>
      CategoryApiModel(
        categoryType = category.categoryType.toString,
        names = category.names.map(NameIsomorphism.name.to)
      )
    }
    val from: CategoryApiModel => Category = { category =>
      Category(
        categoryType = CategoryType.withName(category.categoryType),
        names = category.names.map(NameIsomorphism.name.from)
      )
    }
  }
}
