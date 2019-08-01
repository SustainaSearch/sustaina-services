package com.sustainasearch.services.catalog.products

import java.util.UUID

import com.sustainasearch.searchengine.QueryResponse
import com.sustainasearch.searchengine.solr.SolrMorphism
import com.sustainasearch.services.catalog.products.clothes.{Clothes, Composition}
import com.sustainasearch.services.catalog.products.food.{BabyFood, IngredientStatement}
import org.apache.solr.client.solrj.response.{QueryResponse => SolrQueryResponse}
import org.apache.solr.common.SolrInputDocument

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

class ProductSolrMorphism(fieldRegister: ProductSearchEngineFieldRegister) extends SolrMorphism[Product] {

  import fieldRegister._

  override def toSolrInputDocument(product: Product): SolrInputDocument = {
    val document = new SolrInputDocument()
    document.addField(IdField, product.id.toString)

    document.addField(RepresentativePointField, s"${product.representativePoint.latitude},${product.representativePoint.longitude}")

    product.functionalNames.foreach { name =>
      document.addField(functionalNameWithNameLanguageCodeField(name), name.unparsedName)
    }

    document.addField(brandNameWithNameLanguageCodeField(product.brandName), product.brandName.unparsedName)

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

  override def fromSolrQueryResponse(response: SolrQueryResponse): QueryResponse[Product] = {
    val results = response.getResults
    val documents = results
      .asScala
      .map { document =>
        val representativePoint = {
          val repPointValue = document.getFirstValue(RepresentativePointField).asInstanceOf[String]
          val latLon = repPointValue.split(",").map(_.toDouble)
          val (lat, lon) = (latLon.head, latLon.last)
          RepresentativePoint(lat, lon)
        }

        val functionalNames = ListBuffer.empty[Name]

        if (document.containsKey(FunctionalNameField))
          functionalNames += Name(
            unparsedName = document.getFirstValue(FunctionalNameField).asInstanceOf[String],
            languageCode = None
          )

        val categoryNames = ListBuffer.empty[Name]
        val babyFoodIngredientStatements = ListBuffer.empty[IngredientStatement]
        val clothesCompositions = ListBuffer.empty[Composition]

        LanguageCode.values.foreach { languageCode =>
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

        Product(
          id = UUID.fromString(document.getFirstValue(IdField).asInstanceOf[String]),
          representativePoint,
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
          sustainaIndex = document.getFirstValue(SustainaIndexField).asInstanceOf[Double],
          maybeBabyFood,
          maybeClothes
        )
      }
      .toList
    QueryResponse(
      results.getNumFound,
      documents
    )
  }

}
