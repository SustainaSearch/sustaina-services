package com.sustainasearch.services.sustainaindex

import com.sustainasearch.services.sustainaindex

/**
  * Country, where country code is  ISO 3166-1 alpha-2 code - https://en.wikipedia.org/wiki/List_of_ISO_3166_country_codes
  */
object Country extends Enumeration {

  protected case class Val(
                            countryCode: String,
                            energyMix: Int,
                            transparency: Int
                          ) extends super.Val(countryCode)

  implicit def valueToCountryCodeVal(x: Value): sustainaindex.Country.Country = x.asInstanceOf[Val]

  def withCountryCode(countryCode: String): Country.Country = Country.withName(countryCode.toUpperCase)

  type Country = Val
  val Bangladesh = Val(
    countryCode = "BD",
    energyMix = 1,
    transparency = 23
  )
  val Germany = Val(
    countryCode = "DE",
    energyMix = 2,
    transparency = 87
  )
  val Sweden = Val(
    countryCode = "SE",
    energyMix = 3,
    transparency = 78
  )

}
