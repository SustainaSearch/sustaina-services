package com.sustainasearch.services.sustainaindex.clothes.material

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions
import scala.util.{Failure, Success, Try}

@Singleton
class InMemoryMaterialTypeRepository @Inject()(materialGroupRepository: MaterialGroupRepository)(implicit ec: ExecutionContext) extends MaterialTypeRepository {

  override def getMaterialType(id: Int): Future[Try[MaterialType]] = {
    def convertToMaterialType(materialType: MaterialTypeStorage.Val) = {
      materialGroupRepository
        .getMaterialGroup(materialType.group.id)
        .map {
          case Success(materialGroup) => Success(MaterialType(materialType.id, materialGroup))
          case Failure(t) => Failure(t)
        }
    }

    Try(MaterialTypeStorage(id)) match {
      case Success(mt) => convertToMaterialType(mt)
      case Failure(t) => Future.successful(Failure(t))
    }
  }
}

private object MaterialTypeStorage extends Enumeration {

  case class Val(
                  override val id: Int,
                  group: MaterialGroupStorage.Val
                ) extends super.Val(id) {


  }

  implicit def valueToMaterialTypeVal(x: Value): MaterialTypeStorage.Val = x.asInstanceOf[Val]

  val Cotton = Val(
    id = nextId,
    group = MaterialGroupStorage.group0 // group 0 gives 0 material points
  )
  val Wool = Val(
    id = nextId,
    group = MaterialGroupStorage.group10 // group 10 gives 100 material points (40 with material weight 0.4)
  )

}