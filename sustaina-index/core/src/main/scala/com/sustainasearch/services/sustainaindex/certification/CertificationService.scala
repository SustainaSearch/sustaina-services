package com.sustainasearch.services.sustainaindex.certification

import com.sustainasearch.services.sustainaindex.brand.{Brand, BrandRepository}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

// TODO: add cache
// TODO: add clear cache method
@Singleton
class CertificationService @Inject()(certificationRepository: CertificationRepository)(implicit ec: ExecutionContext) {

  def getCertification(id: Int): Future[Try[Certification]] = certificationRepository.getCertification(id)

}
