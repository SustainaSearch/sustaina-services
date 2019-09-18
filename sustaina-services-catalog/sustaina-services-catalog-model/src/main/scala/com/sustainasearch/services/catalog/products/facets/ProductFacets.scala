package com.sustainasearch.services.catalog.products.facets

case class ProductFacets(brands: Seq[BrandFacet] = Seq.empty,
                         categories: Seq[CategoryFacet] = Seq.empty) {

  def withBrandFacet(brandFacet: BrandFacet): ProductFacets = copy(brands = brands :+ brandFacet)

  def withCategoryFacet(categoryFacet: CategoryFacet): ProductFacets = copy(categories = categories :+ categoryFacet)

}
