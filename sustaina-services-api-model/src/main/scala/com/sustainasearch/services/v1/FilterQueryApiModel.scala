package com.sustainasearch.services.v1

import play.api.libs.json.Json

case class TextFilterQueryApiModel(fieldName: String, fieldValue: String)

object TextFilterQueryApiModel {
  implicit val format = Json.format[TextFilterQueryApiModel]
}

case class BooleanFilterQueryApiModel(fieldName: String, fieldValue: Boolean)

object BooleanFilterQueryApiModel {
  implicit val format = Json.format[BooleanFilterQueryApiModel]
}

case class RangeFilterQueryApiModel(fieldName: String, from: Long, to: Long)

object RangeFilterQueryApiModel {
  implicit val format = Json.format[RangeFilterQueryApiModel]
}

case class FilterQueryApiModel(text: Option[TextFilterQueryApiModel],
                               boolean: Option[BooleanFilterQueryApiModel],
                               range: Option[RangeFilterQueryApiModel])

object FilterQueryApiModel {
  implicit val format = Json.format[FilterQueryApiModel]
}
