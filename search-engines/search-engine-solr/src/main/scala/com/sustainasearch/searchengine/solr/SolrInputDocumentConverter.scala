package com.sustainasearch.searchengine.solr

import com.sustainasearch.searchengine.InputDocumentConverter
import org.apache.solr.common.SolrInputDocument

trait SolrInputDocumentConverter[From] extends InputDocumentConverter[From, SolrInputDocument]
