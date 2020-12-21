package com.sustainasearch.services.sustainaindex

import org.scalatest.{Matchers, WordSpec}

class CountryTest extends WordSpec with Matchers {

  "Country" should {
    "find Country with name" in {
      Country.withName("SE") shouldBe Country.Sweden
    }

    "find Country with country code" in {
      Country.withCountryCode("SE") shouldBe Country.Sweden
    }

    "find Country with country code in lower case" in {
      Country.withCountryCode("se") shouldBe Country.Sweden
    }

    "find Country with country code in camel case" in {
      Country.withCountryCode("Se") shouldBe Country.Sweden
    }

    "provide energy mix in specified country" in {
      Country.Bangladesh.energyMix shouldBe 1
      Country.Germany.energyMix shouldBe 2
      Country.Sweden.energyMix shouldBe 3
    }
  }
}
