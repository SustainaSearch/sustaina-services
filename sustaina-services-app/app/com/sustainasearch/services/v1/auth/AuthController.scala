package com.sustainasearch.services.v1.auth

import java.time.Clock

import io.swagger.annotations._
import javax.inject.{Inject, Singleton}
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim, JwtHeader}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
@Api(value = "/Auth")
class AuthController @Inject()(components: ControllerComponents,
                              )(implicit
                                ec: ExecutionContext,
                                clock: Clock = Clock.systemUTC()) extends AbstractController(components) {
  val privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCUnW/2eazNTCrC\nI/TAGlQSF13RoRK8x86MJAeXQQ1Qqn1ptO5jZMxK8FDopKcVxwrVtChV4t6s0Ay3\nFKvD429PEFUFFCfGEiAW77VXe4wLNF1V+mZ1RrHRbH5brTjMgkTKjnGzGTGD6Ric\nPO7dqTqNPWAFEMkxxbq6GmjMLAMqYnrK+dk8sGcyxE1zheACO6JzBlTsBcBPl7Li\nfneATXLQXLqs0A81aNngBEO6/k2cmkw79PgYprpUIC1hfdpB+vjsTHFeqA0r9ZOL\ndJDLjzZoFEZnOVXQ5tTa1gQsXni8hGmD51RKWU3XOP2/SfjqJLZ9caH9HkT/d+nV\niUVGxDbZAgMBAAECggEAK7c8DhawnBtBoKYPAss7265/7IAjEOD9gv++M+Hw1r+v\n8H5GeXpXFdwnEKgOdjt8lmxOSSVZNyBj/R7Zf9/RCELXn4zUPlFqmxScFUXEZi9b\nHUVxCiJngCEX8kO8J3xSW/sWuwY4KINSt/K8mPuEu7NIIXVUmY1+ZewK07RGohqt\n/Me4rk6dam5e7bC8VLSbG8Q3BQMjb0sBkGM3BZH0XRupEinHZ62HtKlBWEpnUqWK\nt3fDAIsE0EyD/w7NniNBCC6g8BePdA0uRQKs/on+7JOyQ2qYX8Ep/SMe7NDzVSQR\n51b48GMR6EWfwjEF0Merw2W7Tp32Df8/c4P/i3bpYQKBgQDgfZOwDoZCmXJfJjbD\ngPnRqYVUeqLQ+cDK3KPkX8L0ubd7ZwUxk7dcUHuHtPEC48t9AHi3izzacZOGzHJ7\nR5LBht/LcSWgZUgdRaH0sOY/S21WLQA1YGbIG+7c31H3jP8JQ/yis5WUClNzP8Pl\n7LZBp/jcUPmc0yeqGv1D5P24fQKBgQCpeX2Q+Lzbd/3as2fIvvec4ucwf3yw49Ea\n6uLlPIVeGPt7Kj1dyO9NCQcZ3J+ixt9dM8AahCTvv+YtKUIgUEKRLB12KmAkwPxP\njCoRWFD7svCguOWnwHK/S9y3EH8aj7LARHkmuLussGfCHVolWD4jhfYlkk+sV+fa\njmMQcqkijQKBgQDRvoKui+OFdjkWDW49W9QNwIWCWNKmzbMD1wKJ7a8JWDvGYIrJ\nt2oqJkhEkxpbyNnnAPnJA57nuhZMa9jKtiS4DHwsaJrvMbIfJ2Aabu2xVZfiXElF\nlbxyWybh1wWOdX4T+iGhIokuuDcgBwPRX0kqLvalYkLV87Ori2v2c41bQQKBgQCF\nHHJ+spALxXRzGaSzrSLpa+LwWcIDYfy0qrbqHJ4YKYEk3Sl0B0XF+QhFzN7pyFBa\ncTI64X3tfYl6AUT6AnA2fDLrxB7d32KNzGaiVv7Mo2CSrIddOjAmhpxmgSOEJkfc\n8itCOajW23uzoMBUQroTxr1uBzxi/mySYnH20kh8ZQKBgQCxHMM2pVmp1a1NOPzN\n/r9sXz9C2Le++LRuFZasEu2jKMPLThp/SxkKiz9mBdcYqRUIwBQAD+FCBI5WT/h2\nBm/C+bkH5Uuo8SZSy6SP9X5X3WzzuMEjEMf7Qh2sGMRKfDmtuVBZj73zNdxyzJxN\nvzZtZkmdndUx79g4sVzoS3KyRQ=="

  @ApiOperation(
    httpMethod = "GET",
    value = "Get JWT Token - only for test purposes",
    produces = "text/plain",
    response = classOf[String]
  )
  def getAuthorizationHeader(
                              @ApiParam(value = "Tenant ID", required = true) tid: String,
                              @ApiParam(value = "Tenant host", required = true) thost: String,
                              @ApiParam(value = "Token expires in seconds", required = false, defaultValue = "60") exp: Int = 60) = Action.async { implicit request =>
    val claim = JwtClaim(s"""{"tid":"${tid}", "thost":"${thost}"}""")
      .issuedNow
      .startsNow
      .expiresIn(exp)
    val token = Jwt.encode(JwtHeader(JwtAlgorithm.RS256), claim, privateKey)
    Future(Ok(s"Bearer $token"))
  }

}
