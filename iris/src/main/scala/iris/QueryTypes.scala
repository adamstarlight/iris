package iris

trait FindQuery[F[_], T] extends QueryExecutor[F, T, T] {
  //def find(predicate: Predicate): FindQuery[F, T]
  def update(u: Update[T]): UpdateQuery[F, T]
  def delete(): DeleteQuery[F, T]
}

trait QueryResult[T] {
  def rowsAffected: Int
}

trait InsertResult[T] extends QueryResult[T] {
  def lastInsertId: Int
}

trait InsertQuery[F[_], T] extends QueryExecutor[F, T, InsertResult[T]] {}
trait UpdateQuery[F[_], T] extends QueryExecutor[F, T, QueryResult[T]] {}
trait DeleteQuery[F[_], T] extends QueryExecutor[F, T, QueryResult[T]] {}
