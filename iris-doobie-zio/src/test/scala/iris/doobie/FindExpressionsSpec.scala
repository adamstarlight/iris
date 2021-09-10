package iris.doobie

import zio._

class FindExpressionsSpec extends ZTestSpec {
  it should "fail" in {
    assertM(ZIO.succeed("Works"), "Works")
    testM(ZIO.succeed("Okaaaay")) { a =>
      assert(a < "Works")
    }
  }
}
