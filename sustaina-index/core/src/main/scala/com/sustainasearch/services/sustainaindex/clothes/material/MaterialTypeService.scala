package com.sustainasearch.services.sustainaindex.clothes.material

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

// TODO: add cache
// TODO: add clear cache method
@Singleton
class MaterialTypeService @Inject()(materialTypeRepository: MaterialTypeRepository)(implicit ec: ExecutionContext) {

  def getMaterialType(id: Int): Future[Try[MaterialType]] = materialTypeRepository.getMaterialType(id)

}
