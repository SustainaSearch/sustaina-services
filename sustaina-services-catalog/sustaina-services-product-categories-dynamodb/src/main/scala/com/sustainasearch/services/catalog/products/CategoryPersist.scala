package com.sustainasearch.services.catalog.products

import play.api.libs.json.Json

case class CategoryPersist(categoryType: String,
                           names: Seq[NamePersist],
                           filters: Seq[CategoryFilterPersist])

object CategoryPersist {
  implicit val format = Json.format[CategoryPersist]
}

case class NamePersist(unparsedName: String, languageCode: Option[String])

object NamePersist {
  implicit val format = Json.format[NamePersist]
}

case class CategoryFilterPersist(filterType: String, names: Seq[NamePersist])

object CategoryFilterPersist {
  implicit val format = Json.format[CategoryFilterPersist]
}
