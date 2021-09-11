package iris.doobie

import zio._
import zio.interop.catz._
import zio.interop.catz.implicits._

import doobie._
import javax.sql.DataSource
import scala.concurrent.ExecutionContext

trait PostgresConnection {
  def transactor: Transactor[Task]
}

object PostgresConnection {
  def fromDataSource(ds: DataSource, ec: ExecutionContext): PostgresConnection =
    new PostgresConnection {
      val transactor = Transactor.fromDataSource[Task](ds, ec)
    }
}
