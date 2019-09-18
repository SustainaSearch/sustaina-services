package com.sustainasearch.searchengine

trait SearchEngine[Id, Document, Facets] {

  def add(documents: Document*): Unit

  def getById(id: Id): Option[Document]

  def query(query: Query): QueryResponse[Document, Facets]
}
