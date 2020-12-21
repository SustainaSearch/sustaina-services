package com.sustainasearch.services.sustainaindex.clothes

object MaterialType extends Enumeration {

  protected case class Val(
                            override val id: Int,
                            score: Int
                          ) extends super.Val(id)

  implicit def valueToMaterialTypeVal(x: Value): MaterialType.MaterialType = x.asInstanceOf[Val]

  type MaterialType = Val
  val Cotton = Val(
    id = nextId,
    score = 17
  )
  val Wool = Val(
    id = nextId,
    score = 61
  )
}
