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

object MaterialGroupStorage extends Enumeration {
  private val MaxGroup = 10
  private val MaxPoints = 100
  private val StartPoints = 30

  case class Val(override val id: Int) extends super.Val(id) {
    // TODO: why not only pow2?
    // lazy val score: Int = (MaxPoints * pow2(id)) / pow2(MaxGroup)
	lazy val score: Int = if (id == 0) 0 else (StartPoints + ((id)*(MaxPoints-StartPoints)/MaxGroup))
    private def pow2(x: Int): Int = math.pow(x, 2).toInt

    def toMaterialGroup: MaterialGroup = MaterialGroup(id, score)

  }

  implicit def valueToMaterialGroupVal(x: Value): MaterialGroupStorage.Val = x.asInstanceOf[Val]

  val group0 = Val(id = nextId)
  val group1 = Val(id = nextId)
  val group2 = Val(id = nextId)
  val group3 = Val(id = nextId)
  val group4 = Val(id = nextId)
  val group5 = Val(id = nextId)
  val group6 = Val(id = nextId)
  val group7 = Val(id = nextId)
  val group8 = Val(id = nextId)
  val group9 = Val(id = nextId)
  val group10 = Val(id = nextId)

}