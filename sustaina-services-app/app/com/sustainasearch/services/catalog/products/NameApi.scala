package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.{LanguageCode, Name}
import com.sustainasearch.services.catalog.LanguageCode
import scalaz.Isomorphism.<=>

object NameApi {
  val name = new (Name <=> NameApiModel) {
    val to: Name => NameApiModel = { name =>
      NameApiModel(
        unparsedName = name.unparsedName,
        languageCode = name.languageCode.map(_.toString)
      )
    }
    val from: NameApiModel => Name = { name =>
      Name(
        unparsedName = name.unparsedName,
        languageCode = name.languageCode.map(LanguageCode.withName)
      )
    }
  }
}
