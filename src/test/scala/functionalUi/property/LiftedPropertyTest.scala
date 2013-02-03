package functionalUi.property

import org.junit.Assert._
import org.junit.{Before, Test}

class LiftedPropertyTest {
  val v1 = new ValueProperty[Int]
  val v2 = new ValueProperty[Int]
  val prod = Properties.lift[Int, Int, Int](_*_)(v1, v2)
  var listenerCalled = false

  @Before
  def init() {
    prod.addListener(e => listenerCalled = true)
  }


  @Test
  def changeToDeps() {
    v1(1)
    v2(2)

    assertEquals(2, prod())
  }

  @Test
  def listenersNotCalledIfResultNotChanged() {
    v1(239)

    assertFalse(listenerCalled)
  }

  @Test
  def listenersCalledIfResultChanged() {
    v1(239)
    v2(1)

    assertTrue(listenerCalled)
  }
}
