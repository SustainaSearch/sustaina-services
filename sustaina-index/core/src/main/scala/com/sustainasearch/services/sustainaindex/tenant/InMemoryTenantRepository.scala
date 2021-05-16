package com.sustainasearch.services.sustainaindex.tenant

import com.sustainasearch.services.sustainaindex.Tenant
import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext
import scala.language.implicitConversions
import scala.util.Try

@Singleton
class InMemoryTenantRepository @Inject()(implicit
                                         ec: ExecutionContext) extends TenantRepository {

  override def getTenant(id: String): Try[Tenant] = Try(TenantStorage.fromTenantId(id)).map(_.toTenant)

}

private object TenantStorage extends Enumeration {

  case class Val(
                  tenantId: String,
                  host: String
                ) extends super.Val(tenantId) {

    def toTenant: Tenant = Tenant(
      id = tenantId,
      host = host
    )

  }

  implicit def valueToTenantVal(x: Value): TenantStorage.Val = x.asInstanceOf[Val]

  def fromTenantId(id: String): TenantStorage.Val = TenantStorage.withName(id)

  val Tenant1 = Val(
    tenantId = "1",
    host = "host_1"
  )

  val TheWiman = Val(
    tenantId = "547fdb4d-160a-46e9-ac17-54aeb3264389",
    host = "thewiman.com"
  )

}