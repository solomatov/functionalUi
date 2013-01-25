package functionalUi.property

trait WritableProperty[-T] {
  def set(value : T)
}
