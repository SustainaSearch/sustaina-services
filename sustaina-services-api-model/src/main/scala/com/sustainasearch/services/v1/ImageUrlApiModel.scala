package com.sustainasearch.services.v1

import play.api.libs.json.Json

case class ImageUrlApiModel(imageType: String, url: String)

object ImageUrlApiModel {
  implicit val format = Json.format[ImageUrlApiModel]
}