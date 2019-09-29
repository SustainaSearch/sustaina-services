package com.sustainasearch.services.v1.models

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ModelService @Inject()(flowTypeService: FlowTypeService)(implicit ec: ExecutionContext) {

  def getModelsForLanguage(language: String): Future[Option[String]] = {
    language.toLowerCase match {
      case "flow" => flowTypeService.getModels.map(Some(_))
      case _ => Future.successful(None)
    }
  }

}
