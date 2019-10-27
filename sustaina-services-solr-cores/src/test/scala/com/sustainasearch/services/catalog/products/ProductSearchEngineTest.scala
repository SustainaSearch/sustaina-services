package com.sustainasearch.services.catalog.products

import java.util.UUID

import com.sustainasearch.searchengine.{AllDocumentsQuery, FreeTextQuery, Query, TextFilterQuery}
import com.sustainasearch.services.{Image, ImageType, LanguageCode, Name}
import com.sustainasearch.services.catalog._
import com.sustainasearch.services.catalog.products.clothes.{Clothes, Composition}
import com.sustainasearch.services.catalog.products.food.{BabyFood, IngredientStatement}
import org.scalatest.{Matchers, WordSpec}

class ProductSearchEngineTest extends WordSpec with Matchers {

  "Products Search Engine" should {
    val searchEngineFactory = new EmbeddedSolrProductSearchEngineFactory
    val underTest = searchEngineFactory.createSearchEngine(ProductSolrFieldRegister)

    val babyFoodProduct = Product(
      id = UUID.fromString("00000000-0000-0000-0000-000000000001"),
      productActivity = ProductActivity(
        country = Country(
          countryCode = CountryCode.Sweden,
          names = Seq(
            Name("Sverige", Some(LanguageCode.Swedish))
          )
        ),
        city = None,
        representativePoint = RepresentativePoint(
          latitude = 52,
          longitude = 13
        )
      ),
      functionalNames = Seq(
        Name(
          unparsedName = "God Natt! Mildgröt med Grönsaker 6m",
          languageCode = Some(LanguageCode.Swedish)
        )
      ),
      brandName = Name(
        unparsedName = "Hipp",
        languageCode = None
      ),
      category = Category(
        CategoryType.BabyFood,
        names = Seq(Name(
          unparsedName = "Barnmat",
          languageCode = Some(LanguageCode.Swedish)
        )),
        filters = Seq.empty
      ),
      images = Seq(
        Image(
          ImageType.Small,
          url = "small.image.1"
        ),
        Image(
          ImageType.Large,
          url = "large.image.1"
        )
      ),
      sustainaIndex = 78.567d,
      maybeBabyFood = Some(
        BabyFood(
          ingredientStatements = Seq(
            IngredientStatement(
              unparsedStatement = "Ingredienser: Mjölkblandning (MJÖLK* 40 %, vatten, rapsolja*), grönsaker* 30 % (morot* 25 %, majs* 5 %), ris* 8 % (rismannagryn*, fullkornsrisflingor* 1 %), kalciumkarbonat, vitaminer (A, B1 , D). *Ekologisk (DE-ÖKO-001).",
              languageCode = LanguageCode.Swedish
            )
          )
        )
      ),
      maybeClothes = None
    )
    underTest.add(babyFoodProduct)

    val clothesProduct = Product(
      id = UUID.fromString("00000000-0000-0000-0000-000000000002"),
      productActivity = ProductActivity(
        country = Country(
          countryCode = CountryCode.Sweden,
          names = Seq(
            Name("Sverige", Some(LanguageCode.Swedish))
          )
        ),
        city = None,
        representativePoint = RepresentativePoint(
          latitude = 52,
          longitude = 13
        )
      ),
      functionalNames = Seq(
        Name(
          unparsedName = "Skinny Fit Chinos",
          languageCode = Some(LanguageCode.Swedish)
        )
      ),
      brandName = Name(
        unparsedName = "H&M",
        languageCode = None
      ),
      category = Category(
        CategoryType.Clothes,
        names = Seq(Name(
          unparsedName = "Kläder",
          languageCode = Some(LanguageCode.Swedish)
        )),
        filters = Seq.empty
      ),
      images = Seq(
        Image(
          ImageType.Small,
          url = "small.image.2"
        ),
        Image(
          ImageType.Large,
          url = "large.image.2"
        )
      ),
      sustainaIndex = 48.517d,
      maybeBabyFood = None,
      maybeClothes = Some(
        Clothes(
          compositions = Seq(
            Composition(
              unparsedComposition = "Bomull 98%, Elastan 2%",
              languageCode = LanguageCode.Swedish
            )
          )
        )
      )
    )
    underTest.add(clothesProduct)

    "find document by functional name with wildcard query" in {
      val response = underTest.query(Query(mainQuery = FreeTextQuery("natt"), fuzzy = true))
      response.numFound shouldBe 1
      response.documents.head shouldBe babyFoodProduct
    }

    "find document by brand name with wildcard query" in {
      val response = underTest.query(Query(mainQuery = FreeTextQuery("hipp"), fuzzy = true))
      response.numFound shouldBe 1
      response.documents.head shouldBe babyFoodProduct
    }

    "find document by category type with wildcard query" in {
      val response = underTest.query(Query(mainQuery = FreeTextQuery(CategoryType.BabyFood.toString), fuzzy = true))
      response.numFound shouldBe 1
      response.documents.head shouldBe babyFoodProduct
    }

    "find document by category name with wildcard query" in {
      val response = underTest.query(Query(mainQuery = FreeTextQuery("Barnma"), fuzzy = true))
      response.numFound shouldBe 1
      response.documents.head shouldBe babyFoodProduct
    }

    "find document by baby food ingredient statement with wildcard query" in {
      val response = underTest.query(Query(mainQuery = FreeTextQuery("mildgrö"), fuzzy = true))
      response.numFound shouldBe 1
      response.documents.head shouldBe babyFoodProduct
    }

    "find document by clothes composition with wildcard query" in {
      val response = underTest.query(Query(mainQuery = FreeTextQuery("bomull"), fuzzy = true))
      response.numFound shouldBe 1
      response.documents.head shouldBe clothesProduct
    }

    "find document within specified SustainaIndex range" in {
      val response1 = underTest.query(Query(mainQuery = AllDocumentsQuery).withFilterQuery(TextFilterQuery("sustainaIndex", "[70 TO *]")))
      response1.numFound shouldBe 1
      response1.documents.head shouldBe babyFoodProduct

      val response2 = underTest.query(Query(mainQuery = AllDocumentsQuery).withFilterQuery(TextFilterQuery("sustainaIndex", "[40 TO 70]")))
      response2.numFound shouldBe 1
      response2.documents.head shouldBe clothesProduct

      val response3 = underTest.query(Query(mainQuery = AllDocumentsQuery).withFilterQuery(TextFilterQuery("sustainaIndex", "[40 TO *]")))
      response3.numFound shouldBe 2
      response3.documents should contain only(babyFoodProduct, clothesProduct)
    }

    // TODO: add test for nearest point boost function
  }

}
