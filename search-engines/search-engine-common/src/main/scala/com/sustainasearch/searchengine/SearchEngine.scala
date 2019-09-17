package com.sustainasearch.searchengine

trait SearchEngine[Id, Document, Facets] {

  def add(documents: Document*): Unit

  def getById(id: Id): Document

  def query(query: Query): QueryResponse[Document, Facets]
}
