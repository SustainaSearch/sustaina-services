package com.sustainasearch.services.catalog

import com.sustainasearch.searchengine.SpatialPoint

case class CatalogQuery(mainQuery: String,
                        start: Long = 0,
                        rows: Long = 10,
                        fuzzy: Boolean = false,
                        maybeSpatialPoint: Option[SpatialPoint] = None)
