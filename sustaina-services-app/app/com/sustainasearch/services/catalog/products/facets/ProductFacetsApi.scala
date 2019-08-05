package com.sustainasearch.services.catalog.products.facets

import com.sustainasearch.services.catalog.products.{CategoryType, Name, NameApi}
import scalaz.Isomorphism.<=>

object ProductFacetsApi {

  val productFacets = new (ProductFacets <=> ProductFacetsApiModel) {
    val to: ProductFacets => ProductFacetsApiModel = { facets =>
      ProductFacetsApiModel(
        brands = facets.brands.map(brandFacet.to),
        categories = facets.categories.map(categoryFacet.to)
      )
    }
    val from: ProductFacetsApiModel => ProductFacets = { facets =>
      ProductFacets(
        brands = facets.brands.map(brandFacet.from),
        categories = facets.categories.map(categoryFacet.from)
      )
    }
  }

  val brandFacet = new (BrandFacet <=> BrandFacetApiModel) {
    val to: BrandFacet => BrandFacetApiModel = { brandFacet =>
      BrandFacetApiModel(
        brandName = brandFacet.brandName.unparsedName,
        count = brandFacet.count
      )
    }
    val from: BrandFacetApiModel => BrandFacet = { brandFacet =>
      BrandFacet(
        brandName = Name(brandFacet.brandName, None),
        count = brandFacet.count
      )
    }
  }

  val categoryFacet = new (CategoryFacet <=> CategoryFacetApiModel) {
    val to: CategoryFacet => CategoryFacetApiModel = { categoryFacet =>
      CategoryFacetApiModel(
        categoryType = categoryFacet.categoryType.toString,
        names = categoryFacet.names.map(NameApi.name.to),
        count = categoryFacet.count
      )
    }
    val from: CategoryFacetApiModel => CategoryFacet = { categoryFacet =>
      CategoryFacet(
        categoryType = CategoryType.withName(categoryFacet.categoryType),
        names = categoryFacet.names.map(NameApi.name.from),
        count = categoryFacet.count
      )
    }
  }
}
