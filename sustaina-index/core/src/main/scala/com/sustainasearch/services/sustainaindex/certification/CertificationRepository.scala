package com.sustainasearch.services.sustainaindex.certification

import scala.concurrent.Future
import scala.util.Try

trait CertificationRepository {

  def getCertification(id: Int): Future[Try[Certification]]

}
