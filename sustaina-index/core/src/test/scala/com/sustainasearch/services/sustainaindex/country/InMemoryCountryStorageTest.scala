package com.sustainasearch.services.sustainaindex.country

import org.scalatest.{Matchers, WordSpec}

class InMemoryCountryStorageTest extends WordSpec with Matchers {

  "InMemoryCountryStorage" should {
    "find CountryStorage.Val with name" in {
      CountryStorage.fromCountryCode("SE") shouldBe CountryStorage.Sweden
    }

    "find CountryStorage.Val with country code" in {
      CountryStorage.fromCountryCode("SE") shouldBe CountryStorage.Sweden
    }

    "find CountryStorage.Val with country code in lower case" in {
      CountryStorage.fromCountryCode("se") shouldBe CountryStorage.Sweden
    }

    "find CountryStorage.Val with country code in camel case" in {
      CountryStorage.fromCountryCode("Se") shouldBe CountryStorage.Sweden
    }

    "provide energy mix in specified country" in {
      CountryStorage.Bangladesh.renewableEnergy shouldBe 34.75
      CountryStorage.Germany.renewableEnergy shouldBe 14.21
      CountryStorage.Sweden.renewableEnergy shouldBe 53.25
    }
  }
}
