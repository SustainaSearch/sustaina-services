package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.catalog.products.CountryCode.CountryCode

case class Country(countryCode: CountryCode, names: Seq[Name])
