package com.sustainasearch.services.v1

import com.sustainasearch.searchengine.{BooleanFilterQuery, RangeFilterQuery, TextFilterQuery}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}

@RunWith(classOf[JUnitRunner])
class FilterQueryJsonParserTest extends FunSuite with Matchers {

  test("Isomorphism for TextFilterQuery") {
    val query = TextFilterQuery("field1", "value1")
    val json = """{"text":{"fieldName":"field1","fieldValue":"value1"}}"""
    println(json)
    FilterQueryJsonParser.fromJson(json) shouldBe query
  }

  test("Isomorphism for BooleanFilterQuery") {
    val query = BooleanFilterQuery("field1", fieldValue = true)
    val json = """{"boolean":{"fieldName":"field1","fieldValue":true}}"""
    println(json)
    FilterQueryJsonParser.fromJson(json) shouldBe query
  }

  test("Isomorphism for RangeFilterQuery") {
    val query = RangeFilterQuery("field1", from = 1, to = 10)
    val json = """{"range":{"fieldName":"field1","from":1,"to":10}}"""
    println(json)
    FilterQueryJsonParser.fromJson(json) shouldBe query
  }

  test("Unsupported filter query") {
    intercept[IllegalArgumentException] {
      FilterQueryJsonParser.fromJson("""{"unsupported":true}""")
    }
  }

}
