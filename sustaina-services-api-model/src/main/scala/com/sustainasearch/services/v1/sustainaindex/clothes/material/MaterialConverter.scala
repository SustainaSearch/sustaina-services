package com.sustainasearch.services.v1.sustainaindex.clothes.material

import com.sustainasearch.services.sustainaindex.clothes.material.{Material, MaterialTypeRepository, MaterialTypeService}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class MaterialConverter @Inject()(materialTypeService: MaterialTypeService)(implicit
                                                                            ec: ExecutionContext) {

  def convertToMaterial(apiModel: MaterialApiModel): Future[Try[Material]] = {
    materialTypeService
      .getMaterialType(apiModel.id)
      .map { tMaterialType =>
        tMaterialType.map { materialType =>
          Material(materialType, apiModel.percent)
        }
      }
  }

}
