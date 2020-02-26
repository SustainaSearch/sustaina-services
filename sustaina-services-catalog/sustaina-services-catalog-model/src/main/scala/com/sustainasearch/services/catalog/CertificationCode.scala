package com.sustainasearch.services.catalog

object CertificationCode extends Enumeration {
  type CertificationCode = Value
  val Gots = Value("GOTS")
  val Bci = Value("BCI")
  val Bluesign = Value("BLSI")
  val OekoTex100 = Value("OEKO100")
  val EuEcoLabel = Value("EUECOLBL")
  val EuEcoLeaf = Value("EUECOLEAF")
  val Krav = Value("KRAV")
  val Svanen  = Value("SVAN")
}
