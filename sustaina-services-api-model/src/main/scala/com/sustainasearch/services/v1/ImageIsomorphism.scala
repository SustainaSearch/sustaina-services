package com.sustainasearch.services.v1

import com.sustainasearch.services.{Image, ImageType}
import scalaz.Isomorphism.<=>

object ImageIsomorphism {
  val image = new (Image <=> ImageApiModel) {
    val to: Image => ImageApiModel = { image =>
      ImageApiModel(
        imageType = image.imageType.toString(),
        url = image.url
      )
    }
    val from: ImageApiModel => Image = { image =>
      Image(
        imageType = ImageType.withName(image.imageType),
        url = image.url
      )
    }
  }
}