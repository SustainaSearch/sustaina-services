package com.sustainasearch.searchengine.fake

case class FakeInputDocument[D](document: D, indexEntry: String)

object FakeInputDocument {
  def apply[D](document: D): FakeInputDocument[D] = new FakeInputDocument(document, document.toString.toLowerCase())
}
