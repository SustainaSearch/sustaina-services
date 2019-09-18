package com.sustainasearch.services.catalog

import com.sustainasearch.services.Name
import com.sustainasearch.services.catalog.CountryCode.CountryCode

case class Country(countryCode: CountryCode, names: Seq[Name])
