package com.sustainasearch.services.catalog.products

import java.util.UUID

import com.sustainasearch.services.{Name, ImageUrl}

case class SimpleProduct(id: UUID,
                         functionalNames: Seq[Name],
                         imageUrls: Seq[ImageUrl],
                         brandName: Name,
                         category: Category,
                         sustainaIndex: Double
                        )

object SimpleProduct {

  def apply(product: Product): SimpleProduct = new SimpleProduct(
    product.id,
    product.functionalNames,
    product.imageUrls,
    product.brandName,
    product.category,
    product.sustainaIndex
  )

}
