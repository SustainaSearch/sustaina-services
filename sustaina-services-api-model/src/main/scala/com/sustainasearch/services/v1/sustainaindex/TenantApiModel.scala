package com.sustainasearch.services.v1.sustainaindex

import com.sustainasearch.services.sustainaindex.Tenant
import play.api.libs.json.{JsError, JsPath, JsSuccess, Json}

import scala.util.{Failure, Success, Try}

case class TenantApiModel(tid: String,
                          thost: String) {

  def toTenant: Tenant = Tenant(
    id = tid,
    host = thost
  )
}

object TenantApiModel {
  implicit val format = Json.format[TenantApiModel]

  implicit val reads = Json.reads[TenantApiModel]

  def fromJson(json: String): Try[TenantApiModel] = {
    Json.fromJson[TenantApiModel](Json.parse(json)) match {
      case JsSuccess(t: TenantApiModel, _: JsPath) => Success(t)
      case e @ JsError(_) => Failure(throw new IllegalArgumentException(s"Not a Tenant in $json"))
    }
  }

}
