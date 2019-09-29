package com.sustainasearch.services.v1.catalog.products

import com.sustainasearch.searchengine.solr.http.HttpSolrConfig
import com.sustainasearch.services.catalog.products.{HttpSolrProductSearchEngineFactory, ProductSearchEngineFactory, ProductSearchEngineFieldRegister, ProductSolrFieldRegister}
import play.api.inject.{Binding, Module}
import play.api.{Configuration, Environment}

class ProductsModule extends Module {
  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    val productsSolrBaseUrl = configuration
      .getOptional[String]("products.solr.url")
      .getOrElse("http://localhost:8983")
    println(s"Products Solr base URL = '$productsSolrBaseUrl'")

    val productsSolrUrl = s"$productsSolrBaseUrl/solr/sustaina-products"
    println(s"Products Solr URL = '$productsSolrUrl'")

    Seq(
      bind[ProductSearchEngineFieldRegister].toInstance(ProductSolrFieldRegister),
      bind[ProductSearchEngineFactory].toInstance(new HttpSolrProductSearchEngineFactory(HttpSolrConfig(productsSolrUrl)))
    )
  }
}