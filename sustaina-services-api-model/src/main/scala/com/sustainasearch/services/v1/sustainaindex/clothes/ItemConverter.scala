package com.sustainasearch.services.v1.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.certification.{Certification, CertificationService}
import com.sustainasearch.services.sustainaindex.clothes.Item
import com.sustainasearch.services.sustainaindex.clothes.material.Material
import com.sustainasearch.services.sustainaindex.country.CountryService
import com.sustainasearch.services.v1.sustainaindex.brand.BrandConverter
import com.sustainasearch.services.v1.sustainaindex.clothes.material.MaterialConverter
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class ItemConverter @Inject()(countryService: CountryService,
                              certificationService: CertificationService,
                              materialConverter: MaterialConverter,
                              brandConverter: BrandConverter)(implicit
                                                              ec: ExecutionContext) {

  def convertToItem(apiModel: ItemApiModel): Future[Try[Item]] = {
    val fCountry = countryService.getCountry(apiModel.countryCode)
    val fCertifications = apiModel
      .certifications
      .fold(Future.successful(Seq.empty[Try[Certification]])) { certs =>
        Future.sequence(certs.map(certificationService.getCertification))
      }
      .map(sequence)
    val fMaterials = apiModel
      .materials
      .fold(Future.successful(Seq.empty[Try[Material]])) { materials =>
        val materialsF = materials.map(materialConverter.convertToMaterial)
        Future.sequence(materialsF)
      }
      .map(sequence)
    val fBrand = brandConverter.convertToBrand(apiModel.brand)

    for {
      tCountry <- fCountry
      tCertifications <- fCertifications
      tMaterials <- fMaterials
      tBrand <- fBrand
    } yield {
      for {
        country <- tCountry
        certifications <- tCertifications
        materials <- tMaterials
        brand <- tBrand
      } yield Item(
        id = apiModel.id,
        country = country,
        certifications = certifications,
        materials = materials,
        brand = brand
      )

    }
  }

  //  private def sequence[T](xs: Seq[Try[T]]): Try[Seq[T]] = (Try(Seq[T]()) /: xs) {
  //    (a, b) => a.flatMap(c => b map (d => c :+ d))
  //  }

  private def sequence[T](xs: Seq[Try[T]]): Try[Seq[T]] = {
    xs.foldLeft(Try(Seq[T]())) { (triedResult, triedNext) =>
      triedResult.flatMap { result =>
        triedNext.map(next => result :+ next)
      }
    }
  }

}
