package functionalUi.util

trait Registration { reg =>
  def remove()

  def +(r: Registration) : Registration = {
    new Registration {
      def remove() {
        reg.remove()
        r.remove()
      }
    }
  }
}

object Registration {
  val EMPTY = new Registration() {
    def remove() {}
  }
}
