package com.sustainasearch.services.v1.models

import bridges.typescript._
import bridges.typescript.syntax._
import com.sustainasearch.services.v1.catalog.CatalogQueryResponseApiModel
import com.sustainasearch.services.v1.catalog.products._
import com.sustainasearch.services.v1.catalog.products.clothes.{ClothesApiModel, CompositionApiModel}
import com.sustainasearch.services.v1.catalog.products.facets.{BrandFacetApiModel, CategoryFacetApiModel, ProductFacetsApiModel}
import com.sustainasearch.services.v1.catalog.products.food.{BabyFoodApiModel, IngredientStatementApiModel}
import com.sustainasearch.services.v1.{ImageApiModel, NameApiModel}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TypescriptService @Inject()()(implicit ec: ExecutionContext) {

  def getModels: Future[String] = {
    Future(
      Typescript.render(
        List(
          decl[ImageApiModel],
          decl[NameApiModel],
          decl[CatalogQueryResponseApiModel],
          decl[CategoryApiModel],
          decl[CityApiModel],
          decl[CountryApiModel],
          decl[ProductActivityApiModel],
          decl[ProductApiModel],
          decl[ProductContainerApiModel],
          decl[ProductQueryResponseApiModel],
          decl[RepresentativePointApiModel],
          decl[SimpleProductApiModel],
          decl[SimpleProductQueryResponseApiModel],
          decl[ClothesApiModel],
          decl[CompositionApiModel],
          decl[BrandFacetApiModel],
          decl[CategoryFacetApiModel],
          decl[ProductFacetsApiModel],
          decl[BabyFoodApiModel],
          decl[IngredientStatementApiModel],
          decl[CategoryFilterApiModel]
        )
      )
    )
  }

}
