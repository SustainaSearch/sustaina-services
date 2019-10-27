package com.sustainasearch.services.catalog.products

import com.amazonaws.services.dynamodbv2.document.Item
import com.sustainasearch.dynamodb.{DynamoDBFactory, TableName, TablePrefix}
import com.sustainasearch.services.catalog.products.CategoryType.CategoryType
import com.sustainasearch.services.catalog.products.DynamoDBProductCategoryRepository._
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DynamoDBProductCategoryRepository @Inject()(tablePrefix: TablePrefix,
                                                  dynamoDBFactory: DynamoDBFactory,
                                                  categoryJsonConverter: CategoryJsonConverter,
                                                 )(implicit ec: ExecutionContext) extends ProductCategoryRepository {
  private val dynamoDB = dynamoDBFactory.createDynamoDB
  private val tableName = TableName(TableBaseName).withTablePrefix(tablePrefix).value

  override def getAllCategories: Future[Seq[Category]] = {
    val eventualCategories = CategoryType.values.map(getCategory)
    Future
      .sequence(eventualCategories)
      .map(_.flatten.toSeq)
  }

  override def getCategory(categoryType: CategoryType): Future[Option[Category]] = {
    Future {
      val table = dynamoDB.getTable(tableName)
      val item = table.getItem(HashKeyName, categoryType.toString)

      if (item == null)
        None
      else {
        val categoryJson = item.getJSON(CategoryAttrName)
        val category = categoryJsonConverter.fromJson(categoryJson)
        Some(category)
      }
    }
  }

  override def add(category: Category): Future[Category] = {
    Future {
      val table = dynamoDB.getTable(tableName)
      val categoryJson = categoryJsonConverter.toJson(category)
      val item = new Item()
        .withPrimaryKey(HashKeyName, category.categoryType.toString)
        .withJSON(CategoryAttrName, categoryJson)
      table.putItem(item)
      category
    }
  }

}

object DynamoDBProductCategoryRepository {
  val TableBaseName = "ProductCategory"
  val HashKeyName = "CategoryType"
  val CategoryAttrName = "Category"
}
