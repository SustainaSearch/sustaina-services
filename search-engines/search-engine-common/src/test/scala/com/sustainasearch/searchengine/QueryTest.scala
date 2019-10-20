package com.sustainasearch.searchengine

import org.scalatest.FunSuite

class QueryTest extends FunSuite{

  test("awdasd") {
    val query = RangeFilterQuery("field1", 3, 56)
    val str = query.toString
    println(str)

    val a = s"RangeFilterQuery($fieldName,$from,$to)"

    str match {
      case s"RangeFilterQuery($fieldName,$from,$to)" => Some(RangeFilterQuery(fieldName, from, to))
      case _ => None
    }
  }
}
