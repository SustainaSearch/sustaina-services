package com.sustainasearch.services.v1.auth

import java.time.Clock

import com.sustainasearch.services.sustainaindex.tenant.{InMemoryTenantRepository, TenantRepository}
import play.api.inject.{Binding, Module}
import play.api.{Configuration, Environment}

class AuthModule extends Module {

  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    Seq(
      bind[Clock].toInstance(Clock.systemUTC()),
      bind[PublicKeyProvider].to[ConfigPublicKeyProvider],
      bind[TenantRepository].to[InMemoryTenantRepository]
    )
  }

}