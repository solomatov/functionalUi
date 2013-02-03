package functionalUi.property

import org.junit.Assert._
import org.junit.{Test, Before}

class SelectedPropertiesTest {
  val node: Node = new Node
  val parent: Node = new Node
  val parentText = Properties.select[Node, String](node.parent)(_.text)
  var listenerCalled = false

  @Before
  def init() {
    parentText.addListener(e => listenerCalled = true)
    node.parent(parent)
  }

  @Test
  def initialState() {
    assertNull(parentText())
  }

  @Test
  def parentTextChange() {
    parent.text("preved")

    assertEquals("preved", parentText())
    assertTrue(listenerCalled)
  }

  @Test
  def parentToNull() {
    node.parent(null)

    assertNull(parentText())
    assertFalse(listenerCalled)
  }

  @Test
  def parentToDifferentValue() {
    val anotherParent = new Node
    anotherParent.text("hello")
    node.parent(anotherParent)

    assertEquals("hello", parentText())
    assertTrue(listenerCalled)
  }

  class Node {
    val text: ValueProperty[String] = new ValueProperty[String]
    val parent: ValueProperty[Node] = new ValueProperty[Node]
  }
}
