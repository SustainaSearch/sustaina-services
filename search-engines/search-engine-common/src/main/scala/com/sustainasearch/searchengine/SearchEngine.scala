package com.sustainasearch.searchengine

trait SearchEngine[D, F] {

  def add(documents: D*): Unit

  def query(query: Query): QueryResponse[D, F]
}
