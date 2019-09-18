package com.sustainasearch.services.v1

import play.api.libs.json.Json

case class NameApiModel(unparsedName: String, languageCode: Option[String])

object NameApiModel {
  implicit val format = Json.format[NameApiModel]
}