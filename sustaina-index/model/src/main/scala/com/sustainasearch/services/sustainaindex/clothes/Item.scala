package com.sustainasearch.services.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.brand.Brand
import com.sustainasearch.services.sustainaindex.certification.Certification
import com.sustainasearch.services.sustainaindex.clothes.material.Material
import com.sustainasearch.services.sustainaindex.country.Country

/**
  *
  * @param id             Item ID (unique for tenant)
  * @param country        Country of production
  * @param certifications Certifications of the product
  * @param materials      Materials of the product
  * @param brand          Brand
  */
case class Item(id: String,
                country: Option[Country],
                certifications: Seq[Certification],
                materials: Seq[Material],
                brand: Option[Brand])
