package com.sustainasearch.services.sustainaindex

object Certification extends Enumeration {

  protected case class Val(
                            override val id: Int,
                            score: Int
                          ) extends super.Val(id)

  implicit def valueToCertificationVal(x: Value): Certification.Certification = x.asInstanceOf[Val]

  def withId(id: Int): Certification.Certification = Certification(id)

  type Certification = Val
  val BlueSign = Val(
    id = nextId,
    score = 17
  )
  val EUEcoLabel = Val(
    id = nextId,
    score = 61
  )
}
