package com.sustainasearch.services

//import com.sustainasearch.services.catalog2.CatalogService2
import play.api.inject.{Binding, Module}
import play.api.{Configuration, Environment}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

class SustainaServicesModule extends Module {
  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    Seq(
//      bind[CatalogService2].toInstance(new CatalogService2())
    )
  }
}
