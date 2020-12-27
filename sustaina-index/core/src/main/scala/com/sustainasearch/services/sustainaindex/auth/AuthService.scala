package com.sustainasearch.services.sustainaindex.auth

import com.sustainasearch.services.sustainaindex.TenantRepository
import com.sustainasearch.services.sustainaindex.clothes.SustainaIndexCalculator
import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext

@Singleton
class AuthService @Inject()(tenantRepository: TenantRepository)(implicit ec: ExecutionContext) {

}
