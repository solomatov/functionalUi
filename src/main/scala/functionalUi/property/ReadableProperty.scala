package functionalUi.property

import functionalUi.util.Registration

trait ReadableProperty[+T] {
  def get : T
  def addListener(l : PropertyEvent[T] => Unit) : Registration
}
