package com.sustainasearch.services.catalog.products

import java.util.UUID

case class SimpleProduct(id: UUID,
                         functionalNames: Seq[Name],
                         brandName: Name,
                         category: Category,
                         sustainaIndex: Double
                        )

object SimpleProduct {

  def apply(product: Product): SimpleProduct = new SimpleProduct(
    product.id,
    product.functionalNames,
    product.brandName,
    product.category,
    product.sustainaIndex
  )

}
