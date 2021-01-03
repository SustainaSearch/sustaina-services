package com.sustainasearch.services.sustainaindex.country

import scala.concurrent.Future
import scala.util.Try

trait CountryRepository {

  def getCountry(countryCode: Option[String]): Future[Try[Option[Country]]]

}
