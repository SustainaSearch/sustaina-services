package com.sustainasearch.services.catalog.products

import com.sustainasearch.searchengine.solr.http.HttpSolrConfig
import play.api.inject.{Binding, Module}
import play.api.{Configuration, Environment}

class ProductsModule extends Module {
  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    Seq(
      bind[ProductSearchEngineFieldRegister].toInstance(ProductSolrFieldRegister),
      // TODO: get Solr URL from configuration
      bind[ProductSearchEngineFactory].toInstance(new HttpSolrProductSearchEngineFactory(HttpSolrConfig("http://localhost:8983/solr/sustaina-products")))
    )
  }
}