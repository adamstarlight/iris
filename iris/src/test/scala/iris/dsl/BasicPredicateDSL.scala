package iris.dsl

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import matchers.should.Matchers._

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

    ValueTypes.boolVal mustBe a[RecordField[Boolean]]
    ValueTypes.byteVal mustBe a[RecordField[Byte]]
    ValueTypes.shortVal mustBe a[RecordField[Short]]
    ValueTypes.intVal mustBe a[RecordField[Int]]
    ValueTypes.longVal mustBe a[RecordField[Long]]
    ValueTypes.floatVal mustBe a[RecordField[Float]]
    ValueTypes.doubleVal mustBe a[RecordField[Double]]
    ValueTypes.charVal mustBe a[RecordField[Char]]
    ValueTypes.stringVal mustBe a[RecordField[String]]
  }
}
