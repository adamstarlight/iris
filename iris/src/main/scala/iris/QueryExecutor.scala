package iris

trait QueryExecutor[F[_], T, R] {
  def orderBy(field: RecordField[T]): QueryExecutor[F, T, R]
  def limit(n: Int): QueryExecutor[F, T, R]
  def exec(): R
}
