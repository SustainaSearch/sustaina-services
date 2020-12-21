package com.sustainasearch.services.v1.sustainaindex

import com.sustainasearch.services.sustainaindex.Brand
import play.api.libs.json.Json

case class BrandApiModel(id: Int) {

  def toBrand: Brand.Brand = Brand.withId(id)

}

object BrandApiModel {
  implicit val format = Json.format[BrandApiModel]
}
