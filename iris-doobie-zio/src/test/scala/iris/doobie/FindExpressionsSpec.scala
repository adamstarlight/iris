package iris.doobie

import zio._
import zio.interop.catz._
import zio.interop.catz.implicits._

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres
import org.scalatest.BeforeAndAfter
import org.scalatest.BeforeAndAfterAll
import javax.sql.DataSource

import doobie._
import doobie.implicits._

import scala.concurrent.ExecutionContext

class FindExpressionsSpec extends ZTestSpec with BeforeAndAfter {
  var pg: EmbeddedPostgres = _
  var ds: DataSource = _

  before {
    pg = EmbeddedPostgres.start()
    ds = pg.getPostgresDatabase()

    val ec = ExecutionContext.global
    val xa = Transactor.fromDataSource[Task](ds, ec)

    val stmt = sql"""
      CREATE TABLE users (
        id SERIAL,
        username VARCHAR NOT NULL,
        age INT NOT NULL
      );

      INSERT INTO users (username, age) VALUES ('Bob', 10);
    """

    testM(stmt.update.run.transact(xa)) { result =>
      println(result)

      true
    }
  }

  after {
    pg.close()
  }

  it should "fail" in {
    assertM(ZIO.succeed("Works"), "Works")
    testM(ZIO.succeed("Okaaaay")) { a =>
      assert(a < "Works")
    }
  }

  it should "fail2" in {
    assertM(ZIO.succeed("Works"), "Works")
    testM(ZIO.succeed("Okaaaay")) { a =>
      assert(a < "Works")
    }
  }
}
