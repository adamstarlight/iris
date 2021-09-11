package iris.doobie

import zio._
import iris._

class PostgresRepository[T](conn: PostgresConnection)
    extends Repository[Task, T] {
  type PredicateCtx = String

  def find(
      predicate: PredicateDSL[PredicateCtx] => PredicateCtx
  ): FindQuery[Task, T] = ???

  def findAll(): FindQuery[Task, T] = ???

  def insert(document: T): UpdateQuery[Task, T] = ???

  def delete(document: T): DeleteQuery[Task, T] = ???
}
