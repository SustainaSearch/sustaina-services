package com.sustainasearch.services.catalog

import com.sustainasearch.searchengine.SpatialPoint

case class CatalogQuery(mainQuery: String,
                        fuzzy: Boolean = false,
                        maybeSpatialPoint: Option[SpatialPoint] = None)
