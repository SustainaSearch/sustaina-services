package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.catalog.{City, Country, RepresentativePoint}

case class ProductActivity(country: Country,
                           city: Option[City],
                           representativePoint: RepresentativePoint)
