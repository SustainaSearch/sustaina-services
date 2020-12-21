package com.sustainasearch.services.sustainaindex

import org.scalatest.{Matchers, WordSpec}

class CertificationTest extends WordSpec with Matchers {

  "Certification" should {
    "find Certification with id" in {
      Certification.withId(0) shouldBe Certification.BlueSign
      Certification.withId(1) shouldBe Certification.EUEcoLabel
    }

    "provide score" in {
      Certification.BlueSign.score shouldBe 17
      Certification.EUEcoLabel.score shouldBe 61
    }
  }
}
