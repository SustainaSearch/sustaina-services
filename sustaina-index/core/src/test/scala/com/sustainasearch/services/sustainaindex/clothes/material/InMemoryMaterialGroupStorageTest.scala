package com.sustainasearch.services.sustainaindex.clothes.material

import org.scalatest.{Matchers, WordSpec}

class InMemoryMaterialGroupStorageTest extends WordSpec with Matchers {

  "InMemoryMaterialGroupStorage" should {
    "find MaterialGroupStorage.Val with id" in {
      MaterialGroupStorage(0) shouldBe MaterialGroupStorage.group0
      MaterialGroupStorage(1) shouldBe MaterialGroupStorage.group1
      MaterialGroupStorage(2) shouldBe MaterialGroupStorage.group2
    }

    //"provide score" in {
     // MaterialGroupStorage.group0.score shouldBe 0
      //MaterialGroupStorage.group1.score shouldBe 1
      //MaterialGroupStorage.group2.score shouldBe 4
      //MaterialGroupStorage.group3.score shouldBe 9
      //MaterialGroupStorage.group4.score shouldBe 16
      //MaterialGroupStorage.group5.score shouldBe 25
      //MaterialGroupStorage.group6.score shouldBe 36
      //MaterialGroupStorage.group7.score shouldBe 49
      //MaterialGroupStorage.group8.score shouldBe 64
      //MaterialGroupStorage.group9.score shouldBe 81
      //MaterialGroupStorage.group10.score shouldBe 100
   // }
  }
}
