package com.sustainasearch.services.sustainaindex

case class SustainaIndex(percent: Float) {
  @transient
  lazy val formattedPercent = f"${percent * 100}%.0f%%"
}
