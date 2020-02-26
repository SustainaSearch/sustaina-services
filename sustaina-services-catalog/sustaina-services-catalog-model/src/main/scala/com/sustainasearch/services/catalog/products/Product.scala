package com.sustainasearch.services.catalog.products

import java.util.UUID

import com.sustainasearch.services.{Image, Name}
import com.sustainasearch.services.catalog.Certification
import com.sustainasearch.services.catalog.products.clothes.Clothes
import com.sustainasearch.services.catalog.products.food.BabyFood

case class Product(id: UUID,
                   productActivity: ProductActivity,
                   functionalNames: Seq[Name],
                   brandName: Name,
                   category: Category,
                   images: Seq[Image],
                   sustainaIndex: Double,
                   certifications: Seq[Certification],
                   maybeBabyFood: Option[BabyFood],
                   maybeClothes: Option[Clothes]
                  ) {


}
