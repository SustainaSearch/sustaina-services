package com.sustainasearch.services.sustainaindex.clothes.material

import scala.concurrent.Future
import scala.util.Try

trait MaterialGroupRepository {

  def getMaterialGroup(id: Int): Future[Try[MaterialGroup]]

}
