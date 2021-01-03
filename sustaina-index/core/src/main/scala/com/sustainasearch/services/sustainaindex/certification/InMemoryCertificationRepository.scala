package com.sustainasearch.services.sustainaindex.certification

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions
import scala.util.Try

@Singleton
class InMemoryCertificationRepository @Inject()(implicit ec: ExecutionContext) extends CertificationRepository {

  override def getCertification(id: Int): Future[Try[Certification]] = {
    Future {
      Try(CertificationStorage(id)).map(_.toCertification)
    }
  }

}

private object CertificationStorage extends Enumeration {

  protected case class Val(
                            override val id: Int,
                            score: Int
                          ) extends super.Val(id) {

    def toCertification: Certification = Certification(
      id = id,
      score = score
    )

  }

  implicit def valueToCertificationVal(x: Value): CertificationStorage.Val = x.asInstanceOf[Val]

  val BlueSign = Val(
    id = nextId,
    score = 17
  )
  val EUEcoLabel = Val(
    id = nextId,
    score = 61
  )
}