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
  val MAX_GROUP = 10
  val MAX_POINTS = 100

  // use materal fx X^2
  def getScore(x: Int): Int = MAX_POINTS * (x * x) / (MAX_GROUP*MAX_GROUP)
  
  val group0 = Val( // group 0 gives 0 material points
    id = nextId,
    score = getScore(0)
  )
  val group1 = Val(
    id = nextId,
    score = getScore(1)
  )
  val group2 = Val(
    id = nextId,
    score = getScore(2)
  )
  val group3 = Val(
    id = nextId,
    score = getScore(3)
  )
  val group4 = Val(
    id = nextId,
    score = getScore(4)
  )
  val group5 = Val(
    id = nextId,
    score = getScore(5)
  )
  val group6 = Val(
    id = nextId,
    score = getScore(6)
  )
  val group7 = Val(
    id = nextId,
    score = getScore(7)
  )
  val group8 = Val(
    id = nextId,
    score = getScore(8)
  )
  val group9 = Val(
    id = nextId,
    score = getScore(9)
  )
  val group10 = Val(
    id = nextId,
    score = getScore(10) // group 10 gives 100 material points (40 with material weight 0.4)
  )
  
}