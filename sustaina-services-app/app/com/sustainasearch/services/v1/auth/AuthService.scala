package com.sustainasearch.services.v1.auth

import java.security.PublicKey
import java.time.Clock

import com.sustainasearch.services.sustainaindex.Tenant
import com.sustainasearch.services.sustainaindex.tenant.TenantService
import com.sustainasearch.services.v1.sustainaindex.TenantApiModel
import javax.inject.{Inject, Singleton}
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim, JwtOptions}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

@Singleton
class AuthService @Inject()(publicKeyProvider: PublicKeyProvider,
                            tenantService: TenantService)(implicit
                                                          ec: ExecutionContext,
                                                          clock: Clock) {

  private val jwtAlgorithms = JwtAlgorithm.allRSA()

  private val publicKey: PublicKey = publicKeyProvider.getJwtPublicKey

  def validate(authorizationHeaderValue: String): Try[Tenant] = {
    for {
      token <- extractToken(authorizationHeaderValue)
      claim <- Jwt.decode(token, publicKey, jwtAlgorithms, JwtOptions.DEFAULT)
      validatedClaim <- validateClaim(claim)
      tenant <- TenantApiModel.fromJson(validatedClaim.content).map(_.toTenant)
      validatedTenant <- tenantService.isValid(tenant)
    } yield validatedTenant
  }

  // A regex for parsing the Authorization header value
  private val headerTokenRegex =
    """Bearer (.+?)""".r

  private val extractToken: String => Try[String] = {
    case headerTokenRegex(token) => Success(token)
    case _ => Failure(new IllegalArgumentException("Authorization header does not match the correct JWT pattern"))
  }

  private val validateClaim: JwtClaim => Try[JwtClaim] = claim => {
    Option(claim)
      .collect { case c if c.isValid => c }
      .fold[Try[JwtClaim]](Failure(new IllegalArgumentException("The JWT did not pass validation")))(c => Success(c))
  }

}