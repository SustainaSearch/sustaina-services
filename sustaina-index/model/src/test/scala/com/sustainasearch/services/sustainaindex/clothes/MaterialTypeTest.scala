package com.sustainasearch.services.sustainaindex.clothes

import org.scalatest.{Matchers, WordSpec}

class MaterialTypeTest extends WordSpec with Matchers {

  "MaterialType" should {
    "find MaterialType with id" in {
      MaterialType(0) shouldBe MaterialType.Cotton
      MaterialType(1) shouldBe MaterialType.Wool
    }

    "provide score" in {
      MaterialType.Cotton.score shouldBe 17
      MaterialType.Wool.score shouldBe 61
    }
  }
}
