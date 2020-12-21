package com.sustainasearch.services.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.Brand.Brand
import com.sustainasearch.services.sustainaindex.Certification.Certification
import com.sustainasearch.services.sustainaindex.Country.Country

/**
  *
  * @param id         Item ID (unique for tenant)
  * @param country        Country of production
  * @param certifications Certifications of the garment
  * @param materials      Materials of the garment
  * @param garmentWeight  Total weight of the garment
  * @param brand          Brand
  */
case class Item(id: String,
                country: Country,
                certifications: Seq[Certification],
                materials: Seq[Material],
                garmentWeight: Option[Float],
                brand: Option[Brand])
