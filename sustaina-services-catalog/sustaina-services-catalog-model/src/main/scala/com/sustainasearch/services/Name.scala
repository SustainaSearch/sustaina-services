package com.sustainasearch.services

import com.sustainasearch.services.LanguageCode.LanguageCode

case class Name(unparsedName: String, languageCode: Option[LanguageCode])
