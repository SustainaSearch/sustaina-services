package com.sustainasearch.services.v1.catalog.products

import com.sustainasearch.dynamodb.{DynamoDBFactory, TablePrefix}
import com.sustainasearch.services.catalog.products.{DynamoDBProductCategoryRepository, ProductCategoryRepository}
import play.api.inject.{Binding, Module}
import play.api.{Configuration, Environment}

class ProductCategoriesModule extends Module {
  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {

    val accessKey = configuration.get[String]("aws.access.key")
    val secretKey = configuration.get[String]("aws.secret.key")
    val region = configuration.get[String]("aws.region")
    val dynamoDBFactory = new DynamoDBFactory(
      accessKey = accessKey,
      secretKey = secretKey,
      region = region
    )

    // TODO: make use of
    //    val tablePrefix = TablePrefixFactory.createTablePrefix(environment)
    val tablePrefix = TablePrefix("Dev_")

    Seq(
      bind[TablePrefix].toInstance(tablePrefix),
      bind[DynamoDBFactory].toInstance(dynamoDBFactory),
      bind[ProductCategoryRepository].to[DynamoDBProductCategoryRepository]
    )
  }
}
