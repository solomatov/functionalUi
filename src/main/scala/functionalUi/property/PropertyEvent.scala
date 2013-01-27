package functionalUi.property

case class PropertyEvent[+ValueT](oldValue: ValueT, newValue: ValueT) {
}
