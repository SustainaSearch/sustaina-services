package com.sustainasearch.services.sustainaindex.clothes.material

import scala.concurrent.Future
import scala.util.Try

trait MaterialTypeRepository {

  def getMaterialType(id: Int): Future[Try[MaterialType]]

}
