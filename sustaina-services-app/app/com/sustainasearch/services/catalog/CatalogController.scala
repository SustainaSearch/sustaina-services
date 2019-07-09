package com.sustainasearch.services.catalog

import javax.inject.{Inject, Singleton}
import play.api.mvc.{Action, Controller}

@Singleton
class CatalogController @Inject()() extends Controller {

  def index = Action { implicit request =>
    Ok("CatalogController")
  }
}
