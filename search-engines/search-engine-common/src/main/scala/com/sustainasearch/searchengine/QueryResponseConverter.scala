package com.sustainasearch.searchengine

trait QueryResponseConverter[Document, From] {

  def convertFrom(searchEngineQueryResponse: From): QueryResponse[Document]
}
