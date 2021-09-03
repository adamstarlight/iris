package iris.dsl

import scala.reflect.runtime.universe._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import matchers.must.Matchers._

import iris._

class BasicPredicateDSL extends AnyFlatSpec {
  import BasicPredicateDSL._

  it should "compile" in {
    @Record case class ValueTypes(
        boolVal: Boolean,
        byteVal: Byte,
        shortVal: Short,
        intVal: Int,
        longVal: Long,
        floatVal: Float,
        doubleVal: Double,
        charVal: Char,
        stringVal: String
    )
  }

  it should "have a property in companion object for each field" in {
    @Record case class ValueTypes(
        boolVal: Boolean,
        byteVal: Byte,
        shortVal: Short,
        intVal: Int,
        longVal: Long,
        floatVal: Float,
        doubleVal: Double,
        charVal: Char,
        stringVal: String
    )

    assertType(ValueTypes.boolVal, typeOf[RecordField[Boolean]])
    assertType(ValueTypes.byteVal, typeOf[RecordField[Byte]])
    assertType(ValueTypes.shortVal, typeOf[RecordField[Short]])
    assertType(ValueTypes.intVal, typeOf[RecordField[Int]])
    assertType(ValueTypes.longVal, typeOf[RecordField[Long]])
    assertType(ValueTypes.floatVal, typeOf[RecordField[Float]])
    assertType(ValueTypes.doubleVal, typeOf[RecordField[Double]])
    assertType(ValueTypes.charVal, typeOf[RecordField[Char]])
    assertType(ValueTypes.stringVal, typeOf[RecordField[String]])
  }

  it should "preserve original companion object content" in {
    @Record case class User(username: String, age: Int)

    object User {
      val companionField = "exists"
    }

    assert(User.companionField == "exists")
  }
}

object BasicPredicateDSL {
  def assertType[T](value: T, expected: Type)(implicit actual: TypeTag[T]) =
    assert(actual.tpe === expected)
}
