package com.sustainasearch.services.v1.catalog.products.facets

import com.sustainasearch.services.Name
import com.sustainasearch.services.catalog.products.CategoryType
import com.sustainasearch.services.catalog.products.facets._
import com.sustainasearch.services.v1.NameIsomorphism
import scalaz.Isomorphism.<=>

object ProductFacetsIsomorphism {

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
        names = categoryFacet.names.map(NameIsomorphism.name.to),
        count = categoryFacet.count
      )
    }
    val from: CategoryFacetApiModel => CategoryFacet = { categoryFacet =>
      CategoryFacet(
        categoryType = CategoryType.withName(categoryFacet.categoryType),
        names = categoryFacet.names.map(NameIsomorphism.name.from),
        count = categoryFacet.count
      )
    }
  }
}
