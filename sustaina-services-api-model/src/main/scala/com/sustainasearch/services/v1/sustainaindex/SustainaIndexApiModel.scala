package com.sustainasearch.services.v1.sustainaindex

import com.sustainasearch.services.sustainaindex.SustainaIndex
import play.api.libs.json.Json

case class SustainaIndexApiModel(percent: Float,
                                 material: Float,
                                 process: Float,
                                 quality: Float,
                                 workingConditions: Float,
                                 packaging: Float,
                                 energy: Float,
                                 crc: Float,
                                 formattedPercent: String)

object SustainaIndexApiModel {
  implicit val format = Json.format[SustainaIndexApiModel]

  def from(sustainaIndex: SustainaIndex): SustainaIndexApiModel = {
    SustainaIndexApiModel(
      percent = sustainaIndex.percent,
      material = sustainaIndex.material,
      process = sustainaIndex.process,
      quality = sustainaIndex.quality,
      workingConditions = sustainaIndex.workingConditions,
      packaging = sustainaIndex.packaging,
      energy = sustainaIndex.energy,
      crc = sustainaIndex.crc,
      formattedPercent = sustainaIndex.formattedPercent
    )
  }
}
