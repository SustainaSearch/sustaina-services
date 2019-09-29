package com.sustainasearch.services.v1.models

import bridges.flow._
import bridges.flow.syntax._
import com.sustainasearch.services.v1.NameApiModel
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FlowTypeService @Inject()()(implicit ec: ExecutionContext) {

  def getModels: Future[String] = {
    Future(
      Flow.render(
        List(
          decl[NameApiModel]
        )
      )
    )
  }

}
