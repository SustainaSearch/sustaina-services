package com.sustainasearch.services.v1.auth

import javax.inject.Inject
import play.api.http.HeaderNames
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class AuthAction @Inject()(bodyParser: BodyParsers.Default, authService: AuthService)(implicit ec: ExecutionContext)
  extends ActionBuilder[TenantRequest, AnyContent] {

  override def parser: BodyParser[AnyContent] = bodyParser

  override protected def executionContext: ExecutionContext = ec

  //  Called when a request is invoked. We should validate the bearer token here
  // and allow the request to proceed if it is valid.
  override def invokeBlock[A](request: Request[A], block: TenantRequest[A] => Future[Result]): Future[Result] = {
    request
      .headers
      .get(HeaderNames.AUTHORIZATION)
      .map { authorization =>
        authService.validate(authorization) match {
          case Success(tenant) => block(TenantRequest(tenant, request))
          case Failure(t) => Future.successful(Results.Unauthorized(t.getMessage))
        }
      }
      .getOrElse(
        // no token was sent - return 401
        Future.successful(Results.Unauthorized)
      )
  }

}
