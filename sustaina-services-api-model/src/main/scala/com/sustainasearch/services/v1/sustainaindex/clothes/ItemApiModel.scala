package com.sustainasearch.services.v1.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.clothes.{Item, Material}
import com.sustainasearch.services.sustainaindex.{Certification, Country}
import com.sustainasearch.services.v1.sustainaindex.BrandApiModel
import play.api.libs.json.Json

case class ItemApiModel(id: String,
                        countryCode: Option[String],
                        certifications: Option[Seq[Int]],
                        materials: Option[Seq[MaterialApiModel]],
                        garmentWeight: Option[Float],
                        brand: Option[BrandApiModel]) {

  def toItem: Item = Item(
    id = id,
    country = countryCode.map(Country.withCountryCode),
    certifications = certifications.fold(Seq.empty[Certification.Certification])(_.map(Certification.withId)),
    materials = materials.fold(Seq.empty[Material])(_.map(_.toMaterial)),
    garmentWeight = garmentWeight,
    brand = brand.map(_.toBrand)
  )

}

object ItemApiModel {
  implicit val format = Json.format[ItemApiModel]
}