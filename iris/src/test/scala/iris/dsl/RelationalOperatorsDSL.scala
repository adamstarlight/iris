package iris.dsl

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

import iris._

class RelationalOperatorsDSL extends AnyFlatSpec {
  @Record case class User(username: String, age: Int)

  object PredicateImpl extends PredicateDSL[String] {
    def and(p1: String, p2: String): String = ???
    def or(p1: String, p2: String): String = ???

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

  def assertPredicate(p: Predicate[String], expected: String) =
    assert(p(PredicateImpl) == expected)

  List[(String, Predicate[String], String)](
    ("===", User.username === "Bob", "username eq Bob"),
    ("!==", User.username !== "Bob", "username neq Bob"),
    (">", User.username > "Bob", "username gt Bob"),
    (">=", User.username >= "Bob", "username gte Bob"),
    ("<", User.username < "Bob", "username lt Bob"),
    ("<=", User.username <= "Bob", "username lte Bob"),
    (
      "in",
      User.username in List("Bob", "Sam", "Kate"),
      "username in [Bob, Sam, Kate]"
    )
  ).foreach(test =>
    it should s"generate a valid predicate for ${test._1}" in {
      assertPredicate(test._2, test._3)
    }
  )
}
