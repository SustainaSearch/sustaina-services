package com.sustainasearch.services.v1.models

import bridges.flow._
import bridges.flow.syntax._
import com.sustainasearch.services.v1.sustainaindex.{BrandApiModel, SustainaIndexResponseApiModel, TenantApiModel}
import com.sustainasearch.services.v1.sustainaindex.clothes.{ClothesSustainaIndexRequestApiModel, ItemApiModel, MaterialApiModel}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FlowTypeService @Inject()()(implicit ec: ExecutionContext) {

  def getModels: Future[String] = {
    Future(
      Flow.render(
        List(
          decl[ClothesSustainaIndexRequestApiModel],
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
