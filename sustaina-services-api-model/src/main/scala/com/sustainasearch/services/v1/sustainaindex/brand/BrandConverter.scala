package com.sustainasearch.services.v1.sustainaindex.brand

import com.sustainasearch.services.sustainaindex.brand.{Brand, BrandRepository, BrandService}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

@Singleton
class BrandConverter @Inject()(brandService: BrandService)(implicit ec: ExecutionContext) {

  def convertToBrand(apiModel: Option[BrandApiModel]): Future[Try[Option[Brand]]] = {
    apiModel.fold[Future[Try[Option[Brand]]]](Future.successful(Success(None))) { b =>
      brandService
        .getBrand(b.id)
        .map { tBrand =>
          tBrand.map(Some(_))
        }
    }
  }

}
