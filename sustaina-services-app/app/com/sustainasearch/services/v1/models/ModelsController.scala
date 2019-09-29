package com.sustainasearch.services.v1.models

import io.swagger.annotations.{Api, ApiOperation, ApiParam}
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
@Api(value = "/API Models")
class ModelsController @Inject()(components: ControllerComponents,
                                 modelService: ModelService)(implicit ec: ExecutionContext) extends AbstractController(components) {

  @ApiOperation(
    httpMethod = "GET",
    value = "Generates the API models in the specific language",
    produces = "text/plain",
    response = classOf[String]
  )
  def getModels(@ApiParam(value = "Language (Flow or Typescript)", required = true) language: String) = Action.async {
    for {
      maybeModels <- modelService.getModelsForLanguage(language)
    } yield {
      maybeModels.fold(BadRequest(s"Language '$language' is not supported yet")) { models =>
        Ok(models)
      }
    }
  }

}
