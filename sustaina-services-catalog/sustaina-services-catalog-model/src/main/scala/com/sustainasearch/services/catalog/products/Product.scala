package com.sustainasearch.services.catalog.products

import java.util.UUID

import com.sustainasearch.services.{Name, ImageUrl}
import com.sustainasearch.services.catalog.products.clothes.Clothes
import com.sustainasearch.services.catalog.products.food.BabyFood

case class Product(id: UUID,
                   productActivity: ProductActivity,
                   functionalNames: Seq[Name],
                   imageUrls: Seq[ImageUrl],
                   brandName: Name,
                   category: Category,
                   sustainaIndex: Double,
                   maybeBabyFood: Option[BabyFood],
                   maybeClothes: Option[Clothes]
                  ) {


}
