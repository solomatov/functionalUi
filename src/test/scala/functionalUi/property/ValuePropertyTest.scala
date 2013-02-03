package functionalUi.property

import org.junit.{Assert, Test}
import org.junit.Assert._

class ValuePropertyTest {
  @Test
  def valuePropertyInitial() {
    val v: ValueProperty[Int] = new ValueProperty[Int]
    assertEquals(0, v())
  }

  @Test
  def valuePropertySet() {
    val v: ValueProperty[Int] = new ValueProperty[Int]
    var called = false
    v.addListener(e => called = true)

    v(239)
    assertEquals(239, v())
    assertTrue(called)
  }
}
