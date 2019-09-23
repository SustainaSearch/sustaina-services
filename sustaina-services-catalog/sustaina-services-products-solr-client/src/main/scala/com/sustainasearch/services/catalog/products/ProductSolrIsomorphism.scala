package com.sustainasearch.services.catalog.products

import java.util
import java.util.UUID

import com.sustainasearch.searchengine.QueryResponse
import com.sustainasearch.searchengine.solr.SolrIsomorphism
import com.sustainasearch.services.{LanguageCode, Name}
import com.sustainasearch.services.catalog._
import com.sustainasearch.services.catalog.products.clothes.{Clothes, Composition}
import com.sustainasearch.services.catalog.products.facets.{BrandFacet, CategoryFacet, ProductFacets}
import com.sustainasearch.services.catalog.products.food.{BabyFood, IngredientStatement}
import org.apache.solr.client.solrj.response.{FacetField, QueryResponse => SolrQueryResponse}
import org.apache.solr.common.{SolrDocument, SolrInputDocument}

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

class ProductSolrIsomorphism(fieldRegister: ProductSearchEngineFieldRegister) extends SolrIsomorphism[Product, ProductFacets] {

  import fieldRegister._

  override def toSolrInputDocument(product: Product): SolrInputDocument = {
    val document = new SolrInputDocument()
    document.addField(IdField, product.id.toString)

   product.images.foreach { image =>
     document.addField(ImageField, ImageIsomorphism.image.to(image))
   }

    val countryCode = product.productActivity.country.countryCode.toString
    document.addField(CountryCodeField, countryCode)
    product.productActivity.country.names.foreach { name =>
      document.addField(countryNameWithNameLanguageCodeField(name), name.unparsedName)
    }
    product.productActivity.city.foreach { city =>
      city.names.foreach { name =>
        document.addField(cityNameWithNameLanguageCodeField(name), name.unparsedName)
      }
    }
    document.addField(RepresentativePointField, s"${product.productActivity.representativePoint.latitude},${product.productActivity.representativePoint.longitude}")

    product.functionalNames.foreach { name =>
      document.addField(functionalNameWithNameLanguageCodeField(name), name.unparsedName)
    }

    document.addField(brandNameWithNameLanguageCodeField(product.brandName), product.brandName.unparsedName)
    document.addField(BrandNameExactField, product.brandName.unparsedName)

    document.addField(CategoryTypeField, product.category.categoryType.toString)
    product.category.names.foreach { name =>
      document.addField(categoryNameWithNameLanguageCodeField(name), name.unparsedName)
    }

    document.addField(SustainaIndexField, product.sustainaIndex)

    product.maybeBabyFood.foreach { babyFood =>
      babyFood.ingredientStatements.foreach { statement =>
        document.addField(babyFoodIngredientStatemenWithLanguageCodeField(statement.languageCode), statement.unparsedStatement)
      }
    }

    product.maybeClothes.foreach { clothes =>
      clothes.compositions.foreach { composition =>
        document.addField(clothesCompositionWithLanguageCodeField(composition.languageCode), composition.unparsedComposition)
      }
    }

    document
  }

  override def fromSolrDocument(document: SolrDocument): Product = {
    val representativePoint = {
      val repPointValue = document.getFirstValue(RepresentativePointField).asInstanceOf[String]
      val latLon = repPointValue.split(",").map(_.toDouble)
      val (lat, lon) = (latLon.head, latLon.last)
      RepresentativePoint(lat, lon)
    }

    val images = document.getFieldValues(ImageField).asScala.map { value =>
      ImageIsomorphism.image.from(value.asInstanceOf[String])
    }.toSeq

    val functionalNames = ListBuffer.empty[Name]

    if (document.containsKey(FunctionalNameField))
      functionalNames += Name(
        unparsedName = document.getFirstValue(FunctionalNameField).asInstanceOf[String],
        languageCode = None
      )

    val categoryNames = ListBuffer.empty[Name]
    val babyFoodIngredientStatements = ListBuffer.empty[IngredientStatement]
    val clothesCompositions = ListBuffer.empty[Composition]
    val countryNames = ListBuffer.empty[Name]
    val cityNames = ListBuffer.empty[Name]

    LanguageCode.values.foreach { languageCode =>
      val countryNameField = countryNameWithLanguageCodeField(languageCode)
      if (document.containsKey(countryNameField))
        countryNames += Name(
          unparsedName = document.getFirstValue(countryNameField).asInstanceOf[String],
          languageCode = Some(languageCode)
        )

      val cityNameField = cityNameWithLanguageCodeField(languageCode)
      if (document.containsKey(cityNameField))
        cityNames += Name(
          unparsedName = document.getFirstValue(cityNameField).asInstanceOf[String],
          languageCode = Some(languageCode)
        )

      val functionalNameField = functionalNameWithLanguageCodeField(languageCode)
      if (document.containsKey(functionalNameField))
        functionalNames += Name(
          unparsedName = document.getFirstValue(functionalNameField).asInstanceOf[String],
          languageCode = Some(languageCode)
        )

      val categoryNameField = categoryNameWithLanguageCodeField(languageCode)
      if (document.containsKey(categoryNameField))
        categoryNames += Name(
          unparsedName = document.getFirstValue(categoryNameField).asInstanceOf[String],
          languageCode = Some(languageCode)
        )

      val babyFoodIngredientStatementField = babyFoodIngredientStatemenWithLanguageCodeField(languageCode)
      if (document.containsKey(babyFoodIngredientStatementField))
        babyFoodIngredientStatements += IngredientStatement(
          languageCode = languageCode,
          unparsedStatement = document.getFirstValue(babyFoodIngredientStatementField).asInstanceOf[String]
        )

      val clothesCompositionField = clothesCompositionWithLanguageCodeField(languageCode)
      if (document.containsKey(clothesCompositionField))
        clothesCompositions += Composition(
          languageCode = languageCode,
          unparsedComposition = document.getFirstValue(clothesCompositionField).asInstanceOf[String]
        )
    }

    val maybeBabyFood = if (babyFoodIngredientStatements.isEmpty) None else Some(BabyFood(babyFoodIngredientStatements))

    val maybeClothes = if (clothesCompositions.isEmpty) None else Some(Clothes(clothesCompositions))

    val city = if (cityNames.isEmpty) None else Some(City(cityNames))

    Product(
      id = UUID.fromString(document.getFirstValue(IdField).asInstanceOf[String]),
      productActivity = ProductActivity(
        Country(
          countryCode = CountryCode.withName(
            document.getFirstValue(CountryCodeField).asInstanceOf[String]
          ),
          names = countryNames
        ),
        city,
        representativePoint
      ),
      functionalNames,
      brandName = Name(
        unparsedName = document.getFirstValue(BrandNameField).asInstanceOf[String],
        languageCode = None
      ),
      category = Category(
        categoryType = CategoryType.withName(
          document.getFirstValue(CategoryTypeField).asInstanceOf[String]
        ),
        categoryNames
      ),
      images = images,
      sustainaIndex = document.getFirstValue(SustainaIndexField).asInstanceOf[Double],
      maybeBabyFood,
      maybeClothes
    )
  }

  override def fromSolrQueryResponse(response: SolrQueryResponse): QueryResponse[Product, ProductFacets] = {
    val results = response.getResults
    val documents = results
      .asScala
      .map(fromSolrDocument)
      .toList

    val facetFields: util.List[FacetField] = {
      if (response.getFacetFields == null)
        new util.ArrayList[FacetField]
      else
        response.getFacetFields
    }

    val productFacets = facetFields
      .asScala
      .foldLeft(ProductFacets()) { (facets, facetField) =>
        facetField.getName match {
          case BrandNameExactField =>
            facetField.getValues.asScala.foldLeft(facets) { (prev, facetCount) =>
              if (facetCount.getCount > 0) {
                val brandFacet = BrandFacet(brandName = Name(facetCount.getName, languageCode = None), facetCount.getCount)
                prev.withBrandFacet(brandFacet)
              } else
                prev
            }
          case CategoryTypeField =>
            facetField.getValues.asScala.foldLeft(facets) { (prev, facetCount) =>
              if (facetCount.getCount > 0) {
                val categoryFacet = CategoryFacet(CategoryType.withName(facetCount.getName), facetCount.getCount)
                prev.withCategoryFacet(categoryFacet)
              } else
                prev
            }
          case _ =>
            // TODO: log?
            facets
        }
      }

    QueryResponse(
      start = results.getStart,
      numFound = results.getNumFound,
      documents,
      facets = productFacets
    )
  }

}
