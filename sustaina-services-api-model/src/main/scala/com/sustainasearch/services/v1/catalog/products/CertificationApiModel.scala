package com.sustainasearch.services.v1.catalog.products

import play.api.libs.json.Json
import com.sustainasearch.services.v1.{NameApiModel}

case class CertificationApiModel(certificationCode: String, 
								 names: Seq[NameApiModel],
								 logoUrl: String)

object CertificationApiModel {
  implicit val format = Json.format[CertificationApiModel]
}
