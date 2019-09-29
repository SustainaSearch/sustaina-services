package com.sustainasearch.services.v1.models

import akka.japi
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ModelService @Inject()(flowTypeService: FlowTypeService,
                             typescriptService: TypescriptService)(implicit ec: ExecutionContext) {

  def getModelsForLanguage(language: String): Future[Option[String]] = {
    language.toLowerCase match {
      case "flow" => flowTypeService.getModels.map(Some(_))
      case "typescript" => typescriptService.getModels.map(Some(_))
      case _ => Future.successful(None)
    }
  }

}
