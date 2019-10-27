package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.{LanguageCode, Name}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}

@RunWith(classOf[JUnitRunner])
class CategoryJsonConverterTest extends FunSuite with Matchers {
  val underTest = new CategoryJsonConverter

  val category = Category(
    categoryType = CategoryType.Clothes,
    names = Seq(
      Name(
        unparsedName = "Clothes",
        languageCode = Some(LanguageCode.English
        )
      )
    ),
    filters = Seq(
      CategoryFilter(
        filterType = CategoryFilterType.HasCertification,
        names = Seq(
          Name(
            unparsedName = "Has certifications",
            languageCode = Some(LanguageCode.English
            )
          )
        )
      )
    )
  )
  val json = """{"categoryType":"Clothes","names":[{"unparsedName":"Clothes","languageCode":"en"}],"filters":[{"filterType":"HasCertification","names":[{"unparsedName":"Has certifications","languageCode":"en"}]}]}"""

  test("to json") {
    underTest.toJson(category) shouldBe json
  }

  test("from json") {
    underTest.fromJson(json) shouldBe category
  }
}
