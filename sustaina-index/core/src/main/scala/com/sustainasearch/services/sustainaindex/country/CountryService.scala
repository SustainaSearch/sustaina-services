package com.sustainasearch.services.sustainaindex.country

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

// TODO: add cache
// TODO: add clear cache method
@Singleton
class CountryService @Inject()(countryRepository: CountryRepository)(implicit ec: ExecutionContext) {

  def getCountry(countryCode: Option[String]): Future[Try[Option[Country]]] = countryRepository.getCountry(countryCode)

}
