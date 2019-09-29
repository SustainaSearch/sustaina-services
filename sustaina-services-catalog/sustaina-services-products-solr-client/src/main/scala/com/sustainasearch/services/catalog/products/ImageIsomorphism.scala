package com.sustainasearch.services.catalog.products

import com.sustainasearch.services.{Image, ImageType}
import scalaz.Isomorphism.<=>

object ImageIsomorphism {
  private val Separator = '|'
  val image = new (Image <=> String) {
    val to: Image => String = { image =>
      s"${image.imageType.toString}|${image.url}"
    }
    val from: String => Image = { image =>
      val parts = image.split(Separator)
      require(parts.size == 2, "Image parts must be exactly 2")
      val (imageType, url) = (parts.head, parts.last)
      Image(
        imageType = ImageType.withName(imageType),
        url = url
      )
    }
  }
}
