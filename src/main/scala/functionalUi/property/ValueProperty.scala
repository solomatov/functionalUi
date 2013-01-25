package functionalUi.property

import functionalUi.util.Registration
import collection.mutable.ListBuffer

class ValueProperty[T] extends ReadableProperty[T] with WritableProperty[T] {
  private var myValue: T = _
  private val myListeners: ListBuffer[PropertyEvent[T] => Unit] = new ListBuffer[(PropertyEvent[T]) => Unit]

  def get: T = myValue

  def set(value: T) {
    if (value == myValue) return

    val oldValue = myValue
    myValue = value

    for (l <- myListeners) {
      l(new PropertyEvent[T](oldValue, myValue))
    }
  }

  def addListener(l: (PropertyEvent[T]) => Unit): Registration = {
    myListeners += l
    new Registration {
      def remove() {
        myListeners -= l
      }
    }
  }
}
