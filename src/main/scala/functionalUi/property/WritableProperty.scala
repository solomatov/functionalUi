package functionalUi.property

trait WritableProperty[-ValueT] {
  def apply(value: ValueT)
}
