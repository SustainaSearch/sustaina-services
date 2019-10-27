package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.{LanguageCode, Name}
import scalaz.Isomorphism.<=>

object CategoryIsomorphism {

  val category = new (Category <=> CategoryPersist) {
    override def to: Category => CategoryPersist = { category =>
      CategoryPersist(
        categoryType = category.categoryType.toString,
        names = category.names.map(name.to),
        filters = category.filters.map(categoryFilter.to)
      )
    }
    override def from: CategoryPersist => Category = { category =>
      Category(
        categoryType = CategoryType.withName(category.categoryType),
        names = category.names.map(name.from),
        filters = category.filters.map(categoryFilter.from)
      )
    }
  }

  val name = new (Name <=> NamePersist) {
    override def to: Name => NamePersist = { name =>
      NamePersist(
        unparsedName = name.unparsedName,
        languageCode = name.languageCode.map(_.toString)
      )
    }
    override def from: NamePersist => Name = { name =>
      Name(
        unparsedName = name.unparsedName,
        languageCode = name.languageCode.map(LanguageCode.withName)
      )
    }
  }

  val categoryFilter = new (CategoryFilter <=> CategoryFilterPersist) {
    override def to: CategoryFilter => CategoryFilterPersist = { categoryFilter =>
      CategoryFilterPersist(
        filterType = categoryFilter.filterType.toString,
        names = categoryFilter.names.map(name.to)
      )
    }
    override def from: CategoryFilterPersist => CategoryFilter = { categoryFilter =>
      CategoryFilter(
        filterType = CategoryFilterType.withName(categoryFilter.filterType),
        names = categoryFilter.names.map(name.from)
      )
    }
  }
}
