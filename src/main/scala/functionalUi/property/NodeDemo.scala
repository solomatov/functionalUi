package functionalUi.property

object NodeDemo extends App {
  class Node {
    val text: ValueProperty[String] = new ValueProperty[String]
    val parent: ValueProperty[Node] = new ValueProperty[Node]
  }

  val n1 = new Node()
  val parentText = Properties.select[Node, String](n1.parent)(_.text)

  println("parent text = " + parentText.get)

  parentText.addListener(e =>
    println("parent text chaned : " + e.oldValue + " -> " + e.newValue)
  )

  val n2 = new Node()
  n2.text.set("preved")
  n1.parent.set(n2)

  println("parent text = " + parentText.get)
}
