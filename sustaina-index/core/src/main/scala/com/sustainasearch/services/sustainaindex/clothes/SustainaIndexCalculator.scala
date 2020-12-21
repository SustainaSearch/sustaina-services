package com.sustainasearch.services.sustainaindex.clothes

import com.sustainasearch.services.sustainaindex.SustainaIndex
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SustainaIndexCalculator @Inject()(implicit ec: ExecutionContext) {

  def calculateSustainaIndex(input: SustainaIndexInput): Future[SustainaIndex] = {
    // TODO: implement sustaina index for clothes
    Future {
      SustainaIndex(0.437F)
    }
  }

}
