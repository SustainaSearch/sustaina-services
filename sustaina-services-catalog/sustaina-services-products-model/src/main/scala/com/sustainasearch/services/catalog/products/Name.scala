package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.catalog.products.LanguageCode.LanguageCode

case class Name(unparsedName: String, languageCode: Option[LanguageCode])
