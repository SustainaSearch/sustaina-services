package com.sustainasearch.services.catalog.products

import java.util.UUID

import com.sustainasearch.searchengine.QueryResponse
import com.sustainasearch.services.catalog.products.clothes.ClothesApi
import com.sustainasearch.services.catalog.products.facets.{ProductFacets, ProductFacetsApi}
import com.sustainasearch.services.catalog.products.food._
import scalaz.Isomorphism.<=>

object ProductsApi {

  val queryResponse = new (QueryResponse[Product, ProductFacets] <=> ProductQueryResponseApiModel) {
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

  val product = new (Product <=> ProductApiModel) {
    val to: Product => ProductApiModel = { product =>
      ProductApiModel(
        id = product.id.toString,
        representativePoint = representativePoint.to(product.representativePoint),
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
        representativePoint = representativePoint.from(product.representativePoint),
        functionalNames = product.functionalNames.map(NameApi.name.from),
        brandName = NameApi.name.from(product.brandName),
        category = category.from(product.category),
        sustainaIndex = product.sustainaIndex,
        maybeBabyFood = product.babyFood.map(BabyFoodApi.babyFood.from),
        maybeClothes = product.clothes.map(ClothesApi.clothes.from)
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
