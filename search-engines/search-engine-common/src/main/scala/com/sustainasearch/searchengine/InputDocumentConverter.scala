package com.sustainasearch.searchengine

trait InputDocumentConverter[From, To] {

  def convertFrom(document: From): To
}
