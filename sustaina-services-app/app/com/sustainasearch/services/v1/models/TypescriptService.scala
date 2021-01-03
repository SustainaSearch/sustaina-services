package com.sustainasearch.services.v1.models

import bridges.typescript._
import bridges.typescript.syntax._
import com.sustainasearch.services.v1.sustainaindex.brand.BrandApiModel
import com.sustainasearch.services.v1.sustainaindex.clothes.ItemApiModel
import com.sustainasearch.services.v1.sustainaindex.clothes.material.MaterialApiModel
import com.sustainasearch.services.v1.sustainaindex.{SustainaIndexResponseApiModel, TenantApiModel}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TypescriptService @Inject()()(implicit ec: ExecutionContext) {

  def getModels: Future[String] = {
    Future(
      Typescript.render(
        List(
          decl[SustainaIndexResponseApiModel],
          decl[TenantApiModel],
          decl[ItemApiModel],
          decl[MaterialApiModel],
          decl[BrandApiModel]

          //          decl[ImageApiModel],
          //          decl[NameApiModel],
          //          decl[CatalogQueryResponseApiModel],
          //          decl[CategoryApiModel],
          //          decl[CityApiModel],
          //          decl[CountryApiModel],
          //          decl[ProductActivityApiModel],
          //          decl[ProductApiModel],
          //          decl[ProductContainerApiModel],
          //          decl[ProductQueryResponseApiModel],
          //          decl[RepresentativePointApiModel],
          //          decl[SimpleProductApiModel],
          //          decl[SimpleProductQueryResponseApiModel],
          //          decl[ClothesApiModel],
          //          decl[CompositionApiModel],
          //          decl[BrandFacetApiModel],
          //          decl[CategoryFacetApiModel],
          //          decl[ProductFacetsApiModel],
          //          decl[BabyFoodApiModel],
          //          decl[IngredientStatementApiModel],
          //          decl[CategoryFilterApiModel],
          //          decl[TextFilterQueryApiModel],
          //          decl[BooleanFilterQueryApiModel],
          //          decl[RangeFilterQueryApiModel],
          //          decl[FilterQueryApiModel],
          //          decl[FilterQueryContainerApiModel]
        )
      )
    )
  }

}
