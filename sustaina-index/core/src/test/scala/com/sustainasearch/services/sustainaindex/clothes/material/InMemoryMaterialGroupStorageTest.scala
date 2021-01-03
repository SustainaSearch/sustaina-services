package com.sustainasearch.services.sustainaindex.clothes.material

import org.scalatest.{Matchers, WordSpec}

class InMemoryMaterialGroupStorageTest extends WordSpec with Matchers {

  "InMemoryMaterialGroupStorage" should {
    "find MaterialGroupStorage.Val with id" in {
      MaterialGroupStorage(0) shouldBe MaterialGroupStorage.group0
      MaterialGroupStorage(1) shouldBe MaterialGroupStorage.group1
      MaterialGroupStorage(2) shouldBe MaterialGroupStorage.group2
    }

    "provide score" in {
      MaterialGroupStorage.group0.score shouldBe 27
      MaterialGroupStorage.group1.score shouldBe 0
      MaterialGroupStorage.group2.score shouldBe 46
    }
  }
}
