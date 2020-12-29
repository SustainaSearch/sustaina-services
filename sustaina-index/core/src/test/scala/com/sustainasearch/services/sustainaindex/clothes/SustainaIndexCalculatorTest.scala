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
        result shouldBe Success(SustainaIndex(0.437F))
      }
    }
  }

}
