package com.sustainasearch.services.v1.catalog.products

import java.util.UUID

import com.sustainasearch.searchengine.QueryResponse
import com.sustainasearch.services.catalog._
import com.sustainasearch.services.catalog.products._
import com.sustainasearch.services.catalog.products.facets.ProductFacets
import com.sustainasearch.services.v1.catalog.products.clothes.ClothesIsomorphism
import com.sustainasearch.services.v1.catalog.products.facets.ProductFacetsIsomorphism
import com.sustainasearch.services.v1.catalog.products.food.BabyFoodIsomorphism
import com.sustainasearch.services.v1._
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

  val productCatalogQueryResponse = new (ProductCategoryQueryResponse <=> ProductCategoryQueryResponseApiModel) {
    override def to: ProductCategoryQueryResponse => ProductCategoryQueryResponseApiModel = { response =>
      ProductCategoryQueryResponseApiModel(
        productQueryResponse = productQueryResponse.to(response.productQueryResponse),
        filterQueries = response.filterQueries.map(FilterQueryContainerIsomorphism.filterQueryContainer.to)
      )
    }

    override def from: ProductCategoryQueryResponseApiModel => ProductCategoryQueryResponse = { response =>
      ProductCategoryQueryResponse(
        productQueryResponse = productQueryResponse.from(response.productQueryResponse),
        filterQueries = response.filterQueries.map(FilterQueryContainerIsomorphism.filterQueryContainer.from)
      )
    }
  }



  val simpleProduct = new (SimpleProduct <=> SimpleProductApiModel) {
    val to: SimpleProduct => SimpleProductApiModel = { product =>
      SimpleProductApiModel(
        id = productId.to(product.id),
        functionalNames = product.functionalNames.map(NameIsomorphism.name.to),
        brandName = NameIsomorphism.name.to(product.brandName),
        category = CategoryIsomorphism.category.to(product.category),
        sustainaIndex = product.sustainaIndex
      )
    }
    val from: SimpleProductApiModel => SimpleProduct = { product =>
      SimpleProduct(
        id = productId.from(product.id),
        functionalNames = product.functionalNames.map(NameIsomorphism.name.from),
        brandName = NameIsomorphism.name.from(product.brandName),
        category = CategoryIsomorphism.category.from(product.category),
        sustainaIndex = product.sustainaIndex
      )
    }
  }

  val productId = new (UUID <=> String) {
    override def to: UUID => String = { id =>
      id.toString
    }

    override def from: String => UUID = { id =>
      UUID.fromString(id)
    }
  }

  val product = new (Product <=> ProductApiModel) {
    val to: Product => ProductApiModel = { product =>
      ProductApiModel(
        id = productId.to(product.id),
        productActivity = productActivity.to(product.productActivity),
        functionalNames = product.functionalNames.map(NameIsomorphism.name.to),
        brandName = NameIsomorphism.name.to(product.brandName),
        category = CategoryIsomorphism.category.to(product.category),
        images = product.images.map(ImageIsomorphism.image.to),
        sustainaIndex = product.sustainaIndex,
        babyFood = product.maybeBabyFood.map(BabyFoodIsomorphism.babyFood.to),
        clothes = product.maybeClothes.map(ClothesIsomorphism.clothes.to)
      )
    }
    val from: ProductApiModel => Product = { product =>
      Product(
        id = productId.from(product.id),
        productActivity = productActivity.from(product.productActivity),
        functionalNames = product.functionalNames.map(NameIsomorphism.name.from),
        brandName = NameIsomorphism.name.from(product.brandName),
        category = CategoryIsomorphism.category.from(product.category),
        images = product.images.map(ImageIsomorphism.image.from),
        sustainaIndex = product.sustainaIndex,
        maybeBabyFood = product.babyFood.map(BabyFoodIsomorphism.babyFood.from),
        maybeClothes = product.clothes.map(ClothesIsomorphism.clothes.from)
      )
    }
  }

  val productContainer = new (ProductContainer <=> ProductContainerApiModel) {
    override def to: ProductContainer => ProductContainerApiModel = { container =>
      ProductContainerApiModel(
        product = product.to(container.product)
      )
    }

    override def from: ProductContainerApiModel => ProductContainer = { container =>
      ProductContainer(
        product = product.from(container.product)
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

}
