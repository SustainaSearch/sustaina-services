package com.sustainasearch.services.catalog

//import io.swagger.annotations.{Api, ApiOperation}
import javax.inject.{Inject, Singleton}
import play.api.mvc.{Action, Controller}

@Singleton
//@Api(value = "/catalog")
class CatalogController @Inject()() extends Controller {

//  @ApiOperation(
//    httpMethod = "GET",
//    value = "Searches the SustainaCatalog",
//    produces = "application/json"
//  )
  def search() = Action { implicit request =>
    Ok("Search result from the SustainaCatalog")
  }
}
