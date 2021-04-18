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
						// process
						chemical_use_restriction: Int,
						//quality
						requirements_on_wear_tear_and_color_percistance: Int,
						// workers rights
						workers_rights: Int,
						// packaging
						requirements_on_packaging: Int
						) extends super.Val(id) {

    def toCertification: Certification = Certification(
		id = id,
		chemical_use_restriction = chemical_use_restriction,
		requirements_on_wear_tear_and_color_percistance = requirements_on_wear_tear_and_color_percistance,
		workers_rights = workers_rights,
		requirements_on_packaging = requirements_on_packaging
    )

  }

  implicit def valueToCertificationVal(x: Value): CertificationStorage.Val = x.asInstanceOf[Val]

  val BlueSign    = Val(nextId,      10,  0, 10, 0)
  val EUEcoLabel  = Val(nextId,      10, 10, 10, 0)
  val BraMiljoval = Val(nextId,      10, 10, 10, 0)
  val GOTS        = Val(nextId,      10, 10, 10, 0)
  val OekoTex100  = Val(nextId,       5,  0,  0, 0)
  val OekoTexStep = Val(nextId,       5,  0, 10, 0)
  val Svanen      = Val(nextId,      10, 10, 10, 0)
  val MadeInGreen = Val(nextId,      10,  0, 10, 0)

}