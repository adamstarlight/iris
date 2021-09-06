package object iris {
  type Predicate[A] = PredicateDSL[A] => A

  implicit class PredicateWithLogicOps[A](left: Predicate[A]) {
    def &&(right: Predicate[A]): Predicate[A] =
      (dsl: PredicateDSL[A]) => dsl.and(left(dsl), right(dsl))
    def ||(right: Predicate[A]): Predicate[A] =
      (dsl: PredicateDSL[A]) => dsl.or(left(dsl), right(dsl))
  }
}
