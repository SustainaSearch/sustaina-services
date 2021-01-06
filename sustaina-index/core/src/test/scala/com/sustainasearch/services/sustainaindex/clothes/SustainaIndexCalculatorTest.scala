package com.sustainasearch.services.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.{SustainaIndex, Tenant}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

class SustainaIndexCalculatorTest extends WordSpec with Matchers with ScalaFutures {
  val underTest = new SustainaIndexCalculator()

  classOf[SustainaIndexCalculator].getSimpleName should {
    "calculate sustaina index for clothes" in {

	// Test country
      val input = SustainaIndexInput(
        tenant = Tenant(id = "1", host = "host_1"),
        item = Item(
          id = "pid1",
          country = Option(Country.withCountryCode("SE")),
          certifications = Seq.empty,
          materials = Seq.empty,
          garmentWeight = None,
          brand = None
        )
      )
      val eventualResult = underTest.calculateSustainaIndex(input)
      whenReady(eventualResult) { result =>
        result shouldBe Success(SustainaIndex(0.2015F))
      }

	  // Test certification
	  val input = SustainaIndexInput(
        tenant = Tenant(id = "1", host = "host_1"),
        item = Item(
          id = "pid1",
          country = None,
          certifications = Seq(Certification.withId(0), Certification.withId(1)),
          materials = Seq.empty,
          garmentWeight = None,
          brand = None
        )
      )
      val eventualResult = underTest.calculateSustainaIndex(input)
      whenReady(eventualResult) { result =>
        result shouldBe Success(SustainaIndex(0.21F))
      }

	// Test certification
	  val input = SustainaIndexInput(
        tenant = Tenant(id = "1", host = "host_1"),
        item = Item(
          id = "pid1",
          country = None,
          certifications = Seq.empty,
          materials = Seq(Material(MaterialType(1), 1.0F)),
          garmentWeight = None,
          brand = None
        )
      )
      val eventualResult = underTest.calculateSustainaIndex(input)
      whenReady(eventualResult) { result =>
        result shouldBe Success(SustainaIndex(0.4F))
      }

	  // Test empty
	  val input = SustainaIndexInput(
        tenant = Tenant(id = "1", host = "host_1"),
        item = Item(
          id = "pid1",
          country = None,
          certifications = Seq.empty,
          materials = Seq.empty,
          garmentWeight = None,
          brand = None
        )
      )
      val eventualResult = underTest.calculateSustainaIndex(input)
      whenReady(eventualResult) { result =>
        result shouldBe Success(SustainaIndex(0.0F))
      }
    }
  }

}
