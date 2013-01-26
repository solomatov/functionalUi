package functionalUi.property

case class PropertyEvent[+T](oldValue: T, newValue: T) {
}
