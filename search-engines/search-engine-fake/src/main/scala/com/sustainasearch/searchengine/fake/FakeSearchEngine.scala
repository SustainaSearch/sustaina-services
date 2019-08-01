package com.sustainasearch.searchengine.fake

import com.sustainasearch.searchengine.{Query, QueryResponse, SearchEngine}

import scala.collection.mutable.ListBuffer

class FakeSearchEngine[D] extends SearchEngine[D] {
  private val index: ListBuffer[FakeInputDocument[D]] = ListBuffer.empty[FakeInputDocument[D]]

  override def query(query: Query): QueryResponse[D] = {
    val documents = index
      .filter(_.indexEntry.contains(query.mainQuery.toLowerCase()))
      .map(_.document)
      .toList
    QueryResponse(
      start = 0,
      numFound = documents.size,
      documents
    )
  }

  override def add(documents: D*): Unit = documents.foreach { document =>
    index += FakeInputDocument(document)
  }

}
