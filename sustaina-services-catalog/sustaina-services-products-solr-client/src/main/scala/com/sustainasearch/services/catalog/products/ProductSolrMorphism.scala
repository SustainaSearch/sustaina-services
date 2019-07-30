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
        val id = document.getFirstValue("id").asInstanceOf[String]

        val functionalNames = ListBuffer.empty[Name]

        if (document.containsKey("functionalName"))
          functionalNames += Name(
            unparsedName = document.getFirstValue("functionalName").asInstanceOf[String],
            languageCode = None
          )

        if (document.containsKey("functionalName_en"))
          functionalNames += Name(
            unparsedName = document.getFirstValue("functionalName_en").asInstanceOf[String],
            languageCode = Some(LanguageCode.English)
          )

        if (document.containsKey("functionalName_sv"))
          functionalNames += Name(
            unparsedName = document.getFirstValue("functionalName_sv").asInstanceOf[String],
            languageCode = Some(LanguageCode.Swedish)
          )

        val brandName = Name(
          unparsedName = document.getFirstValue("brandName").asInstanceOf[String],
          languageCode = None
        )

        val categoryType = CategoryType.withName(
          document.getFirstValue("categoryType").asInstanceOf[String]
        )

        val categoryNames = ListBuffer.empty[Name]

        if (document.containsKey("categoryName_en"))
          categoryNames += Name(
            unparsedName = document.getFirstValue("categoryName_en").asInstanceOf[String],
            languageCode = Some(LanguageCode.English)
          )

        if (document.containsKey("categoryName_sv"))
          categoryNames += Name(
            unparsedName = document.getFirstValue("categoryName_sv").asInstanceOf[String],
            languageCode = Some(LanguageCode.Swedish)
          )

        val category = Category(
          categoryType = categoryType,
          categoryNames
        )

        val sustainaIndex = document.getFirstValue("sustainaIndex").asInstanceOf[Double]

        val babyFoodIngredientStatements = ListBuffer.empty[IngredientStatement]

        if (document.containsKey("babyFoodIngredientStatement_en"))
          babyFoodIngredientStatements += IngredientStatement(
            languageCode = LanguageCode.English,
            unparsedStatement = document.getFirstValue("babyFoodIngredientStatement_en").asInstanceOf[String]
          )

        if (document.containsKey("babyFoodIngredientStatement_sv"))
          babyFoodIngredientStatements += IngredientStatement(
            languageCode = LanguageCode.Swedish,
            unparsedStatement = document.getFirstValue("babyFoodIngredientStatement_sv").asInstanceOf[String]
          )

        val maybeBabyFood = if (babyFoodIngredientStatements.isEmpty) None else Some(BabyFood(babyFoodIngredientStatements))

        val clothesCompositions = ListBuffer.empty[Composition]

        if (document.containsKey("clothesComposition_en"))
          clothesCompositions += Composition(
            languageCode = LanguageCode.English,
            unparsedComposition = document.getFirstValue("clothesComposition_en").asInstanceOf[String]
          )

        if (document.containsKey("clothesComposition_sv"))
          clothesCompositions += Composition(
            languageCode = LanguageCode.Swedish,
            unparsedComposition = document.getFirstValue("clothesComposition_sv").asInstanceOf[String]
          )

        val maybeClothes = if (clothesCompositions.isEmpty) None else Some(Clothes(clothesCompositions))

        Product(
          UUID.fromString(id),
          functionalNames,
          brandName,
          category,
          sustainaIndex,
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
