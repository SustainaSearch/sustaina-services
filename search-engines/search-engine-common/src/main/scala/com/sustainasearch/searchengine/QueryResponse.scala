package com.sustainasearch.searchengine

case class QueryResponse[D](start: Long,
                            numFound: Long,
                            documents: List[D])