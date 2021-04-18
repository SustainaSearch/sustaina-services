package com.sustainasearch.services.sustainaindex

case class SustainaIndex(percent: Float,
						 material: Float,
						 process: Float,
						 quality: Float,
						 workingConditions: Float,
						 packaging: Float,
						 energy: Float,
						 crc: Float
						 ) {
  @transient
  lazy val formattedPercent = f"${percent * 100}%.0f"
}
