package com.sustainasearch.services.sustainaindex.brand

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

// TODO: add cache
// TODO: add clear cache method
@Singleton
class BrandService @Inject()(brandRepository: BrandRepository)(implicit ec: ExecutionContext) {

  def getBrand(id: Int): Future[Try[Brand]] = brandRepository.getBrand(id)

}
