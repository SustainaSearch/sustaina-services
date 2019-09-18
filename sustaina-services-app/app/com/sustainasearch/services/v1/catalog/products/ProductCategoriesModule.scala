package com.sustainasearch.services.v1.catalog.products

import com.sustainasearch.searchengine.solr.http.HttpSolrConfig
import com.sustainasearch.services.catalog.products.{HttpSolrProductCategorySearchEngineFactory, ProductCategorySearchEngineFactory, ProductCategorySearchEngineFieldRegister, ProductCategorySolrFieldRegister}
import play.api.inject.{Binding, Module}
import play.api.{Configuration, Environment}

class ProductCategoriesModule extends Module {
  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    val productsSolrBaseUrl = configuration.getString("product-categories.solr.url").getOrElse(throw new IllegalArgumentException("No 'product-categories.solr.url' has been provided"))
    println(s"Product categories Solr base URL = '$productsSolrBaseUrl'")
    val productCategoriesSolrUrl = s"$productsSolrBaseUrl/solr/sustaina-categories"
    println(s"Product categories Solr URL = '$productCategoriesSolrUrl'")
    Seq(
      bind[ProductCategorySearchEngineFieldRegister].toInstance(ProductCategorySolrFieldRegister),
      bind[ProductCategorySearchEngineFactory].toInstance(new HttpSolrProductCategorySearchEngineFactory(HttpSolrConfig(productCategoriesSolrUrl)))
    )
  }
}