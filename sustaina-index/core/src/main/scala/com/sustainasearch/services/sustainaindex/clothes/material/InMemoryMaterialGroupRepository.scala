package com.sustainasearch.services.sustainaindex.clothes.material

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions
import scala.util.Try

@Singleton
class InMemoryMaterialGroupRepository @Inject()(implicit ec: ExecutionContext) extends MaterialGroupRepository {

  override def getMaterialGroup(id: Int): Future[Try[MaterialGroup]] = {
    Future {
      Try(MaterialGroupStorage(id)).map(_.toMaterialGroup)
    }
  }

}

private object MaterialGroupStorage extends Enumeration {

  case class Val(
                  override val id: Int,
                  score: Int
                ) extends super.Val(id) {

    def toMaterialGroup: MaterialGroup = MaterialGroup(id, score)

  }

  implicit def valueToMaterialGroupVal(x: Value): MaterialGroupStorage.Val = x.asInstanceOf[Val]

  val group0 = Val(
    id = nextId,
    score = 27
  )
  val group1 = Val(
    id = nextId,
    score = 0
  )
  val group2 = Val(
    id = nextId,
    score = 46
  )

}