package com.sustainasearch.services.v1.catalog.products.clothes

import com.sustainasearch.services.LanguageCode
import com.sustainasearch.services.catalog.products.clothes.{Clothes, Composition}
import scalaz.Isomorphism.<=>

object ClothesIsomorphism {

  val clothes = new (Clothes <=> ClothesApiModel) {
    val to: Clothes => ClothesApiModel = { clothes =>
      ClothesApiModel(
        compositions = clothes.compositions.map(composition.to)
      )
    }
    val from: ClothesApiModel => Clothes = { clothes =>
      Clothes(
        compositions = clothes.compositions.map(composition.from)
      )
    }
  }

  val composition = new (Composition <=> CompositionApiModel) {
    val to: Composition => CompositionApiModel = { composition =>
      CompositionApiModel(
        languageCode = composition.languageCode.toString,
        unparsedComposition = composition.unparsedComposition
      )
    }
    val from: CompositionApiModel => Composition = { composition =>
      Composition(
        languageCode = LanguageCode.withName(composition.languageCode),
        unparsedComposition = composition.unparsedComposition
      )
    }
  }
}
