package com.sustainasearch.services.sustainaindex

import com.sustainasearch.services.sustainaindex.clothes.{SustainaIndexCalculator, SustainaIndexInput}
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SustainaIndexService @Inject()(sustainaIndexCalculator: SustainaIndexCalculator)(implicit ec: ExecutionContext) {

  def calculateClothesSustainaIndex(input: SustainaIndexInput): Future[SustainaIndex] = {
    sustainaIndexCalculator.calculateSustainaIndex(input)
    // TODO: save stats?
  }
}
