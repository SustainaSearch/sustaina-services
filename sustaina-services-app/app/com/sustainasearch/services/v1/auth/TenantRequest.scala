package com.sustainasearch.services.v1.auth

import com.sustainasearch.services.sustainaindex.Tenant
import play.api.mvc.{Request, WrappedRequest}

case class TenantRequest[A](tenant: Tenant, request: Request[A]) extends WrappedRequest[A](request)
