package iris

trait RecordField[T] {
  def fieldName: String

  def ===[A](value: T): Predicate[A] =
    (dsl: PredicateDSL[A]) => dsl.eq(fieldName, value)

  def !==[A](value: T): Predicate[A] =
    (dsl: PredicateDSL[A]) => dsl.neq(fieldName, value)
}
