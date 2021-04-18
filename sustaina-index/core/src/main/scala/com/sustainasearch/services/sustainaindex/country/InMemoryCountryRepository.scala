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

}