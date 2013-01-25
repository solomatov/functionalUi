package functionalUi.property

import functionalUi.util.Registration

object Properties {
  type Property[T] = AnyRef with ReadableProperty[T] with WritableProperty[T]

  def bind[S <: D, D](from: ReadableProperty[S], to: WritableProperty[D]): Registration = {
    from.addListener({e => to.set(e.newValue)})
  }

  def bind[T](from : Property[T], to : Property[T]): Registration = {
    bind(from : ReadableProperty[T], to : WritableProperty[T]) + bind(to : ReadableProperty[T], from : WritableProperty[T])
  }

  def lift[P, R](f: P => R)(p: ReadableProperty[P]): ReadableProperty[R] = ap(pure(f), p)

  def lift[P1, P2, R](f: (P1, P2) => R)(p1: ReadableProperty[P1], p2: ReadableProperty[P2]): ReadableProperty[R] = ap(ap(pure(f.curried), p1), p2)

  def lift[P1, P2, P3, R](f: (P1, P2, P3) => R)(p1: ReadableProperty[P1], p2: ReadableProperty[P2], p3: ReadableProperty[P3]): ReadableProperty[R] = ap(ap(ap(pure(f.curried), p1), p2), p3)

  def pure[T](t : T) : ReadableProperty[T] = {
    new ReadableProperty[T] {
      def get: T = t

      def addListener(l: (PropertyEvent[T]) => Unit): Registration = Registration.EMPTY
    }
  }

  def ap[X, Y](f : ReadableProperty[X => Y], p : ReadableProperty[X]): ReadableProperty[Y] = {
    new ReadableProperty[Y] {
      def get: Y = f.get(p.get)

      def addListener(l: (PropertyEvent[Y]) => Unit): Registration = {
        var lastValue: Y = get

        val handler: PropertyEvent[Any] => Unit = {e =>
          val newValue = get
          if (lastValue != newValue) {
            l(new PropertyEvent[Y](lastValue, newValue))
            lastValue = newValue
          }
        }

        f.addListener(handler) + p.addListener(handler)
      }
    }
  }

  def select[S <: Object, D <: AnyRef](ps: ReadableProperty[S])(f: S => ReadableProperty[D]): ReadableProperty[D] = {
    new ReadableProperty[D] {
      var defaultValue: D = _

      def get: D = {
        val psValue: S = ps.get
        if (psValue == null)
          defaultValue
        else
          f(psValue).get
      }

      def addListener(l: (PropertyEvent[D]) => Unit): Registration = {
        var lastValue: D = get
        val onChange: () => Unit = {() =>
          val newValue = get
          if (lastValue != newValue) {
            l(new PropertyEvent[D](lastValue, newValue))
            lastValue = newValue
          }
        }

        var nestedReg = Registration.EMPTY
        new Registration {
          def remove() {
            nestedReg.remove()
          }
        } + ps.addListener({e =>
          nestedReg.remove()
          nestedReg = Registration.EMPTY

          if (e.newValue != null) {
            nestedReg = f(e.newValue).addListener({e => onChange()})
          }
          onChange()
        })
      }
    }
  }
}