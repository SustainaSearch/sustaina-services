package com.sustainasearch.services.sustainaindex.tenant

import com.sustainasearch.services.sustainaindex.Tenant
import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext
import scala.util.{Success, Try}

@Singleton
class InMemoryTenantRepository @Inject()(implicit
                                         ec: ExecutionContext) extends TenantRepository {

  override def getTenant(id: String): Try[Tenant] = Success(Tenant(id = id, host = s"host_$id"))

}
