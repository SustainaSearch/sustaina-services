package com.sustainasearch.services.v1

import com.sustainasearch.services.Image
import scalaz.Isomorphism.<=>

object ImageIsomorphism {
  val image = new (Image <=> ImageApiModel) {
    val to: Image => ImageApiModel = { image =>
      ImageApiModel(
        imageType = image.imageType,
        url = image.url
      )
    }
    val from: ImageApiModel => Image = { image =>
      Image(
        imageType = image.imageType,
        url = image.url
      )
    }
  }
}