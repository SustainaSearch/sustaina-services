package com.sustainasearch.services.sustainaindex

trait TenantRepository {

  def getTenant(id: String): Tenant

}
