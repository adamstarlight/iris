package iris

trait PredicateDSL[A] {
  def and(p1: A, p2: A): A
  def or(p1: A, p2: A): A

  def eq[T](fieldName: String, value: T): A
  def neq[T](fieldName: String, value: T): A

  def gt[T](fieldName: String, value: T): A
  def gte[T](fieldName: String, value: T): A
  def lt[T](fieldName: String, value: T): A
  def lte[T](fieldName: String, value: T): A

  def in[T](fieldName: String, value: Seq[T]): A
}
