package iris.dsl

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

import iris._

class EqualityOperatorsDSL extends AnyFlatSpec {
  object PredicateImpl extends PredicateDSL[String] {
    def and(p1: String, p2: String): String = ???
    def or(p1: String, p2: String): String = ???
    def eq[T](fieldName: String, value: T): String =
      s"$fieldName eq $value"

    def neq[T](fieldName: String, value: T): String =
      s"$fieldName neq $value"
  }

  it should "generate a valid predicate for ===" in {
    @Record case class User(username: String, age: Int)

    val predicate: Predicate[String] = User.username === "Bob"
    val result = predicate(PredicateImpl)

    assert(result == "username eq Bob")
  }

  it should "generate a valid predicate for !==" in {
    @Record case class User(username: String, age: Int)

    val predicate: Predicate[String] = User.username !== "Bob"
    val result = predicate(PredicateImpl)

    assert(result == "username neq Bob")
  }
}
