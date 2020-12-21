package com.sustainasearch.services.v1.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.clothes.Item
import com.sustainasearch.services.sustainaindex.{Certification, Country}
import com.sustainasearch.services.v1.sustainaindex.BrandApiModel
import play.api.libs.json.Json

case class ItemApiModel(id: String,
                        countryCode: String,
                        certifications: Seq[Int],
                        materials: Seq[MaterialApiModel],
                        garmentWeight: Option[Float],
                        brand: Option[BrandApiModel]) {

  def toItem: Item = Item(
    id = id,
    country = Country.withCountryCode(countryCode),
    certifications = certifications.map(Certification.withId),
    materials = materials.map(_.toMaterial),
    garmentWeight = garmentWeight,
    brand = brand.map(_.toBrand)
  )

}

object ItemApiModel {
  implicit val format = Json.format[ItemApiModel]
}