package com.sustainasearch.services.sustainaindex.certification

import org.scalatest.{Matchers, WordSpec}

class InMemoryCertificationStorageTest extends WordSpec with Matchers {

  "InMemoryCertificationStorage" should {
    "find CertificationStorage.Val with id" in {
      CertificationStorage(0) shouldBe CertificationStorage.BlueSign
      CertificationStorage(1) shouldBe CertificationStorage.EUEcoLabel
    }

    "provide score" in {
      CertificationStorage.BlueSign.score shouldBe 17
      CertificationStorage.EUEcoLabel.score shouldBe 61
    }
  }
}
