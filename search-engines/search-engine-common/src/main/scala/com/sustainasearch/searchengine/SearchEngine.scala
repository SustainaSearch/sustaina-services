package com.sustainasearch.searchengine

trait SearchEngine[D] {

  def add(document: D): Unit

  def query(query: Query): QueryResponse[D]
}
