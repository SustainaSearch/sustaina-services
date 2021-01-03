package com.sustainasearch.services.sustainaindex.brand

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions
import scala.util.Try

@Singleton
class InMemoryBrandRepository @Inject()(implicit ec: ExecutionContext) extends BrandRepository {

  override def getBrand(id: Int): Future[Try[Brand]] = {
    Future {
      Try(BrandStorage(id)).map(_.toBrand)
    }
  }

}

private object BrandStorage extends Enumeration {

  protected case class Val(
                            override val id: Int,
                            score: Int
                          ) extends super.Val(id) {

    def toBrand: Brand = Brand(id, score)

  }

  implicit def valueToBrandVal(x: Value): BrandStorage.Val = x.asInstanceOf[Val]

  val FilippaK = Val(
    id = nextId,
    score = 17
  )
  val PoP = Val(
    id = nextId,
    score = 61
  )
}