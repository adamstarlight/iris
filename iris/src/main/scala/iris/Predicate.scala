package iris

trait PredicateDSL[A] {
  def and(p1: A, p2: A): A
  def or(p1: A, p2: A): A

  def eq[T](fieldName: String, value: T): A
  def neq[T](fieldName: String, value: T): A
}

/*
 * What I want my predicate DSL to resolve into
 * &&(
 *  ===(User.name, "Json"),
 *  >=(User.age, 20)
 * )
 */
