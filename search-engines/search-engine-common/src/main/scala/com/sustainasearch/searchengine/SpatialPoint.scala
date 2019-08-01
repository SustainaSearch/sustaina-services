package com.sustainasearch.searchengine

case class SpatialPoint(latitude: Double, longitude: Double)

object SpatialPoint {

  def apply(spatialPoint: String): SpatialPoint = {
    val latitudeLongitude = spatialPoint.split(",")
    require(latitudeLongitude.size == 2)
    SpatialPoint(latitudeLongitude.head.toDouble, latitudeLongitude.last.toDouble)
  }

}