package com.sustainasearch.searchengine

trait QueryConverter[To] {

  def convertFrom(query: Query): To
}
