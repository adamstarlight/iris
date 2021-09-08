package iris.dsl

import org.scalatest._
import org.scalatestplus.scalacheck.Checkers
import org.scalatest.flatspec.AnyFlatSpec
import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Prop._

import iris._
import PredicateHelpers._

class LogicalOperatorsDSL extends AnyFlatSpec with Checkers {
  @Record case class User(username: String, age: Int)

  case class Case(predicate: Predicate[String], result: String)

  /** Constructs a predicate case for the username */
  def usernamePredicate(username: String): Case =
    Case(User.username === username, PredicateImpl.eq("username", username))

  def genUsernamePredicate: Gen[Case] =
    for {
      s <- Gen.asciiPrintableStr
      p <- Gen.const(usernamePredicate(s))
    } yield p

  /** Constructs a predicate case for the age */
  def agePredicate(age: Int): Case =
    Case(User.age === age, PredicateImpl.eq("age", age))

  def genAgePredicate: Gen[Case] =
    for {
      n <- Gen.choose(-100, 100)
      p <- Gen.const(agePredicate(n))
    } yield p

  /** Applies user operation to the list of predicates creating a case with
    * precalculated value
    */
  def combinedPredicate(
      operands: List[Case],
      pFn: (Predicate[String], Predicate[String]) => Predicate[String],
      rFn: (String, String) => String
  ): Case =
    operands.zipWithIndex.foldLeft(operands.head) {
      case (acc: Case, (p: Case, i: Int)) =>
        i match {
          case 0 => p
          case _ =>
            Case(
              pFn(acc.predicate, p.predicate),
              rFn(acc.result, p.result)
            )
        }
    }

  def andPredicate(operands: List[Case]): Case =
    combinedPredicate(operands, _ && _, PredicateImpl.and)

  def orPredicate(operands: List[Case]): Case =
    combinedPredicate(operands, _ || _, PredicateImpl.or)

  def genComplexPredicate(depth: Int, maxDepth: Int): Gen[Case] =
    for {
      operands <- Gen.nonEmptyListOf(genPredicateTree(depth, maxDepth))
      op <- Gen.oneOf(
        Gen.const[List[Case] => Case](andPredicate),
        Gen.const[List[Case] => Case](orPredicate)
      )
      p <- Gen.const(op(operands))
    } yield p

  def genPredicateTree(depth: Int, maxDepth: Int): Gen[Case] =
    if (depth >= maxDepth) Gen.oneOf(genUsernamePredicate, genAgePredicate)
    else
      Gen.lzy(
        Gen.oneOf(
          genComplexPredicate(depth + 1, maxDepth),
          genUsernamePredicate,
          genAgePredicate
        )
      )

  it should "generate a valid predicate for &&" in {
    assertPredicate(
      (User.username === "Bob") && (User.age >= 18),
      "(username eq Bob) and (age gte 18)"
    )
  }

  it should "generate a valid predicate for ||" in {
    assertPredicate(
      (User.username === "Bob") || (User.age >= 18),
      "(username eq Bob) or (age gte 18)"
    )
  }

  it should "generate predicate result equal to precalculated value" in {
    check {
      forAll(genComplexPredicate(0, 2)) { (p: Case) =>
        p.predicate(PredicateImpl) == p.result
      }
    }
  }
}
