package com.sustainasearch.services

import com.sustainasearch.services.v1.catalog.products.{ProductCategoriesModule, ProductsModule}
import play.api.ApplicationLoader
import play.api.inject.guice.{GuiceApplicationBuilder, GuiceApplicationLoader}
import play.modules.swagger.SwaggerModule

class SustainaServicesApplicationLoader extends GuiceApplicationLoader {

  override def builder(context: ApplicationLoader.Context): GuiceApplicationBuilder = {
    initialBuilder
      .in(context.environment)
      .loadConfig(context.initialConfiguration)
      .overrides(overrides(context): _*)
      .bindings(
        new ProductCategoriesModule,
        new ProductsModule,
        new SwaggerModule
      )
  }
}
