package com.sustainasearch.services.v1.catalog.products

import java.util.UUID

import com.sustainasearch.searchengine.QueryResponse
import com.sustainasearch.services.catalog._
import com.sustainasearch.services.catalog.products.facets.ProductFacets
import com.sustainasearch.services.catalog.products.{Product, ProductActivity, SimpleProduct}
import com.sustainasearch.services.v1.NameApi
import com.sustainasearch.services.v1.catalog._
import com.sustainasearch.services.v1.catalog.products.clothes.ClothesApi
import com.sustainasearch.services.v1.catalog.products.facets.ProductFacetsApi
import com.sustainasearch.services.v1.catalog.products.food.BabyFoodApi
import scalaz.Isomorphism.<=>

object ProductsApi {

  val simpleProductQueryResponse = new (QueryResponse[SimpleProduct, ProductFacets] <=> SimpleProductQueryResponseApiModel) {
    val to: QueryResponse[SimpleProduct, ProductFacets] => SimpleProductQueryResponseApiModel = { response =>
      SimpleProductQueryResponseApiModel(
        start = response.start,
        numFound = response.numFound,
        documents = response
          .documents
          .map(simpleProduct.to),
        facets = ProductFacetsApi.productFacets.to(response.facets)
      )
    }
    val from: SimpleProductQueryResponseApiModel => QueryResponse[SimpleProduct, ProductFacets] = { response =>
      QueryResponse[SimpleProduct, ProductFacets](
        start = response.start,
        numFound = response.numFound,
        documents = response
          .documents
          .map(simpleProduct.from)
          .toList,
        facets = ProductFacetsApi.productFacets.from(response.facets)
      )
    }
  }

  val productQueryResponse = new (QueryResponse[Product, ProductFacets] <=> ProductQueryResponseApiModel) {
    val to: QueryResponse[Product, ProductFacets] => ProductQueryResponseApiModel = { response =>
      ProductQueryResponseApiModel(
        start = response.start,
        numFound = response.numFound,
        documents = response
          .documents
          .map(product.to),
        facets = ProductFacetsApi.productFacets.to(response.facets)
      )
    }
    val from: ProductQueryResponseApiModel => QueryResponse[Product, ProductFacets] = { response =>
      QueryResponse[Product, ProductFacets](
        start = response.start,
        numFound = response.numFound,
        documents = response
          .documents
          .map(product.from)
          .toList,
        facets = ProductFacetsApi.productFacets.from(response.facets)
      )
    }
  }

  val simpleProduct = new (SimpleProduct <=> SimpleProductApiModel) {
    val to: SimpleProduct => SimpleProductApiModel = { product =>
      SimpleProductApiModel(
        id = product.id.toString,
        functionalNames = product.functionalNames.map(NameApi.name.to),
        brandName = NameApi.name.to(product.brandName),
        category = category.to(product.category),
        sustainaIndex = product.sustainaIndex
      )
    }
    val from: SimpleProductApiModel => SimpleProduct = { product =>
      SimpleProduct(
        id = UUID.fromString(product.id),
        functionalNames = product.functionalNames.map(NameApi.name.from),
        brandName = NameApi.name.from(product.brandName),
        category = category.from(product.category),
        sustainaIndex = product.sustainaIndex
      )
    }
  }

  val product = new (Product <=> ProductApiModel) {
    val to: Product => ProductApiModel = { product =>
      ProductApiModel(
        id = product.id.toString,
        productActivity = productActivity.to(product.productActivity),
        functionalNames = product.functionalNames.map(NameApi.name.to),
        brandName = NameApi.name.to(product.brandName),
        category = category.to(product.category),
        sustainaIndex = product.sustainaIndex,
        babyFood = product.maybeBabyFood.map(BabyFoodApi.babyFood.to),
        clothes = product.maybeClothes.map(ClothesApi.clothes.to)
      )
    }
    val from: ProductApiModel => Product = { product =>
      Product(
        id = UUID.fromString(product.id),
        productActivity = productActivity.from(product.productActivity),
        functionalNames = product.functionalNames.map(NameApi.name.from),
        brandName = NameApi.name.from(product.brandName),
        category = category.from(product.category),
        sustainaIndex = product.sustainaIndex,
        maybeBabyFood = product.babyFood.map(BabyFoodApi.babyFood.from),
        maybeClothes = product.clothes.map(ClothesApi.clothes.from)
      )
    }
  }

  val productActivity = new (ProductActivity <=> ProductActivityApiModel) {
    val to: ProductActivity => ProductActivityApiModel = { productActivity =>
      ProductActivityApiModel(
        country = country.to(productActivity.country),
        city = productActivity.city.map(city.to),
        representativePoint = representativePoint.to(productActivity.representativePoint)
      )
    }
    val from: ProductActivityApiModel => ProductActivity = { productActivity =>
      ProductActivity(
        country = country.from(productActivity.country),
        city = productActivity.city.map(city.from),
        representativePoint = representativePoint.from(productActivity.representativePoint)
      )
    }
  }

  val country = new (Country <=> CountryApiModel) {
    val to: Country => CountryApiModel = { country =>
      CountryApiModel(
        countryCode = country.countryCode.toString,
        names = country.names.map(NameApi.name.to)
      )
    }
    val from: CountryApiModel => Country = { country =>
      Country(
        countryCode = CountryCode.withName(country.countryCode),
        names = country.names.map(NameApi.name.from)
      )
    }
  }

  val city = new (City <=> CityApiModel) {
    val to: City => CityApiModel = { city =>
      CityApiModel(
        names = city.names.map(NameApi.name.to)
      )
    }
    val from: CityApiModel => City = { city =>
      City(
        names = city.names.map(NameApi.name.from)
      )
    }
  }

  val representativePoint = new (RepresentativePoint <=> RepresentativePointApiModel) {
    val to: RepresentativePoint => RepresentativePointApiModel = { representativePoint =>
      RepresentativePointApiModel(
        latitude = representativePoint.latitude,
        longitude = representativePoint.longitude
      )
    }
    val from: RepresentativePointApiModel => RepresentativePoint = { representativePoint =>
      RepresentativePoint(
        latitude = representativePoint.latitude,
        longitude = representativePoint.longitude
      )
    }
  }

  val category = new (Category <=> CategoryApiModel) {
    val to: Category => CategoryApiModel = { category =>
      CategoryApiModel(
        categoryType = category.categoryType.toString,
        names = category.names.map(NameApi.name.to)
      )
    }
    val from: CategoryApiModel => Category = { category =>
      Category(
        categoryType = CategoryType.withName(category.categoryType),
        names = category.names.map(NameApi.name.from)
      )
    }
  }

}
