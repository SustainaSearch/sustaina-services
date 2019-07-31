package com.sustainasearch.services.catalog.products

import java.util.UUID

import com.sustainasearch.searchengine.QueryResponse
import com.sustainasearch.searchengine.solr.SolrMorphism
import com.sustainasearch.services.catalog.products.LanguageCode.LanguageCode
import com.sustainasearch.services.catalog.products.clothes.{Clothes, Composition}
import com.sustainasearch.services.catalog.products.food.{BabyFood, IngredientStatement}
import org.apache.solr.client.solrj.response.{QueryResponse => SolrQueryResponse}
import org.apache.solr.common.SolrInputDocument

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

object ProductSolrMorphism extends SolrMorphism[Product] {

  override def toSolrInputDocument(product: Product): SolrInputDocument = {
    def languageCodeSuffix(languageCode: LanguageCode): String = s"_${languageCode.toString}"

    def nameLanguageCodeSuffix(name: Name): String =
      name.languageCode.fold("")(languageCode => languageCodeSuffix(languageCode))

    val solrInputDocument = new SolrInputDocument()
    solrInputDocument.addField("id", product.id.toString)

    product.functionalNames.foreach { name =>
      solrInputDocument.addField(s"functionalName${nameLanguageCodeSuffix(name)}", name.unparsedName)
    }

    solrInputDocument.addField(s"brandName${nameLanguageCodeSuffix(product.brandName)}", product.brandName.unparsedName)

    solrInputDocument.addField("categoryType", product.category.categoryType.toString)
    product.category.names.foreach { name =>
      solrInputDocument.addField(s"categoryName${nameLanguageCodeSuffix(name)}", name.unparsedName)
    }

    solrInputDocument.addField("sustainaIndex", product.sustainaIndex)

    product.maybeBabyFood.foreach { babyFood =>
      babyFood.ingredientStatements.foreach { statement =>
        solrInputDocument.addField(s"babyFoodIngredientStatement${languageCodeSuffix(statement.languageCode)}", statement.unparsedStatement)
      }
    }

    product.maybeClothes.foreach { clothes =>
      clothes.compositions.foreach { composition =>
        solrInputDocument.addField(s"clothesComposition${languageCodeSuffix(composition.languageCode)}", composition.unparsedComposition)
      }
    }

    solrInputDocument
  }

  override def fromSolrQueryResponse(response: SolrQueryResponse): QueryResponse[Product] = {
    val results = response.getResults
    val documents = results
      .asScala
      .map { document =>
        val functionalNames = ListBuffer.empty[Name]

        if (document.containsKey("functionalName"))
          functionalNames += Name(
            unparsedName = document.getFirstValue("functionalName").asInstanceOf[String],
            languageCode = None
          )

        val categoryNames = ListBuffer.empty[Name]
        val babyFoodIngredientStatements = ListBuffer.empty[IngredientStatement]
        val clothesCompositions = ListBuffer.empty[Composition]

        LanguageCode.values.foreach { languageCode =>
          val functionalNameField = s"functionalName_${languageCode.toString}"
          if (document.containsKey(functionalNameField))
            functionalNames += Name(
              unparsedName = document.getFirstValue(functionalNameField).asInstanceOf[String],
              languageCode = Some(languageCode)
            )

          val categoryNameField = s"categoryName_${languageCode.toString}"
          if (document.containsKey(categoryNameField))
            categoryNames += Name(
              unparsedName = document.getFirstValue(categoryNameField).asInstanceOf[String],
              languageCode = Some(languageCode)
            )

          val babyFoodIngredientStatementField = s"babyFoodIngredientStatement_${languageCode.toString}"
          if (document.containsKey(babyFoodIngredientStatementField))
            babyFoodIngredientStatements += IngredientStatement(
              languageCode = languageCode,
              unparsedStatement = document.getFirstValue(babyFoodIngredientStatementField).asInstanceOf[String]
            )

          val clothesCompositionField = s"clothesComposition_${languageCode.toString}"
          if (document.containsKey(clothesCompositionField))
            clothesCompositions += Composition(
              languageCode = languageCode,
              unparsedComposition = document.getFirstValue(clothesCompositionField).asInstanceOf[String]
            )
        }

        val maybeBabyFood = if (babyFoodIngredientStatements.isEmpty) None else Some(BabyFood(babyFoodIngredientStatements))

        val maybeClothes = if (clothesCompositions.isEmpty) None else Some(Clothes(clothesCompositions))

        Product(
          id = UUID.fromString(document.getFirstValue("id").asInstanceOf[String]),
          functionalNames,
          brandName = Name(
            unparsedName = document.getFirstValue("brandName").asInstanceOf[String],
            languageCode = None
          ),
          category = Category(
            categoryType = CategoryType.withName(
              document.getFirstValue("categoryType").asInstanceOf[String]
            ),
            categoryNames
          ),
          sustainaIndex = document.getFirstValue("sustainaIndex").asInstanceOf[Double],
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
