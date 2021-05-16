package com.sustainasearch.services.sustainaindex.tenant

import com.sustainasearch.services.sustainaindex.Tenant
import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext
import scala.util.Try

@Singleton
class TenantService @Inject()(tenantRepository: TenantRepository)(implicit
                                                                  ec: ExecutionContext) {

  def isValid(provided: Tenant): Try[Tenant] = {
    tenantRepository
      .getTenant(provided.id)
      .collect { case retrieved if retrieved == provided => retrieved }
  }

}
