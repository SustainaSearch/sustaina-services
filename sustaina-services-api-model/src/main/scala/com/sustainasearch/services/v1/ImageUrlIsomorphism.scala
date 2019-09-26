package com.sustainasearch.services.v1

import com.sustainasearch.services.ImageUrl
import scalaz.Isomorphism.<=>

object ImageUrlIsomorphism {
  val imageUrl = new (ImageUrl <=> ImageUrlApiModel) {
    val to: ImageUrl => ImageUrlApiModel = { imageUrl =>
      ImageUrlApiModel(
        imageType = imageUrl.imageType,
        url = imageUrl.url
      )
    }
    val from: ImageUrlApiModel => ImageUrl = { imageUrl =>
      ImageUrl(
        imageType = imageUrl.imageType,
        url = imageUrl.url
      )
    }
  }
}
