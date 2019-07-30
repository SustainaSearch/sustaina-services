package com.sustainasearch.services.catalog.products

import java.util.UUID

import com.sustainasearch.services.catalog.products.clothes.Clothes
import com.sustainasearch.services.catalog.products.food.BabyFood

case class Product(id: UUID,
                   functionalNames: Seq[Name],
                   brandName: Name,
                   category: Category,
                   sustainaIndex: Double,
                   maybeBabyFood: Option[BabyFood],
                   maybeClothes: Option[Clothes]
                  ) {


}
