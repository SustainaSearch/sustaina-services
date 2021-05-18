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

object CountryStorage extends Enumeration {

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


  val NotAvailable = Val(
    countryCode     = "NA",
    renewableEnergy = 0.0F,
    crc             = 0.0F
  )

  val Bangladesh = Val(
    countryCode     = "BD",
    renewableEnergy = 34.75F,
    crc             = 21.8F
  )

  val Germany = Val(
    countryCode     = "DE",
    renewableEnergy = 14.21F,
    crc             = 89.5F
  )

  val Sweden = Val(
    countryCode     = "SE",
    renewableEnergy = 53.25F,
    crc             = 95.0F
  )

   val China = Val(
    countryCode     = "CN",
    renewableEnergy = 12.41F,
    crc             = 42.1F
  )

   val UnitedStatesOfAmerica = Val(
    countryCode     = "US",
    renewableEnergy = 8.72F,
    crc             = 84.6F
  )

  val Turkey = Val(
    countryCode     = "TR",
    renewableEnergy = 13.37F,
    crc             = 40.3F
  )

  val SouthKorea = Val( //Republic of Korea
    countryCode     = "KR",
    renewableEnergy = 2.71F,
    crc             = 74.6F
  )

  val VietNam = Val(
    countryCode     = "VN",
    renewableEnergy = 35.0F,
    crc             = 41.2F
  )

  val Pakistan = Val(
    countryCode     = "PK",
    renewableEnergy = 46.48F,
    crc             = 22.9F
  )

  val HongKong = Val(
    countryCode     = "HK",
    renewableEnergy = 0.85F,
    crc             = 86.6F
  )
  
  val Cambodia = Val(
    countryCode     = "KH",
    renewableEnergy = 64.92F,
    crc             = 24.7F
  )

   val SriLanka = Val(
    countryCode     = "LK",
    renewableEnergy = 52.88F,
    crc             = 46.8F
  )
  
  val Lithuania = Val(
    countryCode     = "LT",
    renewableEnergy = 28.96F,
    crc             = 77.6F
  )
  
   val India = Val(
    countryCode     = "IN",
    renewableEnergy = 36.0F,
    crc             = 46.3F
  )
  
  val Portugal = Val(
    countryCode     = "PT",
    renewableEnergy = 27.16F,
    crc             = 84.8F
  )

  val Bulgaria = Val(
    countryCode     = "BG",
    renewableEnergy = 17.65F,
    crc             = 59.8F
  )

    val Austria = Val(
    countryCode     = "AT",
    renewableEnergy = 34.39F,
    crc             = 91.6F
  )


  val EuropeanUnion = Val(
    countryCode     = "EU",
    renewableEnergy = fromCountryCode("BG").renewableEnergy, // Todo check worst values from EU, BG = Bulgaria
    crc             = fromCountryCode("BG").crc
  )

  val Italy = Val(
    countryCode     = "IT",
    renewableEnergy = 16.52F,
    crc             = 67.9F
  )

    val PerfectScoreCountry = Val( // used for test and if process step is skipped
    countryCode     = "XX",
    renewableEnergy = 100.0F,
    crc             = 100.0F
  )
}