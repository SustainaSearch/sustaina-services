package com.sustainasearch.services.sustainaindex.country

/**
  * Country, where country code is the ISO 3166-1 alpha-2 code - https://en.wikipedia.org/wiki/List_of_ISO_3166_country_codes
  */
case class Country(countryCode: String,
                   renewableEnergy: Float,
                   crc: Float)
