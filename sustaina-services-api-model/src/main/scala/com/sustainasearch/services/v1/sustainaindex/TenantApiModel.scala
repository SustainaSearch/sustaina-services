package com.sustainasearch.services.v1.sustainaindex

import com.sustainasearch.services.sustainaindex.Tenant
import play.api.libs.json.Json

case class TenantApiModel(id: String,
                          host: String) {

  def toTenant: Tenant = Tenant(
    id = id,
    host = host
  )
}

object TenantApiModel {
  implicit val format = Json.format[TenantApiModel]
}
