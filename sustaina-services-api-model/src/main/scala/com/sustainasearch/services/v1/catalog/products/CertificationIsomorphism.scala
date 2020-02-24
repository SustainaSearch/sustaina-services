package com.sustainasearch.services.v1.catalog.products

import com.sustainasearch.services.catalog.{Certification, CertificationCode}
import com.sustainasearch.services.v1.{NameIsomorphism}
import scalaz.Isomorphism.<=>

object CertificationIsomorphism {
  val certification = new (Certification <=> CertificationApiModel) {
    val to: Certification => CertificationApiModel = { certification =>
      CertificationApiModel(
        certificationCode = certification.certificationCode.toString,
        names = certification.names.map(NameIsomorphism.name.to),
		logoUrl = certification.logoUrl
      )
    }
    val from: CertificationApiModel => Certification = { certification =>
      Certification(
        certificationCode = CertificationCode.withName(certification.certificationCode),
		names = certification.names.map(NameIsomorphism.name.from),
		logoUrl = certification.logoUrl
      )
    }
  }
}