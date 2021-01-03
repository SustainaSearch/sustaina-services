package com.sustainasearch.services.sustainaindex.country

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions
import scala.util.{Success, Try}

@Singleton
class InMemoryCountryRepository @Inject()(implicit ec: ExecutionContext) extends CountryRepository {

  override def getCountry(countryCode: Option[String]): Future[Try[Option[Country]]] = {
    countryCode.fold[Future[Try[Option[Country]]]](Future.successful(Success(None))) { cc =>
      Future {
        Try(CountryStorage.fromCountryCode(cc)).map(_.toCountry).map(Some(_))
      }
    }
  }

}

private object CountryStorage extends Enumeration {

  protected case class Val(
                            countryCode: String,
                            energyMix: Int,
                            transparency: Int
                          ) extends super.Val(countryCode) {

    def toCountry: Country = Country(
      countryCode = countryCode,
      energyMix = energyMix,
      transparency = transparency
    )

  }

  implicit def valueToCountryCodeVal(x: Value): CountryStorage.Val = x.asInstanceOf[Val]

  def fromCountryCode(countryCode: String): CountryStorage.Val = CountryStorage.withName(countryCode.toUpperCase)

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