package com.sustainasearch.services.sustainaindex.clothes.material

import org.scalatest.{Matchers, WordSpec}

class InMemoryMaterialTypeStorageTest extends WordSpec with Matchers {

  "InMemoryMaterialTypeStorage" should {
    "find MaterialTypeStorage.Val with id" in {
      MaterialTypeStorage(100) shouldBe MaterialTypeStorage.Cotton_NoInfo
      MaterialTypeStorage(401) shouldBe MaterialTypeStorage.Wool_NonChlorine
    }

    "provide group" in {
      MaterialTypeStorage.Cotton_NoInfo.group shouldBe MaterialGroupStorage.group1
      MaterialTypeStorage.Wool_NonChlorine.group shouldBe MaterialGroupStorage.group3
    }
  }
}
Aa