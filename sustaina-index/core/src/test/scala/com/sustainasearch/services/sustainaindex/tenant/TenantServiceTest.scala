package com.sustainasearch.services.sustainaindex.tenant

import com.sustainasearch.services.sustainaindex.Tenant
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

class TenantServiceTest extends WordSpec with Matchers {

  classOf[TenantService].getSimpleName should {
    val underTest = new TenantService(new InMemoryTenantRepository)

    "return provided Tenant if it is valid" in {
      val provided = Tenant(id = "1", host = "host_1")
      underTest.isValid(provided) shouldBe Success(provided)
    }

    "return a failure if provided Tenant is not valid" in {
      val provided = Tenant(id = "1", host = "invalid")
      underTest.isValid(provided).isFailure shouldBe true
    }
  }

}