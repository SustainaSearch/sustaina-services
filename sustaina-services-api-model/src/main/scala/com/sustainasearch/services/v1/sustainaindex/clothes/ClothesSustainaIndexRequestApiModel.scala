package com.sustainasearch.services.v1.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.clothes.SustainaIndexInput
import com.sustainasearch.services.v1.sustainaindex.TenantApiModel
import play.api.libs.json.Json

case class ClothesSustainaIndexRequestApiModel(tenant: TenantApiModel,
                                               item: ItemApiModel) {

  def toInput: SustainaIndexInput = SustainaIndexInput(
    tenant = tenant.toTenant,
    item = item.toItem
  )

}

object ClothesSustainaIndexRequestApiModel {
  implicit val format = Json.format[ClothesSustainaIndexRequestApiModel]
}