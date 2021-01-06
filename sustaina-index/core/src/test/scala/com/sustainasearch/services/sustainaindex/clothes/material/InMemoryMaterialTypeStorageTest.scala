package com.sustainasearch.services.sustainaindex.clothes.material

import org.scalatest.{Matchers, WordSpec}

class InMemoryMaterialTypeStorageTest extends WordSpec with Matchers {

  "InMemoryMaterialTypeStorage" should {
    "find MaterialTypeStorage.Val with id" in {
      MaterialTypeStorage(0) shouldBe MaterialTypeStorage.Cotton
      MaterialTypeStorage(1) shouldBe MaterialTypeStorage.Wool
    }

    "provide group" in {
      MaterialTypeStorage.Cotton.group shouldBe MaterialGroupStorage.group0
      MaterialTypeStorage.Wool.group shouldBe MaterialGroupStorage.group2
    }
  }
}
