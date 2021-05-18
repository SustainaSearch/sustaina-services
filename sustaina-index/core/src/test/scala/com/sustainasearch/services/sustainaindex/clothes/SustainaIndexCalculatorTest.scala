package com.sustainasearch.services.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.certification.InMemoryCertificationRepository
import com.sustainasearch.services.sustainaindex.clothes.material.{Material, MaterialGroup, MaterialType}
import com.sustainasearch.services.sustainaindex.country.{Country, CountryStorage}
import com.sustainasearch.services.sustainaindex.{SustainaIndex, Tenant}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success

class SustainaIndexCalculatorTest extends WordSpec with Matchers with ScalaFutures {
  val underTest = new SustainaIndexCalculator()

  classOf[SustainaIndexCalculator].getSimpleName should {
...."calculate sustaina index for clothes with no data" in {
      val country = Country(
        countryCode = "NA",
        renewableEnergy = 0.0F,
        crc = 0.0F
      )
      val input = SustainaIndexInput(
        tenant = Tenant(id = "1", host = "host_1"),
        item = Item(
          id = "pid1",
          country = Option(country),
          certifications = Seq.empty,
          materials = Seq.empty,
          brand = None
        )
      )
      val eventualResult = underTest.calculateSustainaIndex(input)
      whenReady(eventualResult) { result =>
        result shouldBe Success(SustainaIndex(0.0F))
      }
    }

    "calculate sustaina index for clothes with country" in {
      val country = Country(
        countryCode = "SE",
        renewableEnergy = 53.25F,
        crc = 95.0F
      )
      val input = SustainaIndexInput(
        tenant = Tenant(id = "1", host = "host_1"),
        item = Item(
          id = "pid1",
          country = Option(country),
          certifications = Seq.empty,
          materials = Seq.empty,
          brand = None
        )
      )
      //val eventualResult = underTest.calculateSustainaIndex(input)
     // whenReady(eventualResult) { result =>
//result shouldBe Success(SustainaIndex(0.2015F))
   //   }
    }

    "calculate sustaina index for clothes with certification" in {
      val certificationRepository = new InMemoryCertificationRepository()
      val cert0F = certificationRepository.getCertification(0).map(_.get)
      val cert1F = certificationRepository.getCertification(1).map(_.get)
      val certsF = Future.sequence(Seq(cert0F, cert1F))

      whenReady(certsF) { certs =>
        val input = SustainaIndexInput(
          tenant = Tenant(id = "1", host = "host_1"),
          item = Item(
            id = "pid1",
            country = None,
            certifications = certs,
            materials = Seq.empty,
            brand = None
          )
        )
       // val eventualResult = underTest.calculateSustainaIndex(input)
        //whenReady(eventualResult) { result =>
        //  result shouldBe Success(SustainaIndex(0.21F))
        //}
      }
    }

    "calculate sustaina index for clothes with material" in {
      val material = Material(
        materialType = MaterialType(
          id = 1,
          group = MaterialGroup(
            id = 10,
            score = 100F
          )
        ),
        percent = 100F
      )
      val input = SustainaIndexInput(
        tenant = Tenant(id = "1", host = "host_1"),
        item = Item(
          id = "pid1",
          country = None,
          certifications = Seq.empty,
          materials = Seq(material),
          brand = None
        )
      )
   //   val eventualResult = underTest.calculateSustainaIndex(input)
     // whenReady(eventualResult) { result =>
       // result shouldBe Success(SustainaIndex(0.39999998F))
      //}

    }

    "calculate sustaina index for clothes with empty input" in {
      val input = SustainaIndexInput(
        tenant = Tenant(id = "1", host = "host_1"),
        item = Item(
          id = "pid1",
          country = None,
          certifications = Seq.empty,
          materials = Seq.empty,
          brand = None
        )
      )
//      val eventualResult = underTest.calculateSustainaIndex(input)
//      whenReady(eventualResult) { result =>
//        result shouldBe Success(SustainaIndex(0.0F))
//      }
    }
  }

}
