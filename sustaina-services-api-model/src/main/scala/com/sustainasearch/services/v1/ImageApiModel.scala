package com.sustainasearch.services.v1

import play.api.libs.json.Json

case class ImageApiModel(imageType: String, url: String)

object ImageApiModel {
  implicit val format = Json.format[ImageApiModel]
}
