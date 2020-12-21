package com.sustainasearch.services.v1.sustainaindex

import com.sustainasearch.services.sustainaindex.SustainaIndex
import play.api.libs.json.Json

case class SustainaIndexResponseApiModel(percent: Float, formattedPercent: String)

object SustainaIndexResponseApiModel {
  implicit val format = Json.format[SustainaIndexResponseApiModel]

  def from(sustainaIndex: SustainaIndex): SustainaIndexResponseApiModel = {
    SustainaIndexResponseApiModel(
      percent = sustainaIndex.percent,
      formattedPercent = sustainaIndex.formattedPercent
    )
  }
}