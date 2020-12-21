package com.sustainasearch.services.v1.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.clothes.{Material, MaterialType}
import play.api.libs.json.Json

case class MaterialApiModel(materialType: Int,
                            percent: Float) {

  def toMaterial: Material = Material(
    materialType = MaterialType(materialType),
    percent = percent
  )

}

object MaterialApiModel {
  implicit val format = Json.format[MaterialApiModel]
}