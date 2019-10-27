package com.sustainasearch.dynamodb

import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB

// TODO: move to a separate module in a common repo
class DynamoDBFactory(accessKey: String, secretKey: String, region: String) {

  def createDynamoDB: DynamoDB = {
    val credentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey))

    val client = AmazonDynamoDBAsyncClientBuilder
      .standard()
      .withCredentials(credentialsProvider)
      .withRegion(region)
      .build()

    new DynamoDB(client)
  }

}