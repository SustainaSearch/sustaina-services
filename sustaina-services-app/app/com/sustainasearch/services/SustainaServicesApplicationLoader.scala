package com.sustainasearch.services

import play.api.ApplicationLoader
import play.api.inject.guice.{GuiceApplicationBuilder, GuiceApplicationLoader}
import play.modules.swagger.SwaggerModule

class SustainaServicesApplicationLoader extends GuiceApplicationLoader {

  override def builder(context: ApplicationLoader.Context): GuiceApplicationBuilder = {
    initialBuilder
      .in(context.environment)
      .loadConfig(context.initialConfiguration)
      .overrides(overrides(context): _*)
//      .bindings(
//        new SustainaServicesModule,
//        new SwaggerModule
//      )
  }
}
