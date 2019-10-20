package com.sustainasearch.services.v1

import play.api.libs.json.Json

case class FilterQueryContainerApiModel(names: Seq[NameApiModel], filterQuery: FilterQueryApiModel)

object FilterQueryContainerApiModel {
  implicit val format = Json.format[FilterQueryContainerApiModel]
}
