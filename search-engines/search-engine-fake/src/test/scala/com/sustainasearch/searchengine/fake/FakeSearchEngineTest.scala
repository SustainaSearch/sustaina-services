package com.sustainasearch.searchengine.fake

import com.sustainasearch.searchengine.Query
import org.scalatest.{FunSuite, Matchers}

class FakeSearchEngineTest extends FunSuite with Matchers {

  test("FakeSearchEngine supports case insensitive queries") {
    val underTest = new FakeSearchEngine[SimpleProduct]

    val product1 = SimpleProduct(id = 1l, name = "Hipp")
    val product2 = SimpleProduct(id = 2l, name = "Semper")

    underTest.add(product1)
    underTest.add(product2)

    underTest.query(Query(query = "hip")).documents should contain only product1
    underTest.query(Query(query = "HIPP")).documents should contain only product1
    underTest.query(Query(query = "1")).documents should contain only product1
    underTest.query(Query(query = "semper")).documents should contain only product2
    underTest.query(Query(query = "2")).documents should contain only product2
    underTest.query(Query(query = "p")).documents should contain only(product1, product2)
  }
}

case class SimpleProduct(id: Long, name: String)
