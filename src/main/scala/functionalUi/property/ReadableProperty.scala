package functionalUi.property

import functionalUi.util.Registration

trait ReadableProperty[+ValueT] {
  def apply(): ValueT

  def addListener(l: PropertyEvent[ValueT] => Unit): Registration
}
