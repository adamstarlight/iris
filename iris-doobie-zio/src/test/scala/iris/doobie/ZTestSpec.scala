package iris.doobie

import zio._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalactic.source.Position
import java.io.{StringWriter, PrintWriter}
import org.scalatest.compatible.Assertion

abstract class ZTestSpec extends AnyFlatSpec {
  private def getStackTrace(e: Throwable): String = {
    val writer = new StringWriter
    val printer = new PrintWriter(writer)

    e.printStackTrace(printer)
    writer.toString
  }

  private def failedAssertion(e: Any): Assertion =
    e match {
      case e: Throwable =>
        fail(s"Side effect failed with an exception:\n${getStackTrace(e)}")
      case _ => fail(s"Side effect failed with an error: $e")
    }

  def assertM[A](zio: ZIO[Any, Any, A], expected: A)(implicit pos: Position) =
    Runtime.default.unsafeRun(zio.either) match {
      case Right(a) => assertResult(expected)(a)
      case Left(e)  => failedAssertion(e)
    }

  def testM[A](zio: ZIO[Any, Any, A])(f: A => Any)(implicit pos: Position) =
    Runtime.default.unsafeRun(zio.either) match {
      case Right(a) => f(a)
      case Left(e)  => failedAssertion(e)
    }
}
