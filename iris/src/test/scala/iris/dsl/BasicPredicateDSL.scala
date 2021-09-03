package iris.dsl

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import matchers.must.Matchers._

import iris._

class BasicPredicateDSL extends AnyFlatSpec {
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

    ValueTypes.boolVal mustBe a[RecordField[_]]
    ValueTypes.byteVal mustBe a[RecordField[_]]
    ValueTypes.shortVal mustBe a[RecordField[_]]
    ValueTypes.intVal mustBe a[RecordField[_]]
    ValueTypes.longVal mustBe a[RecordField[_]]
    ValueTypes.floatVal mustBe a[RecordField[_]]
    ValueTypes.doubleVal mustBe a[RecordField[_]]
    ValueTypes.charVal mustBe a[RecordField[_]]
    ValueTypes.stringVal mustBe a[RecordField[_]]
  }

  it should "preserve original companion object content" in {
    @Record case class User(username: String, age: Int)

    object User {
      val companionField = "exists"
    }

    assert(User.companionField == "exists")
  }
}
