package com.sustainasearch.services.sustainaindex.tenant

import com.sustainasearch.services.sustainaindex.Tenant

import scala.util.Try

trait TenantRepository {

  def getTenant(id: String): Try[Tenant]

}
