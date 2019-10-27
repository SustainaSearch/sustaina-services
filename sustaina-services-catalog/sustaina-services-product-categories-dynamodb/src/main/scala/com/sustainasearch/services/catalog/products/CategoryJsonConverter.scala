package com.sustainasearch.services.catalog.products

import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, JsSuccess, Json}

@Singleton
class CategoryJsonConverter @Inject()() {

  def toJson: Category => String = category => {
    val categoryPersist = CategoryIsomorphism.category.to(category)
    Json.toJson(categoryPersist).toString()
  }

  def fromJson: String => Category = category => {
    val categoryPersist = Json.parse(category).validate[CategoryPersist] match {
      case s: JsSuccess[CategoryPersist] => s.get
      case e: JsError => throw new IllegalArgumentException(s"'$category' is not a CategoryPersist: ${e.errors}")
    }
    CategoryIsomorphism.category.from(categoryPersist)
  }

}
