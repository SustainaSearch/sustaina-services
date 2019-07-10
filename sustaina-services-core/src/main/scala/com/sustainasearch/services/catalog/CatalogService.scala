package com.sustainasearch.services.catalog

import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CatalogService @Inject()(implicit ec: ExecutionContext) {

  def query(query: CatalogQuery): Future[CatalogQueryResponse] = {
    Future.successful(CatalogQueryResponse(s"Query response for main query ${query.mainQuery}"))
  }

}
