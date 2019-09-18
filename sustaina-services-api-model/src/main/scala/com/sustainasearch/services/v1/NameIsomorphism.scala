package com.sustainasearch.services.v1

import com.sustainasearch.services.{LanguageCode, Name}
import scalaz.Isomorphism.<=>

object NameIsomorphism {
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
