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
                            renewableEnergy: Float,
                            crc: Float
                          ) extends super.Val(countryCode) {

    def toCountry: Country = Country(
      countryCode = countryCode,
      renewableEnergy = renewableEnergy,
      crc = crc
    )

  }

  implicit def valueToCountryCodeVal(x: Value): CountryStorage.Val = x.asInstanceOf[Val]

  def fromCountryCode(countryCode: String): CountryStorage.Val = CountryStorage.withName(countryCode.toUpperCase)

  // 0 SI points with energy wheight 0.2 and crcWeight 0.1
  val NotAvailable = Val(
    countryCode     = "NA",
    renewableEnergy = 0.0F,
    crc             = 0.0F
  )
  // 9.13 SI points with energy wheight 0.2 and crcWeight 0.1
  val Bangladesh = Val(
    countryCode     = "BD",
    renewableEnergy = 34.75F,
    crc             = 21.8F
  )
  // 11.792 SI points with energy wheight 0.2 and crcWeight 0.1
  val Germany = Val(
    countryCode     = "DE",
    renewableEnergy = 14.21F,
    crc             = 89.5F
  )
  // 20.15 SI points with energy wheight 0.2 and crcWeight 0.1
  val Sweden = Val(
    countryCode     = "SE",
    renewableEnergy = 53.25F,
    crc             = 95.0F
  )

}