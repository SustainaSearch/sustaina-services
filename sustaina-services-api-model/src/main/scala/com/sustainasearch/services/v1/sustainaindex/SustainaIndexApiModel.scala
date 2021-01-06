package com.sustainasearch.services.v1.sustainaindex

import com.sustainasearch.services.sustainaindex.SustainaIndex
import play.api.libs.json.Json

case class SustainaIndexApiModel(percent: Float, formattedPercent: String)

object SustainaIndexApiModel {
  implicit val format = Json.format[SustainaIndexApiModel]

  def from(sustainaIndex: SustainaIndex): SustainaIndexApiModel = {
    SustainaIndexApiModel(
      percent = sustainaIndex.percent,
      formattedPercent = sustainaIndex.formattedPercent
    )
  }
}
