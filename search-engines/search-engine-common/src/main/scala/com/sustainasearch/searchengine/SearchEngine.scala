package com.sustainasearch.searchengine

trait SearchEngine[D] {

  def add(documents: D*): Unit

  def query(query: Query): QueryResponse[D]
}
