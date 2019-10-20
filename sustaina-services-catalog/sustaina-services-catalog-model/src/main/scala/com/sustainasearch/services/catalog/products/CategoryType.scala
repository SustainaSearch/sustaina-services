package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.catalog.products.CategoryFilterType.CategoryFilterType

import scala.language.implicitConversions

object CategoryType extends Enumeration {

  protected case class CategoryTypeVal(name: String, filterTypes: Seq[CategoryFilterType]) extends Val(name)

  implicit def valueToCategoryTypeVal(value: Value): CategoryTypeVal = values.asInstanceOf[CategoryTypeVal]

  type CategoryType = Value

  val BabyFood = CategoryTypeVal(
    name = "BabyFood",
    filterTypes = Seq(
      CategoryFilterType.HasCertification,
//      CategoryFilterType.IsEcological,
//      CategoryFilterType.IsVegan,
//      CategoryFilterType.IsVegetarian,
      CategoryFilterType.ZeroToThreeMonths
    )
  )

  val Clothes = CategoryTypeVal(
    name = "Clothes",
    filterTypes = Seq(
      CategoryFilterType.HasCertification
//      CategoryFilterType.HasCertification,
//      CategoryFilterType.IsEcological
    )
  )

}
