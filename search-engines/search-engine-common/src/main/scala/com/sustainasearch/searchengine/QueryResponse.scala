package com.sustainasearch.searchengine

case class QueryResponse[D](numFound: Long,
                            documents: List[D])