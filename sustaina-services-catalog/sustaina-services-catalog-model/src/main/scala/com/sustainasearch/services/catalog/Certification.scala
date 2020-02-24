package com.sustainasearch.services.catalog

import com.sustainasearch.services.{Name}
import com.sustainasearch.services.catalog.CertificationCode.CertificationCode

case class Certification(certificationCode: CertificationCode, 
					     names: Seq[Name],
						 logoUrl: String)
