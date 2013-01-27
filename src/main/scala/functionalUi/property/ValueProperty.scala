package functionalUi.property

import functionalUi.util.Registration
import collection.mutable.ListBuffer

class ValueProperty[ValueT] extends ReadableProperty[ValueT] with WritableProperty[ValueT] {
  private var myValue: ValueT = _
  private val myListeners: ListBuffer[PropertyEvent[ValueT] => Unit] = new ListBuffer[(PropertyEvent[ValueT]) => Unit]

  def apply(): ValueT = myValue

  def apply(value: ValueT) {
    if (value == myValue) return

    val oldValue = myValue
    myValue = value

    for (l <- myListeners) {
      l(new PropertyEvent[ValueT](oldValue, myValue))
    }
  }

  def addListener(l: (PropertyEvent[ValueT]) => Unit): Registration = {
    myListeners += l
    new Registration {
      def remove() {
        myListeners -= l
      }
    }
  }
}
