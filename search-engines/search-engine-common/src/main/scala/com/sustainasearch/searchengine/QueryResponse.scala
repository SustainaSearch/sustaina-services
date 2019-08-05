package com.sustainasearch.searchengine

case class QueryResponse[D, F](start: Long,
                            numFound: Long,
                            documents: List[D],
                            facets: F)