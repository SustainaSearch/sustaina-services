package com.sustainasearch.dynamodb

import play.api.Environment
import play.api.Mode.{Dev, Prod, Test}

object TablePrefixFactory {

  def createTablePrefix(environment: Environment): TablePrefix = {
    environment.mode match {
      case Dev => TablePrefix("Dev_")
      case Test => TablePrefix("Test_")
      case Prod => TablePrefix("Prod_")
    }
  }

}
