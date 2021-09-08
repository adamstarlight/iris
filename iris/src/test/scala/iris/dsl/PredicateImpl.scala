package iris.dsl

import iris._

object PredicateImpl extends PredicateDSL[String] {
  def and(p1: String, p2: String): String =
    s"($p1) and ($p2)"

  def or(p1: String, p2: String): String =
    s"($p1) or ($p2)"

  def eq[T](fieldName: String, value: T): String =
    s"$fieldName eq $value"

  def neq[T](fieldName: String, value: T): String =
    s"$fieldName neq $value"

  def gt[T](fieldName: String, value: T): String =
    s"$fieldName gt $value"

  def gte[T](fieldName: String, value: T): String =
    s"$fieldName gte $value"

  def lt[T](fieldName: String, value: T): String =
    s"$fieldName lt $value"

  def lte[T](fieldName: String, value: T): String =
    s"$fieldName lte $value"

  def in[T](fieldName: String, value: Seq[T]): String =
    s"$fieldName in [${value.mkString(", ")}]"
}
