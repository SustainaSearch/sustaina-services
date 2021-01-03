package com.sustainasearch.services.v1.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.SustainaIndex
import com.sustainasearch.services.v1.sustainaindex.SustainaIndexApiModel
import play.api.libs.json.Json

case class SustainaIndexResponseApiModel(request: ItemApiModel, sustainaIndex: SustainaIndexApiModel)

object SustainaIndexResponseApiModel {
  implicit val format = Json.format[SustainaIndexResponseApiModel]

  def from(requestApiModel: ItemApiModel, sustainaIndex: SustainaIndex): SustainaIndexResponseApiModel = {
    SustainaIndexResponseApiModel(
      request = requestApiModel,
      sustainaIndex = SustainaIndexApiModel.from(sustainaIndex)
    )
  }

}