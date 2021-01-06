package com.sustainasearch.services.v1.sustainaindex

import com.sustainasearch.services.sustainaindex.brand.{BrandRepository, InMemoryBrandRepository}
import com.sustainasearch.services.sustainaindex.certification.{CertificationRepository, InMemoryCertificationRepository}
import com.sustainasearch.services.sustainaindex.clothes.material.{InMemoryMaterialGroupRepository, InMemoryMaterialTypeRepository, MaterialGroupRepository, MaterialTypeRepository}
import com.sustainasearch.services.sustainaindex.country.{CountryRepository, InMemoryCountryRepository}
import com.sustainasearch.services.sustainaindex.tenant.{InMemoryTenantRepository, TenantRepository}
import play.api.inject.{Binding, Module}
import play.api.{Configuration, Environment}

class InMemorySustainaIndexModule extends Module {

  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    Seq(
      bind[BrandRepository].to(classOf[InMemoryBrandRepository]),
      bind[CertificationRepository].to(classOf[InMemoryCertificationRepository]),
      bind[MaterialGroupRepository].to(classOf[InMemoryMaterialGroupRepository]),
      bind[MaterialTypeRepository].to(classOf[InMemoryMaterialTypeRepository]),
      bind[CountryRepository].to(classOf[InMemoryCountryRepository]),
      bind[TenantRepository].to(classOf[InMemoryTenantRepository])
    )
  }

}
