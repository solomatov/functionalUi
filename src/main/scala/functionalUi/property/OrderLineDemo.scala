package functionalUi.property

object OrderLineDemo extends App {
  class OrderLine {
    val itemPrice: ValueProperty[Int] = new ValueProperty[Int]
    val itemCount: ValueProperty[Int] = new ValueProperty[Int]

    val subTotal: ReadableProperty[Int] = Properties.lift[Int, Int, Int](_*_)(itemPrice, itemCount)
  }


  val line = new OrderLine

  line.subTotal.addListener(e => println("subtotal changed : " + e.oldValue + " -> " + e.newValue))

  println(line.subTotal())

  line.itemPrice(100)
  line.itemCount(3)

  println(line.subTotal())
}
