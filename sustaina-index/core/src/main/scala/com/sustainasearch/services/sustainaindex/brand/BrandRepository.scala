package com.sustainasearch.services.sustainaindex.brand

import scala.concurrent.Future
import scala.util.Try

trait BrandRepository {

  def getBrand(id: Int): Future[Try[Brand]]

}
