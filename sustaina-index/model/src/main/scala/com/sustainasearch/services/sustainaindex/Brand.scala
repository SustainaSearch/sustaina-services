package com.sustainasearch.services.sustainaindex

object Brand extends Enumeration {

  protected case class Val(
                            override val id: Int,
                            score: Int
                          ) extends super.Val(id)

  implicit def valueToBrandVal(x: Value): Brand.Brand = x.asInstanceOf[Val]

  def withId(id: Int): Brand.Brand = Brand(id)

  type Brand = Val
  val FilippaK = Val(
    id = nextId,
    score = 17
  )
  val PoP = Val(
    id = nextId,
    score = 61
  )
}
