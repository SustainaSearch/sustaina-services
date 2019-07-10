package com.sustainasearch.services.catalog

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CatalogService @Inject()(implicit ec: ExecutionContext) {

  def search(query: CatalogQuery): Future[CatalogSearchResult] = {
    Future.successful(CatalogSearchResult(s"Result for main query ${query.mainQuery}"))
  }

}
