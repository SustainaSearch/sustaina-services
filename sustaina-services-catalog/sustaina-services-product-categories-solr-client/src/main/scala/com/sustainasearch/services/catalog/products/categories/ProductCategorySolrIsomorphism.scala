package com.sustainasearch.services.catalog.products.categories

import com.sustainasearch.searchengine.QueryResponse
import com.sustainasearch.searchengine.solr.SolrIsomorphism
import com.sustainasearch.services.catalog.products.{Category, CategoryType}
import com.sustainasearch.services.{LanguageCode, Name}
import org.apache.solr.client.solrj.response.{QueryResponse => SolrQueryResponse}
import org.apache.solr.common.{SolrDocument, SolrInputDocument}

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

class ProductCategorySolrIsomorphism(fieldRegister: ProductCategorySearchEngineFieldRegister) extends SolrIsomorphism[Category, Option[Nothing]] {

  import fieldRegister._

  override def toSolrInputDocument(category: Category): SolrInputDocument = {
    val document = new SolrInputDocument()
    document.addField(IdField, category.categoryType.toString)

    category.names.foreach { name =>
      document.addField(categoryNameWithNameLanguageCodeField(name), name.unparsedName)
    }

    document
  }

  override def fromSolrDocument(document: SolrDocument): Category = {
    val categoryNames = ListBuffer.empty[Name]

    LanguageCode.values.foreach { languageCode =>
      val categoryNameField = categoryNameWithLanguageCodeField(languageCode)
      if (document.containsKey(categoryNameField))
        categoryNames += Name(
          unparsedName = document.getFirstValue(categoryNameField).asInstanceOf[String],
          languageCode = Some(languageCode)
        )
    }

    Category(
      categoryType = CategoryType.withName(
        document.getFirstValue(IdField).asInstanceOf[String]
      ),
      categoryNames
    )
  }

  override def fromSolrQueryResponse(response: SolrQueryResponse): QueryResponse[Category, Option[Nothing]] = {
    val results = response.getResults
    val documents = results
      .asScala
      .map(fromSolrDocument)
      .toList

    QueryResponse(
      start = results.getStart,
      numFound = results.getNumFound,
      documents,
      facets = None
    )
  }

}
