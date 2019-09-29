package com.sustainasearch.searchengine.fake

import com.sustainasearch.searchengine._

import scala.collection.mutable.ListBuffer

class FakeSearchEngine[I, D] extends SearchEngine[I, D, Unit] {
  private val index: ListBuffer[FakeInputDocument[D]] = ListBuffer.empty[FakeInputDocument[D]]

  override def query(query: Query): QueryResponse[D, Unit] = {
    val inputDocs = query.mainQuery match {
      case AllDocumentsQuery => index
      case FreeTextQuery(freeText) => index.filter(_.indexEntry.contains(freeText.toLowerCase()))
    }
    val documents = inputDocs
      .map(_.document)
      .toList
    QueryResponse(
      start = 0,
      numFound = documents.size,
      documents,
      ()
    )
  }

  override def add(documents: D*): Unit = documents.foreach { document =>
    index += FakeInputDocument(document)
  }

  override def getById(id: I): Option[D] = {
    throw new NotImplementedError("getById not implemented yet")
  }
}
