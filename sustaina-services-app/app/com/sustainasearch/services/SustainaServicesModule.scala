package com.sustainasearch.services

import play.api.inject.{Binding, Module}
import play.api.{Configuration, Environment}

class SustainaServicesModule extends Module {
  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    Seq(
      //      bind[CatalogService2].toInstance(new CatalogService2())
    )
  }
}
