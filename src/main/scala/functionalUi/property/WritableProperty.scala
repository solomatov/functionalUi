package functionalUi.property

trait WritableProperty[-T] {
  def apply(value: T)
}
