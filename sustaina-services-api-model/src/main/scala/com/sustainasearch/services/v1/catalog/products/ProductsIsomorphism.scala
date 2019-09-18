package com.sustainasearch.services.v1.catalog.products

import java.util.UUID

import com.sustainasearch.searchengine.QueryResponse
import com.sustainasearch.services.catalog._
import com.sustainasearch.services.catalog.products.facets.ProductFacets
import com.sustainasearch.services.catalog.products._
import com.sustainasearch.services.v1.NameIsomorphism
import com.sustainasearch.services.v1.catalog.products.clothes.ClothesIsomorphism
import com.sustainasearch.services.v1.catalog.products.facets.ProductFacetsIsomorphism
import com.sustainasearch.services.v1.catalog.products.food.BabyFoodIsomorphism
import scalaz.Isomorphism.<=>

object ProductsIsomorphism {

  val simpleProductQueryResponse = new (QueryResponse[SimpleProduct, ProductFacets] <=> SimpleProductQueryResponseApiModel) {
    val to: QueryResponse[SimpleProduct, ProductFacets] => SimpleProductQueryResponseApiModel = { response =>
      SimpleProductQueryResponseApiModel(
        start = response.start,
        numFound = response.numFound,
        documents = response
          .documents
          .map(simpleProduct.to),
        facets = ProductFacetsIsomorphism.productFacets.to(response.facets)
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
        facets = ProductFacetsIsomorphism.productFacets.from(response.facets)
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
        facets = ProductFacetsIsomorphism.productFacets.to(response.facets)
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
        facets = ProductFacetsIsomorphism.productFacets.from(response.facets)
      )
    }
  }

  val simpleProduct = new (SimpleProduct <=> SimpleProductApiModel) {
    val to: SimpleProduct => SimpleProductApiModel = { product =>
      SimpleProductApiModel(
        id = product.id.toString,
        functionalNames = product.functionalNames.map(NameIsomorphism.name.to),
        brandName = NameIsomorphism.name.to(product.brandName),
        category = category.to(product.category),
        sustainaIndex = product.sustainaIndex
      )
    }
    val from: SimpleProductApiModel => SimpleProduct = { product =>
      SimpleProduct(
        id = UUID.fromString(product.id),
        functionalNames = product.functionalNames.map(NameIsomorphism.name.from),
        brandName = NameIsomorphism.name.from(product.brandName),
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
        functionalNames = product.functionalNames.map(NameIsomorphism.name.to),
        brandName = NameIsomorphism.name.to(product.brandName),
        category = category.to(product.category),
        sustainaIndex = product.sustainaIndex,
        babyFood = product.maybeBabyFood.map(BabyFoodIsomorphism.babyFood.to),
        clothes = product.maybeClothes.map(ClothesIsomorphism.clothes.to)
      )
    }
    val from: ProductApiModel => Product = { product =>
      Product(
        id = UUID.fromString(product.id),
        productActivity = productActivity.from(product.productActivity),
        functionalNames = product.functionalNames.map(NameIsomorphism.name.from),
        brandName = NameIsomorphism.name.from(product.brandName),
        category = category.from(product.category),
        sustainaIndex = product.sustainaIndex,
        maybeBabyFood = product.babyFood.map(BabyFoodIsomorphism.babyFood.from),
        maybeClothes = product.clothes.map(ClothesIsomorphism.clothes.from)
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
        names = country.names.map(NameIsomorphism.name.to)
      )
    }
    val from: CountryApiModel => Country = { country =>
      Country(
        countryCode = CountryCode.withName(country.countryCode),
        names = country.names.map(NameIsomorphism.name.from)
      )
    }
  }

  val city = new (City <=> CityApiModel) {
    val to: City => CityApiModel = { city =>
      CityApiModel(
        names = city.names.map(NameIsomorphism.name.to)
      )
    }
    val from: CityApiModel => City = { city =>
      City(
        names = city.names.map(NameIsomorphism.name.from)
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
        names = category.names.map(NameIsomorphism.name.to)
      )
    }
    val from: CategoryApiModel => Category = { category =>
      Category(
        categoryType = CategoryType.withName(category.categoryType),
        names = category.names.map(NameIsomorphism.name.from)
      )
    }
  }

}
