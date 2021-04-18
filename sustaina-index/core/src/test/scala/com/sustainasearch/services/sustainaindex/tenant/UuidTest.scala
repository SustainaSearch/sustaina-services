package com.sustainasearch.services.sustainaindex.tenant

import java.util.UUID

import org.scalatest.WordSpec

class UuidTest extends WordSpec {

  "UUID generator" should {
    "generate UUID" in {
      println(UUID.randomUUID())
    }
  }

}
