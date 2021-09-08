package iris

trait RecordField[T] {
  def fieldName: String

  def ===[A](value: T): Predicate[A] =
    (dsl: PredicateDSL[A]) => dsl.eq(fieldName, value)

  def !==[A](value: T): Predicate[A] =
    (dsl: PredicateDSL[A]) => dsl.neq(fieldName, value)

  def >[A](value: T): Predicate[A] =
    (dsl: PredicateDSL[A]) => dsl.gt(fieldName, value)

  def >=[A](value: T): Predicate[A] =
    (dsl: PredicateDSL[A]) => dsl.gte(fieldName, value)

  def <[A](value: T): Predicate[A] =
    (dsl: PredicateDSL[A]) => dsl.lt(fieldName, value)

  def <=[A](value: T): Predicate[A] =
    (dsl: PredicateDSL[A]) => dsl.lte(fieldName, value)

  def in[A](value: Seq[T]): Predicate[A] =
    (dsl: PredicateDSL[A]) => dsl.in(fieldName, value)
}
